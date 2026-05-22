package com.nhnacademy.team4.taskapi.project.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseTimeEntity;

import jakarta.persistence.*;

import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

    @Builder
    private Project(String name,Status status,Long adminMemberId){
        this.name = name;
        this.status = status;
        this.adminMemberId = adminMemberId;
    }

    public static Project create(String name,Long adminMemberId) {
        return Project.builder()
                .name(name)
                .status(Status.ACTIVE)
                .adminMemberId(adminMemberId)
                .build();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "admin_member_id", nullable = false)
    private Long adminMemberId;

    public void update(String name,Status status){
        if(name != null){
            this.name = name;
        }

        if(status != null){
            this.status = status;
        }
    }

}
