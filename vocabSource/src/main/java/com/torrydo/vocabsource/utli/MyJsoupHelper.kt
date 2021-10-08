package com.torrydo.vocabsource.utli

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class MyJsoupHelper {

    fun connect(url: String): Document {
        return Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36")
            .timeout(1000 * 8)
            .ignoreHttpErrors(true)
            .get()
    }

//    fun connect(url: String) : Document{
//        val mUrl = URL(url)
////        val proxy = Proxy(
////            Proxy.Type.HTTP,
////            InetSocketAddress("127.0.0.1", 8080)
////        ) // or whatever your proxy is
//
//        val uc: HttpURLConnection = mUrl.openConnection() as HttpURLConnection
//
//        uc.connect()
//
//        var line: String? = null
//        val tmp = StringBuffer()
//        val bufferedReader = BufferedReader(InputStreamReader(uc.inputStream))
//        while (bufferedReader.readLine().also { line = it } != null) {
//            tmp.append(line)
//        }
//
//        return Jsoup.parse(tmp.toString())
//    }

    fun getFirstText(element: Element, selector: String): String {
        return try {
            element.select(selector).first().text()
        } catch (e: Exception) {
            "null"
        }
    }

    fun getLastText(element: Element, selector: String): String {
        return try {
            element.select(selector).last().text()
        } catch (e: Exception) {
            "null"
        }
    }

}