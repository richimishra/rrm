package com.project.project1.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.project.project1.Entity.URLShortenerDTO;
import com.project.project1.Entity.Videos;
import com.project.project1.Repository.MediaRepo;
import com.project.project1.Repository.UserRepo;
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

    private String accessKey ="AKIAJNHWYP6OXC5EWHFQ";
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

    public void save(String id, String video) {
        Videos videos=new Videos(id,video);
        mediaRepo.save(videos);
    }


}



