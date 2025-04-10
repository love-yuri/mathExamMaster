package math.yl.love.configuration.exception

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotRoleException
import math.yl.love.common.base.R
import math.yl.love.common.base.SystemCode
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.resource.NoResourceFoundException

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
        return R.fail(SystemCode.InnerError)
    }

    /**
     * 通用Runtime异常
     */
    @ExceptionHandler(RuntimeException::class)
    @ResponseBody
    fun handler(e: RuntimeException): R<*> {
        log.error("yuri: RuntimeException异常 -> ${e.message}", e)
        return R.fail(SystemCode.InnerError.code,  e.message ?: SystemCode.InnerError.message)
    }

    /**
     * 业务逻辑运行时异常
     */
    @ExceptionHandler(BizException::class)
    @ResponseBody
    fun handler(e: BizException): R<*> {
        log.error("yuri: 业务逻辑异常 -> ${e.message}", e)
        return R.fail(SystemCode.BizError.code,  e.message ?: SystemCode.BizError.message)
    }

    /**
     * token解析失败
     */
    @ExceptionHandler(NotLoginException::class)
    @ResponseBody
    fun handler(e: NotLoginException): R<*> {
        log.error("未登录: {}", e.message, e)
        return R.fail(SystemCode.AccessTokenError.code,  e.message ?: SystemCode.AccessTokenError.message)
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
        return R.fail(SystemCode.MissQuery)
    }

    /**
     * 缺少必要参数错误
     */
    @ExceptionHandler(NotRoleException::class)
    @ResponseBody
    fun handler(e: NotRoleException): R<*> {
        log.error("yuri: 没有该角色 ${e.message}", e)
        return R.fail(SystemCode.AccessDenied)
    }

    /**
     * 缺少必要参数错误
     */
    @ExceptionHandler(NoResourceFoundException::class)
    @ResponseBody
    fun handler(e: NoResourceFoundException): R<*> {
        log.error("yuri: 没有找到该url ${e.message}", e)
        return R.fail(SystemCode.NoResource)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleUnsupportedMediaType(ex: HttpMediaTypeNotSupportedException): ResponseEntity<String> {
        log.error("yuri: 无法转换类型: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body("Unsupported Content-Type: ${ex.contentType}. Supported: ${ex.supportedMediaTypes}")
    }
}