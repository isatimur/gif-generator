package xyz.isatimur.blog.gif.generator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import xyz.isatimur.blog.gif.generator.GifGeneratorProperties;

import javax.inject.Inject;

/**
 * Created by isati on 18.01.2017.
 */
@Component
public class GifGeneratorHealthIndicator implements HealthIndicator {
    @Inject
    private GifGeneratorProperties properties;

    @Override
    public Health health() {
        if(!properties.getGifLocation().canWrite()){
            return Health.down().build();
        }
        return Health.up().build();
    }
}
