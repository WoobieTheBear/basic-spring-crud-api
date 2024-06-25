package ch.black.gravel;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({
    "ch.black.gravel",
    "ch.black.socket",
    "ch.black.util"
})
@ContextConfiguration(
    locations = { "classpath:applicationContext.xml" }
)
public class GravelWebTestConfig implements WebMvcConfigurer {}
