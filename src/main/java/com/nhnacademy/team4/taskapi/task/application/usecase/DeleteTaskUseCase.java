package com.nhnacademy.team4.taskapi.task.application.usecase;

public interface DeleteTaskUseCase {
    void deleteTask(Long taskId,Long requesterMemberId);
}
