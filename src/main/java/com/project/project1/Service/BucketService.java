package com.project.project1.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.project.project1.Entity.URLShortenerDTO;
import com.project.project1.Entity.Videos;
import com.project.project1.Repository.MediaRepo;
import com.project.project1.Repository.UserRepo;
import com.project.project1.Utils.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

@Service
public class BucketService {

    private AmazonS3 s3client;
    @Autowired
    public AmazonClient amazonClient;
    @Autowired
    public URLService urlService;
    @Autowired
    public URLShortenerDTO urlShortenerDTO;
    @Autowired
    public MediaRepo mediaRepo;

    @Value("${bucketName}")
    String bucketName;

    @Autowired
    public UserRepo userRepo;
    @Autowired
    BucketService(){
        this.initializeAmazon();}

//    private String accessKey ="AKIAIL7EKDPY4CJ4GHOQ";
//    private String secretKey ="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";


    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }
    public String uploadFile(MultipartFile multipartFile)
    {
        System.out.println("in service");
       return amazonClient.uploadFile(multipartFile,s3client);
    }
    public void deleteFileFromS3Bucket(String fileName)
    {

        amazonClient.deleteFileFromS3Bucket(fileName,s3client);
        List<Videos> video = mediaRepo.findByvideo(fileName);
        for (Videos videos: video) {
            mediaRepo.delete(videos);
        }
    }




    public S3Object[]     showVideos() {
    return amazonClient.showObjects(s3client);
    }

    public void save(String uid, String video) {
        Videos videos=new Videos(uid,video);

        mediaRepo.save(videos);

    }

    public List<Videos> listFiles(String str) {
         return mediaRepo.findByuid(str);
    }


    public void downloadFile(String keyName) throws IOException {

       amazonClient.downloadFile(keyName,s3client);
    }
}



