package math.yl.love.database.service

import com.alibaba.druid.sql.dialect.odps.ast.OdpsAddFileStatement.FileType
import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.mybatis.FileTypeEnum
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.SystemFile
import math.yl.love.database.mapper.SystemFileMapper
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class SystemFileService: BaseService<SystemFile, SystemFileMapper>() {
    override val entityClass: KClass<SystemFile> get() = SystemFile::class

    /**
     * 根据md5 查找数据
     */
    fun findByMd5(md5: String) = list(queryWrapper.eq(SystemFile::md5, md5))

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
}