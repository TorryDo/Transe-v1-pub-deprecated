package com.torrydo.vocabsource.eng

import com.torrydo.vocabsource.utli.MyJsoupHelper
import com.torrydo.vocabsource.ResponseVocabList
import com.torrydo.vocabsource.eng.models.EngResult
import com.torrydo.vocabsource.eng.models.InnerEngResult
import com.torrydo.vocabsource.eng.pronunciation.models.Pronunciation
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
        Thread {
            try {
                val doc = MyJsoupHelper().connect(mainUrl + keyWord)

                val elements: Elements =
                    doc.select("div.pr.dictionary")
                        .first()
                        .select("div.pr.entry-body__el")



                val rsList = getTranslation(elements) as ArrayList<EngResult>

                Runnable {
                    responseVocabList.onSuccess(rsList)
                }


            } catch (e: Exception) {
                responseVocabList.onError(e)
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

    private fun getTranslation(source: Elements): List<EngResult> {

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
                    type,
                    pronunciation,
                    irList
                )
            )
        }

        return engResultList
    }
}