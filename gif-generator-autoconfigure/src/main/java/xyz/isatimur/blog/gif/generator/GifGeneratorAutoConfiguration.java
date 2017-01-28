package xyz.isatimur.blog.gif.generator;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import xyz.isatimur.blog.gif.generator.services.ConverterService;
import xyz.isatimur.blog.gif.generator.services.GifEncoderService;
import xyz.isatimur.blog.gif.generator.services.VideoDecoderService;

import javax.inject.Inject;

/**
 * Created by isati on 18.01.2017.
 */
@Configuration
@ConditionalOnClass({FFmpegFrameGrabber.class, AnimatedGifEncoder.class})
@EnableConfigurationProperties(GifGeneratorProperties.class)
public class GifGeneratorAutoConfiguration {

    @Inject
    private GifGeneratorProperties properties;


    @Bean
    @ConditionalOnProperty(prefix = "xyz.isatimur.blog.gif.generator", name = "create-result-dir")
    public Boolean createResultDir() {
        if (!properties.getGifLocation().exists()) {
            properties.getGifLocation().mkdirs();
        }

        return true;
    }

    @Bean
    @ConditionalOnMissingBean(VideoDecoderService.class)
    public VideoDecoderService videoDecoderService() {
        return new VideoDecoderService();
    }

    @Bean
    @ConditionalOnMissingBean(GifEncoderService.class)
    public GifEncoderService gifEncoderService() {
        return new GifEncoderService();
    }

    @Bean
    @ConditionalOnMissingBean(ConverterService.class)
    public ConverterService converterService() {
        return new ConverterService();
    }

    @Configuration
    @ConditionalOnWebApplication
    public static class WebConfiguretion {
        @Value("${xyz.isatimur.gif-location}")
        private String gifLocation;

        @Bean
        @ConditionalOnProperty(prefix = "xyz.isatimur.blog.gif.generator", name = "optimize")
        public FilterRegistrationBean deRegisterHiddenHttpMethodFilter(HiddenHttpMethodFilter hiddenHttpMethodFilter) {
            FilterRegistrationBean bean = new FilterRegistrationBean(hiddenHttpMethodFilter);
            bean.setEnabled(false);
            return bean;
        }

        @Bean
        @ConditionalOnProperty(prefix = "xyz.isatimur.blog.gif.generator", name = "optimize")
        public FilterRegistrationBean deRegisterHttpPutFormContentFilter(HttpPutFormContentFilter putFormContentFilter) {
            FilterRegistrationBean bean = new FilterRegistrationBean(putFormContentFilter);
            bean.setEnabled(false);
            return bean;
        }

        @Bean
        @ConditionalOnProperty(prefix = "xyz.isatimur.blog.gif.generator", name = "optimize")
        public FilterRegistrationBean deRegisterRequestContentFilter(RequestContextFilter requestContextFilter) {
            FilterRegistrationBean bean = new FilterRegistrationBean(requestContextFilter);
            bean.setEnabled(false);
            return bean;
        }

        @Bean
        public WebMvcConfigurer webMvcConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addResourceHandlers(ResourceHandlerRegistry registry) {
                    registry.addResourceHandler("/gif/**").addResourceLocations("file:" + gifLocation);
                    super.addResourceHandlers(registry);
                }
            };
        }
    }
}
