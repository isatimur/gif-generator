package xyz.isatimur.blog.gif.generator.services;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

/**
 * Created by isati on 18.01.2017.
 */
@Service
public class GifEncoderService {
    public AnimatedGifEncoder getGifEncoder(boolean repeat, float frameRate, Path output) {
        AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();

        if (repeat) {
            gifEncoder.setRepeat(0);
        }

        gifEncoder.setFrameRate(frameRate);
        gifEncoder.start(output.toString());
        return gifEncoder;
    }

}
