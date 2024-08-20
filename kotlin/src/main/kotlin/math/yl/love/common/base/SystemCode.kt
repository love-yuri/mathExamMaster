package math.yl.love.common.base

enum class SystemCode(
    val code: Int,
    val message: String
) {

    OK(200, "success"),
    AccessTokenError(400, "用户登录令牌失效"),
    UNAUTHORIZED(401, "用户未登录"),
    AuthError(402, "用户名或密码错误"),
    InnerError(500, "系统内部错误"),
    ParameterValidError(501, "参数验证错误"),
    AccessDenied(502, "用户没有权限访问"),
    BizError(503, "业务逻辑错误"),
    MissQuery(504, "缺少必要参数"),
    NoResource(505, "没有找到该URL"),
}
