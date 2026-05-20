package com.nhnacademy.team4.taskapi.project.application.result;

import com.nhnacademy.team4.taskapi.project.domain.Project;

public record CreateProjectResult(Long id) {
    public static CreateProjectResult from(Project saved) {
        return new CreateProjectResult(saved.getId());
    }
}
