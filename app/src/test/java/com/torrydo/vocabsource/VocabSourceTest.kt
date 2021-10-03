package com.torrydo.vocabsource

import org.junit.Assert.*
import org.junit.Test

class VocabSourceTest{

    @Test
    fun `get english source test`(){
        val vocabSource = VocabSource()
        vocabSource.search("key"){ nullableList ->
            nullableList.forEach { engRs ->
                println(engRs?.innerEngResultList?.size)
            }
        }
    }

}