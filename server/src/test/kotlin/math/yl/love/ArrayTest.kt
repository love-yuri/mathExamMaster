package math.yl.love

import org.slf4j.LoggerFactory
import kotlin.test.Test

/**
 * 数组测试
 */
class ArrayTest {

    private val log = LoggerFactory.getLogger(ArrayTest::class.java)

    @Test
    fun equalTest() {
        val a = listOf(1, 2, 4)
        val b = listOf(1, 3, 4)
        val c = listOf(1, 2, 4)
        val d = listOf(1, 4, 2)
        log.info("a == b: ${a.containsAll(b)}")
        log.info("a == c: ${a.containsAll(c)}")
        log.info("a == d: ${a.containsAll(d)}")
    }
}