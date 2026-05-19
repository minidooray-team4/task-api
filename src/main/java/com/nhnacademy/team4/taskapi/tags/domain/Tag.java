package com.nhnacademy.team4.taskapi.tags.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseTimeEntity;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tags")
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "name",nullable = false)
    private String name;

    @Builder
    private Tag(Project project, String name){
        this.project=project;
        this.name=name;
    }

    public static Tag create(Project project, String name){
        return Tag.builder()
                .project(project)
                .name(name)
                .build();
    }

    public void rename(String name){
        this.name=name;
    }
}
