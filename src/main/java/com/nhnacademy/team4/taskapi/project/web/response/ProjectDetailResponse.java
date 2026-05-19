package com.nhnacademy.team4.taskapi.project.web.response;

import com.nhnacademy.team4.taskapi.milestone.web.response.MilestoneResponse;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectDetailResult;
import com.nhnacademy.team4.taskapi.tags.web.response.TagResponse;
import com.nhnacademy.team4.taskapi.task.web.response.TaskSummaryResponse;

import java.util.List;

public record ProjectDetailResponse(
        ProjectSummaryResponse project,
        List<ProjectMemberResponse> members,
        List<TaskSummaryResponse> tasks,
        List<TagResponse> tags,
        List<MilestoneResponse> milestones
) {

    public static ProjectDetailResponse from(ProjectDetailResult result) {
        return new ProjectDetailResponse(
                ProjectSummaryResponse.from(result.projectSummaryResult()),

                result.members().stream()
                        .map(ProjectMemberResponse::from)
                        .toList(),

                result.tasks().stream()
                        .map(TaskSummaryResponse::from)
                        .toList(),

                result.tags().stream()
                        .map(TagResponse::from)
                        .toList(),

                result.milestones().stream()
                        .map(MilestoneResponse::from)
                        .toList()
        );
    }
}
