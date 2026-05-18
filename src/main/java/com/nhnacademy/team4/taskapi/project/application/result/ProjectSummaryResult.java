package com.nhnacademy.team4.taskapi.project.application.result;

import com.nhnacademy.team4.taskapi.project.domain.Status;

public record ProjectSummaryResult(Long id, String name, Status status, Long adminMemberId) {
}
/*
usecase 추출 DTO Fields쪽에
ProjectDetailViewResult	project, members, tasks, tags, milestones
ProjectResult	id, name, status, adminMemberId
ProjectMemberResult	id, projectId, memberId
이런식으로 구성되어있는데

Task API UseCase DTO Field 쪽
ProjectResult	id, name, status, adminMemberId
ProjectSummaryResult	id, name, status, adminMemberId
ProjectDetailResult	id, name, status, adminMemberId
이쪽 부분이랑
API 생성쪽 파일
DTO 쪽
ProjectResponse	id, name, status, adminMemberId
ProjectSummaryResponse	id, name, status, adminMemberId
ProjectDetailResponse	id, name, status, adminMemberId
이쪽부분이 이렇게 되어있어서 이쪽 부분수정이 필요할것같습니다

 */