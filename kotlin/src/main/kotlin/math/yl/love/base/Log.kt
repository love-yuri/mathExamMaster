package math.yl.love.base

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Log {
    val <T> T.log: Logger get () = LoggerFactory.getLogger(javaClass)
}