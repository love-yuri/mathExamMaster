package math.yl.love.configuration.mvc

import cn.dev33.satoken.`fun`.SaFunction
import cn.dev33.satoken.interceptor.SaInterceptor
import cn.dev33.satoken.router.SaHttpMethod
import cn.dev33.satoken.router.SaRouter
import cn.dev33.satoken.stp.StpUtil
import math.yl.love.configuration.config.JsonConfig.Companion.json
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.web.servlet.config.annotation.*


@Configuration
class WebMvcConfiguration: WebMvcConfigurationSupport() {

    private val log = LoggerFactory.getLogger(WebMvcConfiguration::class.java)

    public override fun addInterceptors(registry: InterceptorRegistry) {

        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(SaInterceptor {
            // option请求直接放行
            SaRouter.match(SaHttpMethod.OPTIONS).back()

            // 所有接口进行登录校验
            StpUtil.checkLogin()
        })
        // 对所有接口进行校验
        .addPathPatterns("/**")

        // 放行文件查看接口
        .excludePathPatterns("/system/file/get/**")

        // 放行登录/退出登录接口
        .excludePathPatterns("/user/login")
        .excludePathPatterns("/user/logout")

        // 放行Knife4j文档
        .excludePathPatterns("/doc.html")
        .excludePathPatterns("/swagger-resources/**")
        .excludePathPatterns("/webjars/**")
        .excludePathPatterns("/v2/api-docs/**")
        .excludePathPatterns("/v3/api-docs/**")
    }

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