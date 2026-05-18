package com.nhnacademy.team4.taskapi.project.web.response;

import com.nhnacademy.team4.taskapi.milestone.web.response.MilestoneResponse;
import com.nhnacademy.team4.taskapi.task.web.response.TaskSummaryResponse;

import java.util.List;

public record ProjectDetailResponse(
        ProjectSummaryResponse project,
        List<ProjectMemberResponse> members,
        List<TaskSummaryResponse> tasks,
        List<TagResponse> tags,
        List<MilestoneResponse> milestones
) {

}
