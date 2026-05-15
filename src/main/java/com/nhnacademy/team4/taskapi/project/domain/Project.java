package com.nhnacademy.team4.taskapi.project.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseCreatedAtEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "projects")
public class Project extends BaseCreatedAtEntity {

    public enum Status {
        ACTIVE,DORMANT,CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "admin_member_id",nullable = false)
    private Long adminMemberId;

    
}
