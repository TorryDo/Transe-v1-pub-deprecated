package com.torrydo.transe.views.launcherScreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.torrydo.transe.R
import com.torrydo.transe.adapter.base.GenericAdapter
import com.torrydo.transe.adapter.holderClass.ResultHolder
import com.torrydo.transe.dataSource.database.LocalDatabaseRepository
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.dataSource.translation.SearchRepository
import com.torrydo.transe.dataSource.translation.eng.models.EngResult
import com.torrydo.transe.dataSource.translation.eng.pronunciation.PronunciationHelper
import com.torrydo.transe.dataSource.translation.eng.pronunciation.models.Pronunciation
import com.torrydo.transe.databinding.ItemResultBinding
import com.torrydo.transe.databinding.ViewTransBinding
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.RequestListener
import com.torrydo.transe.interfaces.ResultListener
import com.torrydo.transe.interfaces.VocabListenter
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.transe.utils.MyThreadHelper
import com.torrydo.transe.utils.Utils
import com.torrydo.transe.views.base.HomeLauncherBaseView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Date

@OptIn(DelicateCoroutinesApi::class)
class TransView(
    private val context: Context,
    windowManager: WindowManager,
    private val searchRepository: SearchRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
    private var clickedBackButton: () -> Unit
) : HomeLauncherBaseView<ViewTransBinding>(windowManager) {

    private val pronunciationHelper = PronunciationHelper(context)

    private var engResultList = ArrayList<EngResult>()
    private var adapterEng: GenericAdapter<EngResult>? = null

    private var editText: EditText? = null

    private var KEY_WORD = ""

    private var STAR_TYPE = -1
    private val STAR_UNSAVED = 0
    private val STAR_SAVED = 1

    private val TAG = "_TAG_TransView"

    init {
        binding = ViewTransBinding.inflate(LayoutInflater.from(context))
        initViewFunction(binding)
        initViewLayoutParams()
        initRecyclerView()
    }

    @SuppressLint("SetTextI18n")
    override fun initViewFunction(viewBinding: ViewTransBinding) {
        super.initViewFunction(viewBinding)

        editText = binding.viewTransSearchBar.searchBarEdittext
        val searchButton = binding.viewTransSearchBar.searchBarButtonSearch

        binding.viewTransChipGroup.chipStar.let { star ->
            star.setOnClickListener {
                if (KEY_WORD.length > 1) {
                    when (STAR_TYPE) {
                        STAR_SAVED -> {
                            star.setAnimation(R.raw.lottie_star_reversed)
                            star.playAnimation()

                            GlobalScope.launch(Dispatchers.IO) {
                                getVocabFromLocalDB(KEY_WORD, localDatabaseRepository) { vocab ->
                                    if (vocab != null) {
                                        deleteVocabFromLocalDB(
                                            localDatabaseRepository,
                                            vocab
                                        )
                                        STAR_TYPE = STAR_UNSAVED
                                        Utils.showShortToast(context, "deleted")
                                    }
                                }
                            }

                        }
                        STAR_UNSAVED -> {

                            star.setAnimation(R.raw.lottie_star)
                            star.playAnimation()

                            val strBuilder = StringBuilder()
                            try {
                                for (inner in engResultList[0].innerEngResultList) {
                                    strBuilder.append("• ${inner.title} \n")
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "message = ${e.message}")
                            }


                            insertVocabToLocalDB(
                                localDatabaseRepository,
                                Vocab(
                                    uid = 0,
                                    time = Date(System.currentTimeMillis()),
                                    vocab = KEY_WORD,
                                    finished = false,
                                    contentEng = engResultList,
                                    contentVi = emptyList()
                                )
                            )
                            STAR_TYPE = STAR_SAVED
                            Utils.showShortToast(context, "saved")
                        }
                    }
                } else {
                    Utils.showShortToast(context, "đừng khiến tau đau khổ")
                }
            }
        }

        binding.viewTransSearchBar.searchBarBackArrow.setOnClickListener {
            clickedBackButton()
            Utils.hideKeyboard(context, editText!!)
        }

        searchButton.setOnClickListener {
            val keyWord = editText!!.text.toString()
            searchVocab(keyWord)
        }
        editText!!.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyWord = editText!!.text.toString()
                searchVocab(keyWord)
            }
            true
        }

        binding.viewTransChipGroup.chip2.setOnClickListener {
            searchRepository.getImageList(editText!!.text.toString(), object : ResultListener{
                override fun <T> onSuccess(data: T?) {
                    data?.let {
                        println(it)
                    }
                }
            })
        }

    }

    private fun searchVocab(keyWord: String) {
        KEY_WORD = keyWord
        determineStarState(localDatabaseRepository, keyWord)

        try {
            searchRepository.getEnglishSource(keyWord, object : ListResultListener {
                override fun <T> onSuccess(dataList: List<T>) {
                    if (dataList[0] is EngResult) {
                        engResultList = dataList as ArrayList<EngResult>
                        adapterEng?.setItems(dataList)

                        CONSTANT.KeyWord_Holder = keyWord

                    } else {
                        Log.d(TAG, "doesn't match condition")
                    }
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, "unable to get Vocab")
                }

            })

            /** search eng->vi later */

        } catch (e: Exception) {
            Utils.showShortToast(context, "no result")
        }

        Utils.hideKeyboard(context, editText!!)
    }

//   --------------- freeze the things below (just kidding) :D ----------------------

    @OptIn(DelicateCoroutinesApi::class)
    private fun determineStarState(repo: LocalDatabaseRepository, keyWord: String) {
        GlobalScope.launch(Dispatchers.IO) {

            getVocabFromLocalDB(keyWord, repo) { vocab ->
                binding.viewTransChipGroup.chipStar.also { lt ->
                    MyThreadHelper().startOnMainThread(object : RequestListener {
                        override fun request() {
                            if (vocab != null) {
                                lt.makeStarSaved()
                            } else {
                                lt.makeStarUnSaved()
                            }
                        }
                    })
                }

            }
        }
    }

    private fun LottieAnimationView.makeStarSaved() {
        this.setAnimation(R.raw.lottie_star_reversed)
        this.frame = 0
        STAR_TYPE = STAR_SAVED
    }

    private fun LottieAnimationView.makeStarUnSaved() {
        this.setAnimation(R.raw.lottie_star)
        this.frame = 0
        STAR_TYPE = STAR_UNSAVED
    }

    private fun initRecyclerView() {

        adapterEng = object : GenericAdapter<EngResult>(engResultList) {
            override fun getLayoutId(position: Int, obj: EngResult): Int {
                return R.layout.item_result
            }

            override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return ResultHolder(
                    ItemResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    MyVocabListener()
                )
            }
        }

        binding.viewTransRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterEng
        }
    }

    private fun insertVocabToLocalDB(repo: LocalDatabaseRepository, vocab: Vocab) {
        GlobalScope.launch {
            repo.insert(vocab)
        }
    }

    private suspend fun getVocabFromLocalDB(
        keyWord: String,
        localDatabaseRepository: LocalDatabaseRepository,
        isReady: (data: Vocab?) -> Unit
    ) {
        localDatabaseRepository.get(keyWord) {
            isReady(it)
        }

    }

    private fun deleteVocabFromLocalDB(repo: LocalDatabaseRepository, vocab: Vocab) {
        GlobalScope.launch(Dispatchers.IO) {
            repo.delete(vocab)
        }

    }

    private inner class MyVocabListener : VocabListenter {
        override fun playPronunciation(keyWord: String, pronunciation: Pronunciation) {
            pronunciationHelper.playAudio(keyWord, pronunciation)
        }
    }

    override fun addView() {
        super.addView()
        Utils.showKeyboard(context, editText!!)
    }

    override fun removeView() {
        Utils.hideKeyboard(context, editText!!)
        super.removeView()
    }

    override fun initViewLayoutParams() {
        super.initViewLayoutParams()

        viewParams?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            gravity = Gravity.TOP
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.5f
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
            windowAnimations = R.style.TransViewStyle
        }
    }
}

