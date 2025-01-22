package math.yl.love.database.domain.params.user

enum class GetStudentEnum() {
    All , // 获取所有学生
    HasClass, // 有班级的学生
    NoClass, // 获取没有班级的学生
}