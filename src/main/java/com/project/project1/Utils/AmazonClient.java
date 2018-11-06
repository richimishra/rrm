package com.project.project1.Utils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.project.project1.Service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

import static org.aspectj.bridge.MessageUtil.info;

@Component
public class AmazonClient {

    private String bucketName ="rrm-videos";
    private FileEncoder fileEncoder;
    @Autowired
    private URLService urlService;

    public AmazonClient(FileEncoder fileEncoder) {
        this.fileEncoder = fileEncoder;
    }


    public String uploadFile(MultipartFile multipartFile, AmazonS3 s3client) {
        System.out.println("in a client");
        Map map = new HashMap();
        String fileName=null;
        String fileUrl = "";
        String sr = null;
        try {

            File target = fileEncoder.encodeFile(multipartFile);
            fileName = multipartFile.getOriginalFilename();
            fileUrl = "https://s3.us-east-2.amazonaws.com" + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, target, s3client);
            target.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sr = urlService.saveUrl(fileUrl).getShortenedURL();
        return sr;
    }



    private String generateFileName(MultipartFile target) {

        return target.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file,AmazonS3 s3client) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void deleteFileFromS3Bucket(String fileName, AmazonS3 s3client) {

        System.out.println(fileName);

        s3client.deleteObject(new DeleteObjectRequest(bucketName , fileName));


    }

    public List<String> listObjects(AmazonS3 s3client) throws IOException {
        ObjectListing objectListing = s3client.listObjects(bucketName);


        List<String> stringList = new ArrayList<>();
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            stringList.add(os.getKey());
                    }
        System.out.println(stringList);
        return stringList;

    }

    public S3Object[] showObjects(AmazonS3 s3client)
    {
        ObjectListing objectListing = s3client.listObjects(bucketName);
        S3Object[] fullObject=null;
        int i=0;
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            fullObject[i] = s3client.getObject(new GetObjectRequest(bucketName, os.getKey()));
            i++;
            }
            return fullObject;
}
    public void downloadFile(String keyName, AmazonS3 s3client) throws IOException {
        try {

            System.out.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            System.out.println("Content-Type: "  + s3object.getObjectMetadata().getContentType()+s3object.getRedirectLocation());
            final BufferedInputStream i = new BufferedInputStream(s3object.getObjectContent());
            InputStream objectData = s3object.getObjectContent();
            Files.copy(objectData, new File("E:\\rimishra\\IdeaProjects\\"+keyName).toPath()); //location to local path
            objectData.close();

        } catch (AmazonServiceException ase) {
            System.out.println(ase.getMessage());

        } catch (AmazonClientException ace) {

        }
    }
}