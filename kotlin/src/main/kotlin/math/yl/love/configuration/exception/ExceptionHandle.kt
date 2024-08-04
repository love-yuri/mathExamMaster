package math.yl.love.configuration.exception

import math.yl.love.base.R
import math.yl.love.base.SystemCode
import org.slf4j.LoggerFactory
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class ExceptionHandle {
    private val log = LoggerFactory.getLogger(ExceptionHandle::class.java)

    /**
     * 全局异常捕获
     */
    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handler(e: Exception): R<*> {
        log.error("yuri: 未知异常 -> ${e.message}", e)
        return R.fail(SystemCode.InnerError.code, SystemCode.InnerError.message)
    }

    /**
     * 参数解析失败
     * 用于处理方法参数验证失败的情况。
     * 当使用 @Valid 或 @Validated 注解来验证控制器方法的参数时
     * 如果验证失败 springBoot 会抛出MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handler(e: MethodArgumentNotValidException): R<*> {
        val errorMsg: String = e.bindingResult.allErrors.joinToString { error ->
            val fieldError = error as FieldError
            "[${fieldError.field}: ${fieldError.defaultMessage}]"
        }
        log.error("yuri: 参数解析解析失败 msg -> $errorMsg", e)
        return R.fail(SystemCode.ParameterValidError.code, errorMsg)
    }


    /**
     * 数据绑定过程中发生错误时被抛出。
     * 当 Spring 尝试将请求参数绑定到控制器方法的参数上时，
     * 如果发现绑定过程中出现了问题，就会抛出 BindException。
     */
    @ExceptionHandler(BindException::class)
    @ResponseBody
    fun handler(e: BindException): R<*> {
        val errorMsg: String = e.bindingResult.allErrors.joinToString { error ->
            val fieldError = error as FieldError
            "[${fieldError.field}: ${fieldError.defaultMessage}]"
        }
        log.error("yuri: 绑定参数异常 msg -> $errorMsg", e)
        return R.fail(SystemCode.ParameterValidError.code, errorMsg)
    }

    /**
     * 缺少必要参数错误
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseBody
    fun handler(e: HttpMessageNotReadableException): R<*> {
        log.error("yuri: 缺少必要参数!!! ${e.message}", e)
        return R.fail(555, "缺少必要参数!!!")
    }
}