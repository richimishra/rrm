package com.project.project1.Utils;

import it.sauronsoftware.jave.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileEncoder {
    public File encodeFile(MultipartFile multipartFile) throws EncoderException, IOException {
        File source = convertMultiPartToFile(multipartFile);
        File target=new File("target.3gp");
        VideoAttributes video = new VideoAttributes();
        video.setCodec("mpeg4");
        video.setBitRate(new Integer(160000));
        video.setFrameRate(new Integer(15));
        video.setSize(new VideoSize(100, 100));

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setVideoAttributes(video);
        attrs.setFormat("3gp");
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);

        return target;

    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
