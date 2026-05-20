package com.nhnacademy.team4.taskapi.milestone.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.application.command.CreateMilestoneCommand;
import com.nhnacademy.team4.taskapi.milestone.application.command.UpdateMilestoneCommand;
import com.nhnacademy.team4.taskapi.milestone.application.usecase.CreateMilestoneUseCase;
import com.nhnacademy.team4.taskapi.milestone.application.usecase.DeleteMilestoneUseCase;
import com.nhnacademy.team4.taskapi.milestone.application.usecase.UpdateMilestoneUseCase;
import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MilestoneCommandService implements CreateMilestoneUseCase, UpdateMilestoneUseCase, DeleteMilestoneUseCase {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public void createMilestone(CreateMilestoneCommand command) {

        Project project = projectRepository.findById(command.projectId())
                .orElseThrow(()->new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        // 해당 프로젝트 멤버인지 검증
        validateProjectMember(command.projectId(), command.requesterMemberId());

        // 프로젝트내에 해당 마일스톤명 중복여부 검증
        validateDuplicateMilestoneName(command.projectId(),command.name());

        Milestone mileStone = Milestone.create(project, command.name(),command.dueDate());
        milestoneRepository.save(mileStone);
    }

    @Override
    public void deleteMilestone(Long milestoneId, Long requesterMemberId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MILESTONE_NOT_FOUND));

        // 해당 프로젝트 멤버인지 검증
        validateProjectMember(milestone.getProjectId(),requesterMemberId);

        milestoneRepository.delete(milestone);

    }

    @Override
    public void updateMilestone(UpdateMilestoneCommand command) {

        Milestone milestone = milestoneRepository.findById(command.milestoneId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MILESTONE_NOT_FOUND));

        //해당 프로젝트 멤버인지 검증
        validateProjectMember(milestone.getProjectId(), command.requesterMemberId());

        milestone.update(command.name(),command.dueDate());

    }

    private void validateProjectMember(Long projectId, Long memberId) {
        if (!projectMemberRepository.existsByProjectIdAndMemberId(projectId, memberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }

    private void validateDuplicateMilestoneName(Long projectId, String name) {
        boolean exists = milestoneRepository
                .existsByProject_IdAndName(projectId, name);

        if (exists) {
            throw new BusinessException(ErrorCode.MILESTONE_ALREADY_EXISTS);
        }
    }

}
