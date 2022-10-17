package com.travelmaker.repository;

import com.travelmaker.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity, String> {

    @Query(value = "select * from hashtags h where h.tag_name = :name",
            nativeQuery = true)
    Optional<HashtagEntity> findByName(@Param("name")String name);
}
