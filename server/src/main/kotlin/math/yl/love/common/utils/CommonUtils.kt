package math.yl.love.common.utils

import cn.dev33.satoken.stp.StpUtil

object CommonUtils {

    // 获取当前登陆的用户名
    val username get() = StpUtil.getLoginId().toString()
}