package math.yl.love.configuration.exception

/**
 * 存放所有自定以的错误
 * 定义完成后，请到 ExceptionHandle 进行拦截
 */

/**
 * 通用业务逻辑错误
 */
class BizException(message: String) : RuntimeException(message)