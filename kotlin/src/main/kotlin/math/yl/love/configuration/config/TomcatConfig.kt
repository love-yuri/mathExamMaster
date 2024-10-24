package math.yl.love.configuration.config

import org.apache.catalina.connector.Connector
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class TomcatConfig {

    @Bean
    fun webServerFactory(): TomcatServletWebServerFactory {
        val factory = TomcatServletWebServerFactory()
        factory.addConnectorCustomizers(TomcatConnectorCustomizer { connector: Connector ->
            connector.setProperty("relaxedPathChars", "\"#<>[\\]^`{|}")
            connector.setProperty("relaxedQueryChars", "\"#<>[\\]^`{|}")
        })
        return factory
    }
}