package com.project.project1.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.project.project1.Entity.URLShortenerDTO;
import com.project.project1.Entity.Videos;
import com.project.project1.User.dao.MediaRepo;
import com.project.project1.User.dao.UserRepo;
import com.project.project1.Utils.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@Component
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
    @Autowired
    public UserRepo userRepo;
    @Autowired
    BucketService(){
        this.initializeAmazon();}

    private String accessKey ="AKIAIUX34GLWXN2DT5QA";
    private String secretKey ="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";


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

    }


    public List<String> listFiles() throws IOException {
        return amazonClient.listObjects(s3client);
    }

    public S3Object[]     showVideos() {
    return amazonClient.showObjects(s3client);
    }

    public void save(String file) {
        userRepo
        Videos videos=new Videos(uid,video);
        mediaRepo.save(videos);
    }
}



