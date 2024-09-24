package math.yl.love.database.service

import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.SystemFile
import math.yl.love.database.domain.params.system.GenerateParam
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream

@Service
@Transactional(readOnly = true)
class SystemService (
    private val autoMysqlService: AutoMysqlService,
    private val systemFileService: SystemFileService
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
}