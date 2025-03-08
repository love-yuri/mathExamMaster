package math.yl.love.database.domain.typeEnum

import com.baomidou.mybatisplus.annotation.IEnum

enum class ExamPageStatusEnum(
    val type: Int
): IEnum<Int> {
    // 未开始
    NOT_START(0),

    // 进行中
    DOING(1),

    // 已结束
    FINISHED(2),

    // 正在阅卷
    REVIEWING(3),

    // 阅卷结束
    REVIEW_COMPLETED(4);

    override fun getValue(): Int {
        return type
    }
}

