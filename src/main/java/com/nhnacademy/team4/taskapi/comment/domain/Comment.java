package com.nhnacademy.team4.taskapi.comment.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseTimeEntity;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "writer_member_id", nullable = false)
    private Long writerMemberId;

    private String content;

    public Long getTaskId() {
        return task.getId();
    }

    @Builder
    private Comment(Task task, Long writerMemberId, String content) {
        this.task = task;
        this.writerMemberId = writerMemberId;
        this.content = content;
    }

    public static Comment create(Task task,String content, Long writerMemberId) {
        return Comment.builder()
                .task(task)
                .writerMemberId(writerMemberId)
                .content(content)
                .build();
    }

    public void update(String content) {
        if(content != null) {
            this.content = content;
        }
    }


}
