package com.torrydo.transeng.dataSource.data.eng

import com.torrydo.transeng.dataSource.data.MyJsoupHelper
import com.torrydo.transeng.interfaces.ListResultListener
import com.torrydo.transeng.dataSource.data.eng.models.InnerEngResult
import com.torrydo.transeng.dataSource.data.eng.models.EngResult
import com.torrydo.transeng.dataSource.data.eng.pronunciation.models.Pronunciation
import com.torrydo.transeng.interfaces.RequestListener
import com.torrydo.transeng.utils.MyThreadHelper
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


class EngSearchImpl : EngSearch {

    private val TAG = "_TAG_EngSearchImpl"

    private val baseUrlPreparedForAudioDownloader = "https://dictionary.cambridge.org"
    private val mainUrl = "https://dictionary.cambridge.org/dictionary/english/"

    private var doc: Document? = null
    private var myJsoupHelper: MyJsoupHelper? = null

    init {
        myJsoupHelper = MyJsoupHelper()

    }

    override fun getResult(
        keyWord: String,
        listResultListener: ListResultListener
    ) {
        Thread {
            try {
                doc = MyJsoupHelper().connect(mainUrl + keyWord)

                val elements: Elements =
                    doc!!.select("div.pr.dictionary")
                        .first()
                        .select("div.pr.entry-body__el")

                val rsList = getTranslation(elements) as ArrayList<EngResult>

                MyThreadHelper().startOnMainThread(object : RequestListener {
                    override fun request() {
                        listResultListener.onSuccess(rsList)
                    }
                })

            } catch (e: Exception) {
                listResultListener.onError(e)
            }
        }.start()
    }


    private fun getType(element: Element): String {
        return myJsoupHelper?.getFirstText(
            element,
            "span.pos.dpos"
        ) ?: "null"
    }

    private fun getPronunciationText(element: Element): String {
        return myJsoupHelper?.getLastText(
            element,
            "span.ipa.dipa.lpr-2.lpl-1"
        ) ?: "null"
    }

    private fun getPronunciationAudioUrl(): String {
        return try {
            baseUrlPreparedForAudioDownloader +
                    doc!!.getElementById("ampaudio2")
                        .select("source")
                        .first()
                        .attr("src")
        } catch (e: Exception) {
            "null"
        }
    }

    private fun getTranslation(source: Elements): List<EngResult> {

        val engResultList: ArrayList<EngResult> = ArrayList()


        for (elementOuter: Element in source) {
            val irList: ArrayList<InnerEngResult> = ArrayList()

            val type = getType(elementOuter)

            val pronunciationText = getPronunciationText(elementOuter)
            val pronunciationUrl = getPronunciationAudioUrl()
            val pronunciation = Pronunciation(pronunciationText, pronunciationUrl)

            val elementsContent: Elements = elementOuter.select("div.def-block.ddef_block ")

            for (element in elementsContent) {

                val title = element.select("div.def.ddef_d.db").text()
                val exampleElements = element.select("div.examp.dexamp")

                val examples = StringBuilder()

                for (tempExampleElement in exampleElements) {
                    val tempExemple = tempExampleElement.text()
                    examples.append("• $tempExemple \n")
                }
                val ir = InnerEngResult(title, examples.toString(), false)
                irList.add(ir)

            }

            engResultList.add(
                EngResult(
                    type,
                    pronunciation,
                    irList
                )
            )
        }

        return engResultList
    }
}