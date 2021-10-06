package com.torrydo.vocabsource.eng

import com.torrydo.vocabsource.ResponseVocabList
import com.torrydo.vocabsource.eng.models.EngResult
import com.torrydo.vocabsource.eng.models.InnerEngResult
import com.torrydo.vocabsource.eng.models.Pronunciation
import com.torrydo.vocabsource.utli.MyJsoupHelper
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

internal class EngSearchImpl : EngSearch {

    private val TAG = "vocabSource-EngSearchImpl"

    private val baseUrlPreparedForAudioDownloader = "https://dictionary.cambridge.org"
    private val mainUrl = "https://dictionary.cambridge.org/dictionary/english/"

    private var myJsoupHelper: MyJsoupHelper? = null

    init {
        if (myJsoupHelper == null) {
            myJsoupHelper = MyJsoupHelper()
        }

    }

    override fun getResult(
        keyWord: String,
        responseVocabList: ResponseVocabList
    ) {
        var rsList = ArrayList<EngResult>()
        val mThread = Thread {
            try {
                val doc = MyJsoupHelper().connect(mainUrl + keyWord)

                val elements: Elements =
                    doc.select("div.pr.dictionary")
                        .first()
                        .select("div.pr.entry-body__el")

                rsList = getTranslation(keyWord, elements) as ArrayList<EngResult>


            } catch (e: Exception) {
                responseVocabList.onError(e)
                println(e.message)
            }
        }
        mThread.start()
        mThread.join()

        if (!rsList.isNullOrEmpty())
            responseVocabList.onSuccess(rsList)
        else println("EngSearchImpl - rsLIst null/empty")
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

    // error ------------------------

    private fun getPronunciationAudioUrl(element: Element): String {
        return try {
            baseUrlPreparedForAudioDownloader +
                    element.select("span.daud")
                        .last()
                        .select("source")
                        .first()
                        .attr("src")
        } catch (e: Exception) {
            print("message = ${e.message}")
            "null"
        }
    }

    private fun getTranslation(keyWord: String, source: Elements): List<EngResult> {

        val engResultList: ArrayList<EngResult> = ArrayList()


        for (elementOuter: Element in source) {
            val irList: ArrayList<InnerEngResult> = ArrayList()

            val type = getType(elementOuter)

            val pronunciationText = getPronunciationText(elementOuter)
            val pronunciationUrl = getPronunciationAudioUrl(elementOuter)
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
                    vocab = keyWord,
                    type = type,
                    pronunciation = pronunciation,
                    innerEngResultList = irList
                )
            )
        }

        return engResultList
    }
}