package math.yl.love.database.service

import math.yl.love.common.constant.DataBaseConstant
import math.yl.love.common.constant.GenerateMapperConstant
import math.yl.love.common.utils.StrUtils.snakeToCamel
import math.yl.love.configuration.config.SystemConfig
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.params.system.CreateTableFileParam
import math.yl.love.database.domain.params.system.GenerateParam
import math.yl.love.database.mapper.SystemMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File

/**
 * MySql 自动生成代码service
 *
 */
@Service
@Transactional(readOnly = true)
class AutoMysqlService(
    private val systemMapper: SystemMapper,
    systemConfig: SystemConfig
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val dbPath = systemConfig.projectPath?.also {
        if (it.endsWith("/")) {
            it.dropLast(1)
        }
    }

    /**
     * 数据库类型转kotlin type
     */
    fun String.toKotlinType(): String {
        return when {
            startsWith("bigint") -> "Long"
            startsWith("int") -> "Int"
            startsWith("tinyint") -> "Int"
            startsWith("varchar") || startsWith("text") -> "String"
            startsWith("datetime") || startsWith("timestamp") -> "LocalDateTime"
            startsWith("float") -> "Float"
            startsWith("double") -> "Double"
            startsWith("decimal") -> "BigDecimal"
            startsWith("boolean") -> "Boolean"
            else -> "String"
        }
    }

    /**
     * 创建生成的代码文件
     * 如果需要替换原来的内容请删除存在检测
     */
    fun createFile(path: String, override: Boolean): File {
        return File(path).apply {
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
            if (!override && exists()) {
                val msg = "$path 已存在!!"
                log.error(msg)
                throw BizException(msg)
            }
            createNewFile()
        }
    }

    /**
     * 生成代码测试
     */
    fun generate(param: GenerateParam): String {
        val baseFields = listOf(
            "id",
            DataBaseConstant.DELETED,
            DataBaseConstant.UPDATE_BY,
            DataBaseConstant.UPDATE_TIME,
            DataBaseConstant.CREATE_BY,
            DataBaseConstant.CREATE_TIME
        )

        val res = systemMapper.getColumnInfo(param.dataBaseName, param.tableName).filter {
            !baseFields.contains(it.field)
        }

        if (res.isEmpty()) {
            throw BizException("${param.tableName} 表中字段为空!!")
        }

        if (dbPath == null) {
            throw BizException("请配置数据目录!!!")
        }

        val entityName = param.tableName.snakeToCamel(false)
        val createParam = CreateTableFileParam(
            param.tableName,
            entityName,
            res[0].tableComment,
            column = res,
            override = param.override
        )

        createEntity(createParam)
        createMapper(createParam)
        createService(createParam)
        createController(createParam)

        return res.joinToString("\n")
    }

    /**
     * 创建实体类
     * 返回实体类名
     */
    fun createEntity(param: CreateTableFileParam) {
        val ( tableName, entityName, _, _, _, _, _, column, override ) = param
        val file = createFile("${dbPath}/${GenerateMapperConstant.ENTITY_PATH}/${entityName}.kt", override)
        val packageName = GenerateMapperConstant.ENTITY_PATH.replace("/", ".").dropLast(1)
        param.entity = "$packageName.$entityName"

        val content = """
            package $packageName

            import com.baomidou.mybatisplus.annotation.*
            import io.swagger.v3.oas.annotations.media.Schema
            import kotlinx.serialization.Contextual
            import kotlinx.serialization.Serializable
            import kotlinx.serialization.Transient
            import math.yl.love.common.constant.DataBaseConstant
            import math.yl.love.common.base.NoArg
            import kotlinx.serialization.builtins.LongAsStringSerializer
            import java.time.LocalDateTime

            @NoArg
            @Serializable
            @TableName("$tableName")
            data class $entityName (
                @TableId(type = IdType.ASSIGN_ID)
                @Schema(description = "主键id")
                @Serializable(with = LongAsStringSerializer::class)
                override var id: Long? = null,

                @Transient
                @TableLogic
                @TableField(DataBaseConstant.DELETED)
                @Schema(description = "是否被删除")
                val deleted: Boolean = false,

                @Contextual
                @Schema(description = "创建时间")
                @TableField(value = DataBaseConstant.CREATE_TIME, fill = FieldFill.INSERT)
                override val createTime: LocalDateTime? = null,

                @Schema(description = "创建用户")
                @TableField(value = DataBaseConstant.CREATE_BY, fill = FieldFill.INSERT)
                override val createBy: String? = null,

                @Contextual
                @Schema(description = "更新时间")
                @TableField(value = DataBaseConstant.UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
                override val updateTime: LocalDateTime? = null,

                @Schema(description = "更新用户")
                @TableField(value = DataBaseConstant.UPDATE_BY, fill = FieldFill.INSERT_UPDATE)
                override val updateBy: String? = null,


        """.trimIndent()

        val stringBuilder = StringBuilder(content)

        column.forEach {
            val defaultValue = when(it.defaultValue) {
                String -> "\"${it.defaultValue}\""
                null -> "null"
                else -> it.defaultValue
            }
            val isNull = when(it.isNullable) {
                "YES" -> "?"
                else -> ""
            }

            stringBuilder.appendLine("    @Schema(description = \"${it.comment}\")")
            stringBuilder.appendLine("    @TableField(value = \"${it.field}\")")
            if (it.isNullable == "YES" || defaultValue != "null") {
                stringBuilder.appendLine("    val ${it.field.snakeToCamel()}: ${it.type.toKotlinType()}${isNull} = ${defaultValue},")
            } else {
                stringBuilder.appendLine("    val ${it.field.snakeToCamel()}: ${it.type.toKotlinType()},")
            }

            stringBuilder.appendLine()
        }

        stringBuilder.appendLine(") : BaseEntity")

        file.writeText(stringBuilder.toString())
    }

    /**
     * 创建Mapper
     */
    fun createMapper(param: CreateTableFileParam) {
        val packageName = GenerateMapperConstant.MAPPER_PATH.replace("/", ".").dropLast(1)
        val ( _, entityName, _, entity, _, _, _, _, override ) = param.apply {
            param.mapper = "${packageName}.${entityName}Mapper"
        }
        val baseName = "${entityName}Mapper"
        val file = createFile("${dbPath}/${GenerateMapperConstant.MAPPER_PATH}/${baseName}.kt", override)

        val content = """
            package $packageName
            
            import com.baomidou.mybatisplus.core.mapper.BaseMapper
            import $entity
            import org.apache.ibatis.annotations.Mapper
            
            @Mapper
            interface ${baseName}: BaseMapper<${entityName}>
        """.trimIndent()

        file.writeText(content)
    }

    /**
     * 创建服务类
     */
    fun createService(param: CreateTableFileParam) {
        val packageName = GenerateMapperConstant.SERVICE_PATH.replace("/", ".").dropLast(1)
        val ( _, entityName, _, entity, mapper, _, _, _, override ) = param.apply {
            param.service = "${packageName}.${entityName}Service"
        }
        val baseName = "${entityName}Service"
        val file = createFile("${dbPath}/${GenerateMapperConstant.SERVICE_PATH}/${baseName}.kt", override)

        val content = """
            package $packageName

            import math.yl.love.common.mybatis.BaseService
            import $entity
            import $mapper
            import org.springframework.stereotype.Service
            import org.springframework.transaction.annotation.Transactional
            import kotlin.reflect.KClass
            
            @Service
            @Transactional(readOnly = true)
            class ${baseName}: BaseService<${entityName}, ${entityName}Mapper>() {
                override val entityClass: KClass<${entityName}> get() = ${entityName}::class
            }
        """.trimIndent()

        file.writeText(content)
    }

    /**
     * 创建服务类
     */
    fun createController(param: CreateTableFileParam) {
        val packageName = GenerateMapperConstant.CONTROLLER_PATH.replace("/", ".").dropLast(1)
        val ( tableName, entityName, tableComment, entity, mapper, service, _, _, override ) = param.apply {
            param.controller = "${packageName}.${entityName}Controller"
        }
        val baseName = "${entityName}Controller"
        val file = createFile("${dbPath}/${GenerateMapperConstant.CONTROLLER_PATH}/${baseName}.kt", override)

        val content = """
            package $packageName

            import io.swagger.v3.oas.annotations.tags.Tag
            import math.yl.love.common.mybatis.BaseController
            import $entity
            import $mapper
            import $service
            import org.springframework.web.bind.annotation.RequestMapping
            import org.springframework.web.bind.annotation.RestController

            @RestController
            @RequestMapping("/${tableName.split("_").joinToString("/")}")
            @Tag(name = "$tableComment")
            class ${baseName}: BaseController<${entityName}, ${entityName}Mapper, ${entityName}Service>()
        """.trimIndent()

        file.writeText(content)
    }
}