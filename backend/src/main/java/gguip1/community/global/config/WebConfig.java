package gguip1.community.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) {
        String uploadPath = "file:///" + System.getProperty("user.dir") + "/upload/images/";
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}
