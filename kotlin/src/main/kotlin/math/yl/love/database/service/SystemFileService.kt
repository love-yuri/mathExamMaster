package math.yl.love.database.service

import com.aspose.imaging.Image
import com.aspose.imaging.License
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
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.pathString
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class SystemFileService(
    systemConfig: SystemConfig
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
    fun findByMd5(md5: String) = list(queryWrapper.eq(SystemFile::md5, md5))

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
        ).apply { create(this) }
    }

    /**
     * 根据文件id获取文件访问路径
     * @param fileId 文件id
     */
    fun getFile(fileId: Long): ResponseEntity<UrlResource> {
        val file = getById(fileId) ?: throw BizException("该文件不存在!!")
        return try {
            val filePath = Paths.get(file.path)
            val resource = UrlResource(filePath.toUri())

            if (resource.exists() || resource.isReadable) {
                ResponseEntity.ok()
                    .contentType(FileTypeEnum.getMediaType(file.filename))
                    .body(resource)

            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.internalServerError().build()
        }
    }

    /**
     * 将wmf文件转为图像文件
     * @param file wmf文件
     */
    @Transactional(rollbackFor = [Exception::class])
    fun wmfToJpg(file: MultipartFile): SystemFile {
        License().setLicense(ByteArrayInputStream(ByteArray(0)))
        val outPutStream = ByteArrayOutputStream()

        // 保存文件
        Image.load(file.inputStream).also {
            val option = FileTypeEnum.getImgOption(file.originalFilename ?: "temp.jpg")
            it.save(outPutStream, option)
            it.dispose()
        }

        // 上传文件
        return uploadFile(outPutStream.toByteArray(), file.originalFilename ?: "temp.jpg")
    }
}