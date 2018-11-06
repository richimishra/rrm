package com.project.project1.Controller;


import com.amazonaws.services.s3.model.S3Object;
import com.project.project1.Entity.User;
import com.project.project1.Entity.Videos;
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
         bucketService.save(id,file.getOriginalFilename());
        modelMap.addAttribute("tinyURL",str);
        List<Videos> temp = this.bucketService.listFiles(user.getUser_id());
        List<String> names = new ArrayList<>();
        for (Videos videos : temp) {
            names.add(videos.getVideo());
        }
        modelMap.addAttribute("fileList",names);

        return "dashboard";
    }

    @PostMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileName) {
         this.bucketService.deleteFileFromS3Bucket(fileName);
         return "result";
    }
    @PostMapping("/showFile")
    public String listFile(ModelMap modelMap) throws IOException {

        String str=user.getUser_id();
        this.bucketService.listFiles(str);
        return "dashboard";
    }
    @PostMapping("/play")
    public String showVideos(ModelMap modelMap){
        S3Object[] s3Object=this.bucketService.showVideos();
        System.out.println(s3Object);
        modelMap.addAttribute("videos",s3Object);
        return "dashboard";
    }

    @PostMapping("/download")
    public String downloadFile(@RequestPart(value = "url") String key) throws IOException {
        bucketService.downloadFile(key);
        return "res";
    }


    @GetMapping("/dashboard/{str}")
    public String dashboard(@PathVariable String str, ModelMap modelMap) {
        user.setUser_id(str);
        System.out.println(str);
        List<Videos> temp = this.bucketService.listFiles(str);
        List<String> names = new ArrayList<>();
        for (Videos videos : temp) {
            names.add(videos.getVideo());
        }
        modelMap.addAttribute("fileList",names);
        modelMap.addAttribute("str",str);
        return "dashboard";
    }
    @GetMapping("/dashboard")
    public String dashboard(ModelMap modelMap )
    {
        List<Videos> temp = this.bucketService.listFiles(user.getUser_id());
        List<String> names = new ArrayList<>();
        for (Videos videos : temp) {
            names.add(videos.getVideo());
        }
        modelMap.addAttribute("fileList",names);
        return "dashboard";
    }

}

