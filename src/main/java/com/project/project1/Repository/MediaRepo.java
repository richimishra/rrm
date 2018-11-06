package com.project.project1.Repository;

import com.project.project1.Entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepo extends JpaRepository<Videos,String> {
    List<Videos> findByuid(String uid);
    List<Videos> findByvideo(String name);
//    void deleteByvideo(String name);

//    void deleteByvideo(String fileName);
}
