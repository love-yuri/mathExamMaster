package math.yl.love.database.service

import math.yl.love.common.constant.RedisConstant
import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.typeEnum.FileTypeEnum
import math.yl.love.common.utils.FileUtils.getMd5
import math.yl.love.configuration.config.SystemConfig
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.SystemFile
import math.yl.love.database.mapper.SystemFileMapper
import org.springframework.core.io.UrlResource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.pathString
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class SystemFileService(
    systemConfig: SystemConfig,
    val redisService: RedisService
): BaseService<SystemFile, SystemFileMapper>() {
    override val entityClass: KClass<SystemFile> get() = SystemFile::class
    private val uploadPath = Paths.get(systemConfig.uploadPath ?: "files/upload").apply {
        if (!Files.exists(this)) {
            Files.createDirectories(this)
        }
    }


    /**
     * 根据md5 查找数据
     */
    fun findByMd5(md5: String): List<SystemFile> = list(queryWrapper.eq(SystemFile::md5, md5))

    /**
     * 上传文件并返回文件数据
     * @param byteArray 文件的数据
     * @param filename 文件名
     */
    fun uploadFile(byteArray: ByteArray, filename: String): SystemFile {
        // 计算 MD5
        val md5 = getMd5(byteArray)

        val res = findByMd5(md5)
        val target = uploadPath.resolve(md5)

        /**
         * 同文件只写入一次
         */
        if (res.isEmpty()) {
            Files.write(target, byteArray)
        }

        // 创建数据
        return SystemFile (
            md5 = md5,
            filename = filename,
            path = target.pathString,
            type = FileTypeEnum.getFileTypeByName(filename)
        ).apply { save(this) }
    }

    override fun getById(id: Serializable): SystemFile? {
        return redisService.getOrReSet("${RedisConstant.SYSTEM_FILE_GET}:$id", {
            super.getById(id)
        })
    }

    /**
     * 根据文件id获取文件访问路径
     * @param fileId 文件id
     */
    fun getFile(fileId: Long): ResponseEntity<Any> {
        val file = getById(fileId) ?: throw BizException("该文件不存在!!")
        return try {
            val filePath = Paths.get(file.path)
            val resource = UrlResource(filePath.toUri())

            if (resource.exists() || resource.isReadable) {
                ResponseEntity.ok()
                    .contentType(FileTypeEnum.getMediaType(file.filename))
                    .body(resource)

            } else {
                if (resource.exists()) {
                    ResponseEntity.ok("File(${filePath}) is not readable!!")
                } else {
                    ResponseEntity.ok("File(${filePath}) is not exist!!")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.internalServerError().build()
        }
    }
}