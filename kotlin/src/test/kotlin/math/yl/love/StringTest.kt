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
}