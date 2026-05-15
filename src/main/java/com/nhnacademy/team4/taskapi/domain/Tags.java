package com.nhnacademy.team4.taskapi.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags")
public class Tags extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(name = "project_id", nullable = false)
//    private Long projectId;

    @Column(name = "name",nullable = false)
    private String name;
}
