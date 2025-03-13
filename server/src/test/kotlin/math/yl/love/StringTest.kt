package math.yl.love

import math.yl.love.common.base.Log.log
import kotlin.test.Test

class StringTest {

    @Test
    fun split() {
        val str = "user_info"
        val res = str.split("_").joinToString("/")
        log.info(res.toString())
    }

    @Test
    fun splitTest() {
        val answer = "1 4 5"
        val res = answer.split(" *".toRegex())
            .filter { it.isNotEmpty() }
            .map { it }
        log.info(res.toString())
    }
}