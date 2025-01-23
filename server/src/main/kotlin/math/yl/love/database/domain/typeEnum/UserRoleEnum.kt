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

    companion object {
        const val ADMIN_STR = "ADMIN"
        const val TEACHER_STR = "TEACHER"
        const val STUDENT_STR = "STUDENT"
    }

    override fun getValue(): Int {
        return type
    }
}

