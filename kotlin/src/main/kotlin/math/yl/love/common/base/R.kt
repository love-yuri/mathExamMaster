package math.yl.love.common.base

import kotlinx.serialization.Serializable


@Serializable
data class R <T>  @JvmOverloads constructor (
    val code: Int,
    val message: String,
    val data: T? = null,
    val isSuccess: Boolean = true,
) {

    companion object {

        /**
         * 响应失败返回
         */
        @JvmStatic
        fun fail(code: Int, msg: String): R<String> {
            return R(code, msg, null, false)
        }

        @JvmStatic
        fun fail(systemCode: SystemCode): R<String> {
            return R(systemCode.code, systemCode.message, null, false)
        }

        /**
         * 只传消息默认业务错误
         */
        @JvmStatic
        fun fail(msg: String): R<String> {
            return R(SystemCode.BizError.code, msg, null, false)
        }


        /**
         * 响应成功返回
         */
        @JvmStatic
        fun <T> success(response: T): R<T> {
            val systemCode: SystemCode = SystemCode.OK
            return R(systemCode.code, systemCode.message, response, true)
        }


        /**
         * 响应成功返回
         */
        @JvmStatic
        fun success(): R<String> {
            val systemCode: SystemCode = SystemCode.OK
            return R(systemCode.code, systemCode.message, null, true)
        }
    }
}
