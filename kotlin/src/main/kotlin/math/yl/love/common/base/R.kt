package math.yl.love.common.base

import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.Serializable
import math.yl.love.common.utils.JsonUtils.toJson


@Serializable
data class R <T> (
    val code: Int,
    val message: String,
    val data: T? = null,
    val isSuccess: Boolean = true,
) {
    companion object {

        /**
         * 响应失败返回
         */
        fun fail(code: Int, msg: String): R<String> {
            return R(code, msg, null, false)
        }

        fun fail(systemCode: SystemCode): R<String> {
            return R(systemCode.code, systemCode.message, null, false)
        }

        /**
         * 只传消息默认业务错误
         */
        fun fail(msg: String): R<String> {
            return R(SystemCode.BizError.code, msg, null, false)
        }

        /**
         * 写入response
         */
        fun fail(response: HttpServletResponse, code: SystemCode) {
            response.apply {
                status = code.code
                contentType = "application/json;charset=UTF-8"
                writer.write(fail(code).toJson())
            }
        }


        /**
         * 响应成功返回
         */
        fun <T> success(response: T): R<T> {
            val systemCode: SystemCode = SystemCode.OK
            return R(systemCode.code, systemCode.message, response, true)
        }


        /**
         * 响应成功返回
         */
        fun success(): R<String> {
            val systemCode: SystemCode = SystemCode.OK
            return R(systemCode.code, systemCode.message, null, true)
        }
    }
}
