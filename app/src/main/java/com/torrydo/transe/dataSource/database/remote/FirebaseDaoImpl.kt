package com.torrydo.transe.dataSource.database.remote

import com.google.firebase.database.*
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener

class FirebaseDaoImpl : RemoteDao {

    private val TAG = "_TAG_FirebaseDaoImpl"

    companion object {
        const val VOCAB = "Vocab"
        const val TIME = "time"
        const val IS_FINISHED = "isFinished"
    }

    private val firebaseDatabse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseRef: DatabaseReference = firebaseDatabse.reference

    override fun getAllVocab(listResultListener: ListResultListener) {

        firebaseRef.child(VOCAB).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(temp: DataSnapshot) {

                val remoteVocabArray = ArrayList<RemoteVocab>()

                temp.children.forEach { snapshot ->

                    val keyWord = snapshot.key
                    var time: Long = 0
                    var isFinished = false
                    keyWord?.let { nonNullKey ->
                        snapshot.child(nonNullKey).also {
                            time = it.child(TIME).value as Long
                            isFinished = it.child(IS_FINISHED).value as Boolean
                        }
                    }
                    remoteVocabArray.add(
                        RemoteVocab(keyWord ?: "", RemoteVocabProperties(time, isFinished))
                    )

                }
                listResultListener.onSuccess(remoteVocabArray)

            }

            override fun onCancelled(error: DatabaseError) {
                listResultListener.onError(error.toException())
            }
        })
    }

    override fun insert(remoteVocab: RemoteVocab, resultListener: ResultListener?) {
        firebaseRef
            .child(VOCAB)
            .child(remoteVocab.keyWord)
            .setValue(remoteVocab.remoteVocabProperties)
            .addOnSuccessListener {
                resultListener?.onSuccess(null)
            }
            .addOnFailureListener {
                resultListener?.onError(it)
            }
    }

    override fun insertAll(listRemoteVocab: List<RemoteVocab>, resultListener: ResultListener?) {
    }

    override fun update(remoteVocab: RemoteVocab, resultListener: ResultListener?) {
    }

    override fun delete(remoteVocab: RemoteVocab, resultListener: ResultListener?) {
    }
}