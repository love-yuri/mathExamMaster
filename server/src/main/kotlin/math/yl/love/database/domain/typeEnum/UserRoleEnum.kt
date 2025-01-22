package math.yl.love.database.domain.typeEnum

import com.baomidou.mybatisplus.annotation.IEnum

enum class UserRoleEnum(
    val type: Int
): IEnum<Int> {
    // 管理员
    ADMIN(1),

    // 教师
    TEACHER(2),

    // 学生
    STUDENT(3);

    override fun getValue(): Int {
        return type
    }
}

