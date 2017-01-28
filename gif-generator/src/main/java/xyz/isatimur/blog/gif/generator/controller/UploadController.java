package xyz.isatimur.blog.gif.generator.controller;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.isatimur.blog.gif.generator.services.ConverterService;
import xyz.isatimur.blog.gif.generator.services.GifEncoderService;
import xyz.isatimur.blog.gif.generator.services.VideoDecoderService;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by isati on 17.01.2017.
 */
@RestController
public class UploadController {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${spring.http.multipart.location}")
    private String location;

    @Inject
    ConverterService converterService;

    @Inject
    GifEncoderService gifEncoderService;

    @Inject
    VideoDecoderService videoDecoderService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.IMAGE_GIF_VALUE)
    public String upload(@RequestPart("file") MultipartFile file,
                         @RequestParam("start") int start,
                         @RequestParam("end") int end,
                         @RequestParam("speed") int speed,
                         @RequestParam("repeat") boolean repeat) throws IOException, FrameGrabber.Exception {
        File videoFile = new File(location + System.currentTimeMillis() + ".mp4");
        file.transferTo(videoFile);
        log.info("Saved file to {} ", videoFile.getAbsolutePath());


        Path output = Paths.get(location + "/gif/").resolve(System.currentTimeMillis() + ".gif");

        FFmpegFrameGrabber frameGrabber = videoDecoderService.read(videoFile);
        AnimatedGifEncoder gifEncoder = gifEncoderService.getGifEncoder(repeat,
                (float) frameGrabber.getFrameRate(), output);
        converterService.toAnimatedGif(frameGrabber, gifEncoder, start, end, speed);

        log.info("Saved generated gif to {}", output.toString());

        return output.getFileName().toString();


    }
}
