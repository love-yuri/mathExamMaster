package math.yl.love.database.service

import math.yl.love.common.mybatis.FileTypeEnum
import math.yl.love.common.utils.FileUtils.getMd5
import math.yl.love.configuration.config.SystemConfig
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.SystemFile
import math.yl.love.database.domain.params.system.GenerateParam
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.pathString

@Service
@Transactional(readOnly = true)
class SystemService(
    private val autoMysqlService: AutoMysqlService,
    private val systemConfig: SystemConfig,
    private val systemFileService: SystemFileService
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val uploadPath = Paths.get(systemConfig.uploadPath!!).apply {
        if (!Files.exists(this)) {
            Files.createDirectories(this)
        }
    }

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
    fun uploadFile(file: MultipartFile): Long {
        return try {
            val byteArray = file.inputStream.use { inputStream ->
                BufferedInputStream(inputStream).use { bufferedInputStream ->
                    bufferedInputStream.readBytes()
                }
            }
            // 计算 MD5
            val md5 = getMd5(byteArray.inputStream())

            val res = systemFileService.findByMd5(md5)
            val target = uploadPath.resolve(md5)

            /**
             * 同文件只写入一次
             */
            if (res.isEmpty()) {
                Files.write(target, byteArray)
            }

            val systemFile = SystemFile(
                md5 = md5,
                filename = file.originalFilename!!,
                path = target.pathString,
                type = FileTypeEnum.getFileTypeByName(file.originalFilename!!)
            )
            systemFileService.create(systemFile)

            systemFile.id!!
        } catch (e: Exception) {
            throw BizException("文件上传失败! ${e.message}")
        }
    }

//    fun getFile(filename: String): ResponseEntity<UrlResource> {
//        return try {
//            val filePath = uploadDirectory.resolve(filename).normalize()
//            val resource = UrlResource(filePath.toUri())
//
//            if (resource.exists() || resource.isReadable) {
//                ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
//                    .body(resource)
//
//            } else {
//                ResponseEntity.notFound().build()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ResponseEntity.internalServerError().build()
//        }
//    }
}