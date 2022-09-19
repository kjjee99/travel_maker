package com.travelmaker.repository;

import com.travelmaker.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<PostEntity, String> {
}
