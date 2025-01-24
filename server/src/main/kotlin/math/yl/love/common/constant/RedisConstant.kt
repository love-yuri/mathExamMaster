package math.yl.love.common.constant

/**
 * redis的前缀key
 */
object RedisConstant {
    /**
     * 程序通用前缀
     */
    const val PREFIX = "love:yl"

    /**
     * 登录信息
     */
    const val USER_INFO = "${PREFIX}:user:info"

    /**
     * 根据id获取系统文件信息
     */
    const val SYSTEM_FILE_GET = "${PREFIX}:system:file:get"
}