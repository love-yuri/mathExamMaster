package math.yl.love.common.utils

import cn.dev33.satoken.stp.StpUtil

object CommonUtils {
    val username get() = StpUtil.getLoginId()?.toString() ?: "yuri"
}