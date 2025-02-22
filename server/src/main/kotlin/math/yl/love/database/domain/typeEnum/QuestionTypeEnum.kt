package math.yl.love.database.domain.typeEnum

import com.baomidou.mybatisplus.annotation.IEnum

enum class QuestionTypeEnum(
    val type: Int
): IEnum<Int> {
    // 单选题
    SINGLE_CHOICE(0),

    // 多选题
    MULTIPLE_CHOICE(1),

    // 判断题
    JUDGE(2),

    // 填空
    GAP_FILLING(3),

    // 主观题
    SUBJECTIVE(4);

    override fun getValue(): Int {
        return type
    }

    companion object {
        const val SINGLE_CHOICE_SRT = "SINGLE_CHOICE"
        const val MULTIPLE_CHOICE_SRT = "MULTIPLE_CHOICE"
        const val JUDGE_SRT = "JUDGE"
        const val GAP_FILLING_SRT = "GAP_FILLING"
        const val SUBJECTIVE_SRT = "SUBJECTIVE"
    }
}

