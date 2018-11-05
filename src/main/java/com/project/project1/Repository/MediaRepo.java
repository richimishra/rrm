package com.project.project1.Repository;

import com.project.project1.Entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepo extends JpaRepository<Videos,Integer> {
}
