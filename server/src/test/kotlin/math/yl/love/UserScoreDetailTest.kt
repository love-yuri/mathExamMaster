package math.yl.love

import math.yl.love.common.utils.JsonUtils.parseJson
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.domain.entity.UserScore
import math.yl.love.database.domain.result.questionBank.GapFillingAnswer
import math.yl.love.database.domain.result.questionBank.JudgeAnswer
import math.yl.love.database.domain.result.questionBank.MultipleChoiceAnswer
import math.yl.love.database.domain.result.questionBank.SingleChoiceAnswer
import math.yl.love.database.domain.result.userScore.UserScoreDetail
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum.*
import math.yl.love.database.service.QuestionBankService
import math.yl.love.database.service.UserScoreService
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.Test

@SpringBootTest
class UserScoreDetailTest {

    @Autowired
    private lateinit var userScoreService: UserScoreService

    @Autowired
    private lateinit var questionBankService: QuestionBankService

    private val log = LoggerFactory.getLogger(UserScoreDetailTest::class.java)

    @Test
    fun createTest() {
        val questions = questionBankService.list()
        val details = questions.map {
            UserScoreDetail(
                questionId = it.id!!,
                questionAnswer = parseAnswer(it)
            )
        }
        userScoreService.save(UserScore(
            detail = details,
            score = 100,
            totalScore = 100,
            pageReleaseId = 1,
            userId = 1,
        ))
    }

    @Test
    fun showTest() {
        log.info("list -> {}", userScoreService.list())
        userScoreService.list().forEach { k ->
            k.detail.forEach {
                log.info("questionId: ${it.questionId}, questionAnswer: ${it.questionAnswer}")
            }
        }
    }

    /**
     * 将答案解析回去
     */
    private fun parseAnswer(questionBank: QuestionBank): Any {
        return when (questionBank.type) {
            SINGLE_CHOICE -> questionBank.answer.parseJson<SingleChoiceAnswer>()
            MULTIPLE_CHOICE -> questionBank.answer.parseJson<MultipleChoiceAnswer>()
            JUDGE -> questionBank.answer.parseJson<JudgeAnswer>()
            GAP_FILLING -> questionBank.answer.parseJson<GapFillingAnswer>()
            SUBJECTIVE -> ""
        }
    }
}