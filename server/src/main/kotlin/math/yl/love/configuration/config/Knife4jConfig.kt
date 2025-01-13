package math.yl.love.configuration.config

import io.swagger.v3.core.util.AnnotationsUtils.getTags
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.servers.Server
import math.yl.love.common.constant.HeadersConstant
import org.springdoc.core.customizers.GlobalOperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.method.HandlerMethod


@Configuration
class Knife4jConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI().info(
            Info().title("API Documentation")
                .version("1.0")
                .description("API Documentation with global header")
        ).addServersItem(Server().url("/"))
    }

    @Bean
    fun globalOperationCustomizer(): GlobalOperationCustomizer {
        return GlobalOperationCustomizer { operation: Operation, _: HandlerMethod? ->
            val authorizationHeader =
                Parameter().`in`("header").schema(StringSchema()).name(HeadersConstant.AUTHORIZATION).description("JWT Token")
                    .required(false)
            operation.addParametersItem(authorizationHeader)
            operation
        }
    }


}
