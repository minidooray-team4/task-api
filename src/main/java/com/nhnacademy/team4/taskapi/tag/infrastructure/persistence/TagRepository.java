package com.nhnacademy.team4.taskapi.tag.infrastructure.persistence;

import com.nhnacademy.team4.taskapi.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    List<Tag> findAllByProjectId(Long projectId);
    boolean existsByProjectIdAndName(Long projectId,String name);
}
