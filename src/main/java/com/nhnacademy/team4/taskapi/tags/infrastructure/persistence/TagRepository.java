package com.nhnacademy.team4.taskapi.tags.infrastructure.persistence;

import com.nhnacademy.team4.taskapi.tags.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    List<Tag> findAllByProjectId(Long projectId);
    boolean existsByProjectIdAndName(Long projectId,String name);
}
