package com.nhnacademy.team4.taskapi.milestone.application.usecase;

public interface DeleteMilestoneUseCase {
    void deleteMilestone(Long milestoneId, Long requesterMemberId);
}
