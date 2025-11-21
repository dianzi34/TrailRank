@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Serve static images packaged in resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }

    // Allow frontend access
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8082")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
