package com.travelmaker.repository;

import com.travelmaker.entity.PostnHashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostnTagRepository extends JpaRepository<PostnHashtagEntity, String> {

}
