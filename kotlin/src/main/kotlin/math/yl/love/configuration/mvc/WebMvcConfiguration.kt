package math.yl.love.configuration.mvc

import math.yl.love.configuration.config.JsonConfig.Companion.json
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebMvcConfiguration: WebMvcConfigurationSupport() {

    private val log = LoggerFactory.getLogger(WebMvcConfiguration::class.java)

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
    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        /**
         * 替换原来的配置，添加自定义配置
         */
        converters.indexOfFirst { it is KotlinSerializationJsonHttpMessageConverter }
            .takeIf { it != -1 }
            ?.let { index ->
                converters[index] = kotlinSerializationJsonHttpMessageConverter()
            }
    }

    @Bean
    fun kotlinSerializationJsonHttpMessageConverter(): KotlinSerializationJsonHttpMessageConverter {
        return KotlinSerializationJsonHttpMessageConverter(json)
    }
}