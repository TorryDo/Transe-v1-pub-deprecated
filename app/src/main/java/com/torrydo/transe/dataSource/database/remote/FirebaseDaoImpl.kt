package com.torrydo.transe.dataSource.database.remote

import android.util.Log
import com.google.firebase.database.*
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener

class FirebaseDaoImpl : RemoteDao {

    private val TAG = "_TAG_FirebaseDaoImpl"

    companion object {
        const val BRANCH_USER = "User"
        const val VOCAB = "Vocab"
        const val TIME = "time"
        const val FINISHED = "finished"
    }

    private val firebaseDatabse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var firebaseRef: DatabaseReference? = null

    override fun setUserID(uid: String) {
        firebaseRef = firebaseDatabse.reference
            .child(BRANCH_USER)
            .child(uid)
    }

    override fun getAllVocab(listResultListener: ListResultListener) {
        // ---------------------- when user logged in -------------------
        if (firebaseRef != null) {
            firebaseRef!!.child(VOCAB).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(baseVocabJson: DataSnapshot) {

                    val remoteVocabArray = ArrayList<BaseVocab>()

                    baseVocabJson.children.forEach { singleVocab ->

                        val keyWord = singleVocab.key

                        keyWord?.let { nonNullKey ->
                            try {
                                val time = singleVocab.child(TIME).value.toString().toLong()
                                val finished = singleVocab.child(FINISHED).value.toString().toBoolean()

                                Log.e(TAG, "time = $time & finished = $finished")

                                remoteVocabArray.add(BaseVocab(nonNullKey, time, finished))
                            } catch (e: Exception) {
                                Log.e(TAG, "unable to add remotevocab")
                            }

                        }
                    }
                    listResultListener.onSuccess(remoteVocabArray)
                }

                override fun onCancelled(error: DatabaseError) {
                    listResultListener.onError(error.toException())
                }
            })
        } else {
            // ---------------------- when user didn't logged in -------------------
            listResultListener.onError(Exception("Please Sign In First !"))
        }

    }

    override fun insert(baseVocab: BaseVocab, resultListener: ResultListener?) {
        if (firebaseRef != null) {

            val tempHashmap =  HashMap<String, String>()
            tempHashmap.put(TIME, baseVocab.time.toString())
            tempHashmap.put(FINISHED, baseVocab.finished.toString())

            firebaseRef!!
                .child(VOCAB)
                .child(baseVocab.keyWord)
                .setValue(tempHashmap)
                .addOnSuccessListener { resultListener?.onSuccess(null) }
                .addOnFailureListener { resultListener?.onError(it) }
        } else {
            resultListener?.onError(Exception("Please Sign In First"))
        }

    }

    override fun insertAll(listBaseVocab: List<BaseVocab>, resultListener: ResultListener?) {
    }

    override fun update(baseVocab: BaseVocab, resultListener: ResultListener?) {
        insert(baseVocab, resultListener)
    }


    override fun delete(baseVocab: BaseVocab, resultListener: ResultListener?) {
        if (firebaseRef != null) {
            firebaseRef!!.child(baseVocab.keyWord)
                .removeValue()
                .addOnSuccessListener { resultListener?.onSuccess(null) }
                .addOnFailureListener { resultListener?.onError(it) }
        } else {
            resultListener?.onError(Exception("Please Sign In First"))
        }
    }
}