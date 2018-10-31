package com.project.project1.User.dao;

import com.project.project1.Entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepo extends JpaRepository<Videos,Long> {
}
