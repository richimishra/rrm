package com.project.project1.Controller;


import com.amazonaws.services.s3.model.S3Object;
import com.project.project1.Entity.User;
import com.project.project1.Service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BucketController {

    @Autowired
    private BucketService bucketService;
    @Autowired
    private static User user=new User();

    @Autowired
    BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    List list=new ArrayList();

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, ModelMap modelMap) throws IOException {
        System.out.println("in controller");
        System.out.println(user.getUser_id());
         String str= this.bucketService.uploadFile(file);
         String id=user.getUser_id();
         System.out.println(id);
        bucketService.save(id,file.getOriginalFilename());
        modelMap.addAttribute("tinyURL",str);
        List<String> temp = this.bucketService.listFiles();
        modelMap.addAttribute("fileList",temp);
        return "dashboard";
    }

    @PostMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileName) {
         this.bucketService.deleteFileFromS3Bucket(fileName);
         return "result";
    }
    @PostMapping("/showFile")
    public String listFile(ModelMap modelMap) throws IOException {
       List<String> temp = this.bucketService.listFiles();

         modelMap.addAttribute("fileList",temp);
        return "dashboard";
    }
    @PostMapping("/play")
    public String showVideos(ModelMap modelMap){
        S3Object[] s3Object=this.bucketService.showVideos();
        System.out.println(s3Object);
        modelMap.addAttribute("videos",s3Object);
        return "dashboard";
    }


    @GetMapping("/dashboard/{str}")
    public String dashboard(@PathVariable String str) {
        user.setUser_id(str);
        System.out.println(str);
        return "dashboard";
    }

}

