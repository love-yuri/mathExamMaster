package math.yl.love.database.domain.typeEnum

import com.baomidou.mybatisplus.annotation.IEnum

enum class QuestionTypeEnum: IEnum<Int> {
    // 单选题
    SINGLE_CHOICE,

    // 多选题
    MULTIPLE_CHOICE,

    // 判断题
    JUDGE,

    // 简答题
    SHORT_ANSWER,

    // 主观题
    SUBJECTIVE;

    override fun getValue(): Int {
        return value
    }
}

