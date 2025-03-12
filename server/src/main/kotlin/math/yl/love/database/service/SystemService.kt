package math.yl.love.database.service

import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.domain.entity.SystemFile
import math.yl.love.database.domain.params.system.GenerateParam
import math.yl.love.database.mapper.SystemMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import math.yl.love.database.domain.params.system.AiCreateQuestionParam
import math.yl.love.database.domain.result.questionBank.*
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum.*
import java.io.BufferedInputStream

@Service
@Transactional(readOnly = true)
class SystemService (
    private val autoMysqlService: AutoMysqlService,
    private val systemFileService: SystemFileService,
    private val systemMapper: SystemMapper,
    private val deepseekService: DeepseekService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 生成代码
     * @param param 待生成的表
     * 默认使用默认数据库
     */
    fun generate(param: GenerateParam) = autoMysqlService.generate(param)

    /**
     * 上传文件到服务器，使用本地服务器
     * @param file 文件
     */
    @Transactional(rollbackFor = [Exception::class])
    fun uploadFile(file: MultipartFile): SystemFile {
        return try {
            val byteArray = file.inputStream.use { inputStream ->
                BufferedInputStream(inputStream).use { bufferedInputStream ->
                    bufferedInputStream.readBytes()
                }
            }

            systemFileService.uploadFile(byteArray, file.originalFilename ?: "temp")
        } catch (e: Exception) {
            throw BizException("文件上传失败! ${e.message}")
        }
    }

    /**
     * 获取数据库列表
     */
    fun getDataBaseList() = systemMapper.getDataBases()

    /**
     * 获取指定数据库下表格
     */
    fun getTables(dbName: String): Any {
        log.info("获取数据库 $dbName 表格")
        return systemMapper.getTables(dbName)
    }

    /**
     * ai生成题目
     */
    fun aiCreateQuestion(param: AiCreateQuestionParam): QuestionBank? {
        val prompt = """
            请帮我生成一道高数题目，题目类型: ${param.type}, 用户描述: ${param.description}
            所有生成的题目里的公式请按照 <yuri-math math="\int_0^{\infty}\!55\,\mathrm{d}x"></yuri-math> 这种格式来
            输出内容请按照wangeditor的格式，所有换行请用<br>标签,回复内容不要输出多余解释，按照以下格式来。如果是如果是单选多选
            请在题目内容里添加选项，没特殊要求生成4个即可。如果是多选或者填空，答案请用空格隔开
            题目: <p>{题目内容} {如果是单选多选: <br> A. 选项1<br> B. 选项2..}</p>
            难度: {题目难度： 数字1-9}
            答案: {答案内容: 答案对应序号: 0 1 2 ...}
            解析: <p>{题目解析内容}</p>
        """.trimIndent()
        val res = deepseekService.chat(prompt)
        val regex = """
            题目:\s*<p>(.*?)</p>
            \s*难度:\s*(\d+)
            \s*答案:\s*(.*?)
            \s*解析:\s*<p>(.*?)</p>
        """.trimIndent().toRegex(RegexOption.DOT_MATCHES_ALL)

        val matchResult = regex.find(res) ?: return null

        val (question, difficulty, answer, description) = matchResult.destructured

        return when(param.type) {
            SINGLE_CHOICE -> QuestionBank(
                content = question,
                description = description,
                difficulty = difficulty.toInt(),
                answer = SingleChoiceAnswer(
                    options = listOf("", "", "", ""),
                    answer = answer.toInt()
                ),
                type = SINGLE_CHOICE,
            )
            MULTIPLE_CHOICE -> QuestionBank(
                content = question,
                description = description,
                difficulty = difficulty.toInt(),
                answer = MultipleChoiceAnswer(
                    options = listOf("", "", "", ""),
                    answer = answer.split(" *".toRegex()).map { it.toInt() }
                ),
                type = MULTIPLE_CHOICE,
            )
            JUDGE -> QuestionBank(
                content = question,
                description = description,
                difficulty = difficulty.toInt(),
                answer = JudgeAnswer(
                    answer = answer.toInt() == 0
                ),
                type = JUDGE,
            )
            GAP_FILLING -> QuestionBank(
                content = question,
                description = description,
                difficulty = difficulty.toInt(),
                answer = GapFillingAnswer(
                    answer = answer.split(" *".toRegex())
                ),
                type = GAP_FILLING,
            )
            SUBJECTIVE -> QuestionBank(
                content = question,
                description = description,
                difficulty = difficulty.toInt(),
                answer = SubjectiveAnswer(),
                type = SUBJECTIVE,
            )
        }
    }
}