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
}

