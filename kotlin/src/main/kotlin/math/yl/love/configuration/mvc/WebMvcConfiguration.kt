package math.yl.love.configuration.mvc

import com.alibaba.druid.support.json.JSONWriter
import kotlinx.serialization.json.Json
import math.yl.love.common.base.Log.log
import math.yl.love.configuration.config.JsonConfig
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.*
import java.nio.charset.StandardCharsets


@Configuration
class WebMvcConfiguration: WebMvcConfigurationSupport() {

    public override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/", "/student/index.html")
        registry.addRedirectViewController("/student", "/student/index.html")
        registry.addRedirectViewController("/admin", "/admin/index.html")
    }

    /**
     * 静态文件访问设置
     *
     */
    public override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .setCachePeriod(31556926)

        // 添加Knife4j的静态资源路径
        registry.addResourceHandler("doc.html")
            .addResourceLocations("classpath:/META-INF/resources/")

        // 添加webjars路径
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")

    }

    /**
     * 跨域设置
     * 允许所有请求
     */
    public override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowCredentials(true)
            .allowedMethods("*")
            .allowedOriginPatterns("*")
            .allowedHeaders("*")
        super.addCorsMappings(registry)
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        // 1. 配置 kotlinx.serialization
        val json = JsonConfig.json

        // 2. 添加 KotlinSerializationJsonHttpMessageConverter
        val converter = object : KotlinSerializationJsonHttpMessageConverter(json) {}

        // 3. 添加支持的媒体类型
        val supportedMediaTypes = mutableListOf(
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_ATOM_XML,
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_OCTET_STREAM,
            MediaType.APPLICATION_PDF,
            MediaType.APPLICATION_RSS_XML,
            MediaType.APPLICATION_XHTML_XML,
            MediaType.APPLICATION_XML,
            MediaType.IMAGE_GIF,
            MediaType.IMAGE_JPEG,
            MediaType.IMAGE_PNG,
            MediaType.TEXT_EVENT_STREAM,
            MediaType.TEXT_HTML,
            MediaType.TEXT_MARKDOWN,
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_XML
        )

        converter.supportedMediaTypes = supportedMediaTypes

        // 4. 将 converter 添加到 converters 列表中
        converters.add(0, converter)
    }
}