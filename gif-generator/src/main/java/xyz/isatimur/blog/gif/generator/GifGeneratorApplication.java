package xyz.isatimur.blog.gif.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;

@SpringBootApplication(exclude = {WebSocketAutoConfiguration.class, JmxAutoConfiguration.class, JacksonAutoConfiguration.class})
public class GifGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GifGeneratorApplication.class, args);
    }



}
