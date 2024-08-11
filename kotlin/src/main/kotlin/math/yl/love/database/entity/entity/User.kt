package math.yl.love.database.entity.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import math.yl.love.common.mybatis.BaseEntity

@TableName("user")
class User: BaseEntity() {

    @TableField("role")
    val role: Int? = null

    @TableField("pass_word")
    val passWord: String? = null
}