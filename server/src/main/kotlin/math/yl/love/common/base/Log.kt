package math.yl.love.common.base

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Log {
    val <T> T.log: Logger get () = LoggerFactory.getLogger(javaClass)
}