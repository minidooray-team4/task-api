package com.nhnacademy.team4.taskapi.tags.infrastructure.persistence;

import com.nhnacademy.team4.taskapi.tags.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
}
