package math.yl.love.database.entity.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.v3.oas.annotations.media.Schema
import math.yl.love.common.mybatis.BaseEntity

@TableName("user")
class User: BaseEntity() {

    @Schema(description = "用户角色")
    @TableField("role")
    val role: Int? = null

    @TableField("pass_word")
    @Schema(description = "密码")
    val passWord: String? = null
}