package com.nhnacademy.team4.taskapi.milestone.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;
import com.nhnacademy.team4.taskapi.milestone.application.usecase.GetProjectMilestonesUseCase;
import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MilestoneQueryService implements GetProjectMilestonesUseCase {
    private final MilestoneRepository milestoneRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public List<MilestoneResult> getProjectMilestones(Long projectId, Long requesterMemberId) {
        validateProjectMember(projectId, requesterMemberId);

        List<Milestone> milestoneList = milestoneRepository.findByProject_Id(projectId);

        return milestoneList.stream()
                .map(MilestoneResult::from)
                .toList();
    }

    private void validateProjectMember(Long projectId, Long memberId) {
        if (!projectMemberRepository.existsByProjectIdAndMemberId(projectId, memberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
