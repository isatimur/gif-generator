package xyz.isatimur.blog.gif.generator;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * Created by isati on 18.01.2017.
 */
@ConfigurationProperties(prefix = "xyz.isatimur")
public class GifGeneratorProperties {
    /**
     * The location to animated gif file
     */
    File gifLocation;

    /**
     * Whether or not to optimize web filters
     */
    Boolean optimize;

    public File getGifLocation() {
        return gifLocation;
    }

    public void setGifLocation(File gifLocation) {
        this.gifLocation = gifLocation;
    }

    public Boolean getOptimize() {
        return optimize;
    }

    public void setOptimize(Boolean optimize) {
        this.optimize = optimize;
    }
}
