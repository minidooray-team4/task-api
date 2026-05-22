package com.nhnacademy.team4.taskapi.project.application.result;

import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;
import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.task.application.result.TaskSummaryResult;

import java.util.List;

public record ProjectDetailResult(
        ProjectSummaryResult projectSummaryResult,
        List<ProjectMemberResult> members,
        List<TaskSummaryResult> tasks,
        List<TagResult> tags,
        List<MilestoneResult> milestones
) {

}
