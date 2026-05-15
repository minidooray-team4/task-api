package com.nhnacademy.team4.taskapi.tags.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseTimeEntity;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.swing.text.html.HTML;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tags")
public class Tags extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "name",nullable = false)
    private String name;

    @Builder
    private Tags(Project project,String name){
        this.project=project;
        this.name=name;
    }

    public static Tags create(Project project,String name){
        return Tags.builder()
                .project(project)
                .name(name)
                .build();
    }
}
