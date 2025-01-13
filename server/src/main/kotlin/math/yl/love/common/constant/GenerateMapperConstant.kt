package math.yl.love.common.constant

/**
 * 生成Mybatis-plus所需类
 * 常用变量
 */
object GenerateMapperConstant {
    /**
     * mybatis-plus 常用类基础目录
     */
    private const val BATH_PATH = "math/yl/love"

    /**
     * mapper 所在路径
     */
    const val MAPPER_PATH = "${BATH_PATH}/database/mapper/"

    /**
     * Service所在路径
     */
    const val SERVICE_PATH = "${BATH_PATH}/database/service/"

    /**
     * 实体类所在路径
     */
    const val ENTITY_PATH = "${BATH_PATH}/database/domain/entity/"

    /**
     * 控制类所在路径
     */
    const val CONTROLLER_PATH = "${BATH_PATH}/controller/"
}