# Project

## UseCases

| UseCase                    | 입력                                         | 결과                           | 설명            |
| -------------------------- | ------------------------------------------ | ---------------------------- | ------------- |
| `CreateProjectUseCase`     | `CreateProjectCommand`                     | `ProjectSummaryResult`       | 프로젝트 생성       |
| `GetMyProjectsUseCase`     | `Long requesterMemberId`                   | `List<ProjectSummaryResult>` | 내 프로젝트 목록     |
| `GetProjectDetailUseCase`  | `Long projectId`, `Long requesterMemberId` | `ProjectDetailResult`        | 프로젝트 상세       |
| `UpdateProjectUseCase`     | `UpdateProjectCommand`                     | `ProjectSummaryResult`       | 프로젝트 이름/상태 수정 |
| `AddProjectMemberUseCase`  | `AddProjectMemberCommand`                  | `void`                       | 프로젝트 멤버 추가    |
| `GetProjectMembersUseCase` | `Long projectId`, `Long requesterMemberId` | `List<ProjectMemberResult>`  | 프로젝트 멤버 목록    |

---

## Commands

| DTO                       | Fields                                                               |
| ------------------------- | -------------------------------------------------------------------- |
| `CreateProjectCommand`    | `requesterMemberId`, `name`                                          |
| `UpdateProjectCommand`    | `projectId`, `requesterMemberId`, `name nullable`, `status nullable` |
| `AddProjectMemberCommand` | `projectId`, `targetMemberId`, `requesterMemberId`                   |

---

## Results

| DTO                    | Fields                                              |
| ---------------------- | --------------------------------------------------- |
| `ProjectSummaryResult` | `id`, `name`, `status`, `adminMemberId`             |
| `ProjectDetailResult`  | `project`, `members`, `tasks`, `tags`, `milestones` |
| `ProjectMemberResult`  | `id`, `projectId`, `memberId`                       |

---

## API

| Method  | Path                                           |           Status | Request                | Response                       | 설명         |
| ------- | ---------------------------------------------- | ---------------: | ---------------------- | ------------------------------ | ---------- |
| `POST`  | `/api/projects`                                |    `201 Created` | `CreateProjectRequest` | `ProjectSummaryResponse`       | 프로젝트 생성    |
| `GET`   | `/api/projects`                                |         `200 OK` | 없음                     | `List<ProjectSummaryResponse>` | 내 프로젝트 목록  |
| `GET`   | `/api/projects/{projectId}`                    |         `200 OK` | 없음                     | `ProjectDetailResponse`        | 프로젝트 상세    |
| `PATCH` | `/api/projects/{projectId}`                    |         `200 OK` | `UpdateProjectRequest` | `ProjectSummaryResponse`       | 프로젝트 수정    |
| `PUT`   | `/api/projects/{projectId}/members/{memberId}` | `204 No Content` | 없음                     | 없음                             | 프로젝트 멤버 추가 |
| `GET`   | `/api/projects/{projectId}/members`            |         `200 OK` | 없음                     | `List<ProjectMemberResponse>`  | 프로젝트 멤버 목록 |

---

## Request DTO

| DTO                    | Fields                             |
| ---------------------- | ---------------------------------- |
| `CreateProjectRequest` | `name`                             |
| `UpdateProjectRequest` | `name nullable`, `status nullable` |

---

## Response DTO

| DTO                      | Fields                                              |
| ------------------------ | --------------------------------------------------- |
| `ProjectSummaryResponse` | `id`, `name`, `status`, `adminMemberId`             |
| `ProjectDetailResponse`  | `project`, `members`, `tasks`, `tags`, `milestones` |
| `ProjectMemberResponse`  | `id`, `projectId`, `memberId`                       |

---

# Task

## UseCases

| UseCase                          | 입력                                      | 결과                        | 설명            |
| -------------------------------- | --------------------------------------- | ------------------------- | ------------- |
| `CreateTaskUseCase`              | `CreateTaskCommand`                     | `TaskResult`              | Task 생성       |
| `UpdateTaskUseCase`              | `UpdateTaskCommand`                     | `TaskResult`              | Task 수정       |
| `DeleteTaskUseCase`              | `Long taskId`, `Long requesterMemberId` | `void`                    | Task 삭제       |
| `GetTaskDetailUseCase`           | `Long taskId`, `Long requesterMemberId` | `TaskDetailResult`        | Task 상세       |
| `GetProjectTasksUseCase`         | `GetProjectTasksQuery`                  | `List<TaskSummaryResult>` | 프로젝트별 Task 목록 |
| `AssignMilestoneToTaskUseCase`   | `AssignMilestoneToTaskCommand`          | `void`                    | Milestone 지정  |
| `RemoveMilestoneFromTaskUseCase` | `Long taskId`, `Long requesterMemberId` | `void`                    | Milestone 제거  |

---

## Commands

| DTO                            | Fields                                                                                |
| ------------------------------ | ------------------------------------------------------------------------------------- |
| `CreateTaskCommand`            | `projectId`, `requesterMemberId`, `title`, `content nullable`, `milestoneId nullable` |
| `UpdateTaskCommand`            | `taskId`, `requesterMemberId`, `title nullable`, `content nullable`                   |
| `GetProjectTasksQuery`         | `projectId`, `requesterMemberId`, `milestoneId nullable`, `tagId nullable`            |
| `AssignMilestoneToTaskCommand` | `taskId`, `milestoneId`, `requesterMemberId`                                          |

---

## Results

| DTO                 | Fields                                                                                              |
| ------------------- | --------------------------------------------------------------------------------------------------- |
| `TaskResult`        | `id`, `projectId`, `title`, `content`, `writerMemberId`, `milestoneId nullable`                     |
| `TaskSummaryResult` | `id`, `projectId`, `title`, `writerMemberId`, `milestoneId nullable`                                |
| `TaskDetailResult`  | `id`, `projectId`, `title`, `content`, `writerMemberId`, `milestoneId nullable`, `tags`, `comments` |

---

## API

| Method   | Path                                          |           Status | Request             | Response                    | 설명           |
| -------- | --------------------------------------------- | ---------------: | ------------------- | --------------------------- | ------------ |
| `POST`   | `/api/projects/{projectId}/tasks`             |    `201 Created` | `CreateTaskRequest` | `TaskResponse`              | Task 생성      |
| `GET`    | `/api/projects/{projectId}/tasks`             |         `200 OK` | QueryString         | `List<TaskSummaryResponse>` | Task 목록      |
| `GET`    | `/api/tasks/{taskId}`                         |         `200 OK` | 없음                  | `TaskDetailResponse`        | Task 상세      |
| `PATCH`  | `/api/tasks/{taskId}`                         |         `200 OK` | `UpdateTaskRequest` | `TaskResponse`              | Task 수정      |
| `DELETE` | `/api/tasks/{taskId}`                         | `204 No Content` | 없음                  | 없음                          | Task 삭제      |
| `PUT`    | `/api/tasks/{taskId}/milestone/{milestoneId}` | `204 No Content` | 없음                  | 없음                          | Milestone 지정 |
| `DELETE` | `/api/tasks/{taskId}/milestone`               | `204 No Content` | 없음                  | 없음                          | Milestone 제거 |

---

## Request DTO

| DTO                 | Fields                                              |
| ------------------- | --------------------------------------------------- |
| `CreateTaskRequest` | `title`, `content nullable`, `milestoneId nullable` |
| `UpdateTaskRequest` | `title nullable`, `content nullable`                |

---

## Response DTO

| DTO                   | Fields                                                                                              |
| --------------------- | --------------------------------------------------------------------------------------------------- |
| `TaskResponse`        | `id`, `projectId`, `title`, `content`, `writerMemberId`, `milestoneId nullable`                     |
| `TaskSummaryResponse` | `id`, `projectId`, `title`, `writerMemberId`, `milestoneId nullable`                                |
| `TaskDetailResponse`  | `id`, `projectId`, `title`, `content`, `writerMemberId`, `milestoneId nullable`, `tags`, `comments` |

---

# Comment

## UseCases

| UseCase                  | 입력                                         | 결과                    | 설명    |
| ------------------------ | ------------------------------------------ | --------------------- | ----- |
| `CreateCommentUseCase`   | `CreateCommentCommand`                     | `CommentResult`       | 댓글 생성 |
| `UpdateCommentUseCase`   | `UpdateCommentCommand`                     | `CommentResult`       | 댓글 수정 |
| `DeleteCommentUseCase`   | `Long commentId`, `Long requesterMemberId` | `void`                | 댓글 삭제 |
| `GetTaskCommentsUseCase` | `Long taskId`, `Long requesterMemberId`    | `List<CommentResult>` | 댓글 목록 |

---

## Commands

| DTO                    | Fields                                      |
| ---------------------- | ------------------------------------------- |
| `CreateCommentCommand` | `taskId`, `requesterMemberId`, `content`    |
| `UpdateCommentCommand` | `commentId`, `requesterMemberId`, `content` |

---

## Results

| DTO             | Fields                                      |
| --------------- | ------------------------------------------- |
| `CommentResult` | `id`, `taskId`, `writerMemberId`, `content` |

---

## API

| Method   | Path                           |           Status | Request                | Response                | 설명    |
| -------- | ------------------------------ | ---------------: | ---------------------- | ----------------------- | ----- |
| `POST`   | `/api/tasks/{taskId}/comments` |    `201 Created` | `CreateCommentRequest` | `CommentResponse`       | 댓글 생성 |
| `GET`    | `/api/tasks/{taskId}/comments` |         `200 OK` | 없음                     | `List<CommentResponse>` | 댓글 목록 |
| `PATCH`  | `/api/comments/{commentId}`    |         `200 OK` | `UpdateCommentRequest` | `CommentResponse`       | 댓글 수정 |
| `DELETE` | `/api/comments/{commentId}`    | `204 No Content` | 없음                     | 없음                      | 댓글 삭제 |

---

# Tag

## UseCases

| UseCase                    | 입력                                         | 결과                | 설명     |
| -------------------------- | ------------------------------------------ | ----------------- | ------ |
| `CreateTagUseCase`         | `CreateTagCommand`                         | `TagResult`       | Tag 생성 |
| `UpdateTagUseCase`         | `UpdateTagCommand`                         | `TagResult`       | Tag 수정 |
| `DeleteTagUseCase`         | `Long tagId`, `Long requesterMemberId`     | `void`            | Tag 삭제 |
| `GetProjectTagsUseCase`    | `Long projectId`, `Long requesterMemberId` | `List<TagResult>` | Tag 목록 |
| `AttachTagToTaskUseCase`   | `AttachTagToTaskCommand`                   | `void`            | Tag 연결 |
| `DetachTagFromTaskUseCase` | `DetachTagFromTaskCommand`                 | `void`            | Tag 제거 |

---

## Commands

| DTO                        | Fields                                   |
| -------------------------- | ---------------------------------------- |
| `CreateTagCommand`         | `projectId`, `requesterMemberId`, `name` |
| `UpdateTagCommand`         | `tagId`, `requesterMemberId`, `name`     |
| `AttachTagToTaskCommand`   | `taskId`, `tagId`, `requesterMemberId`   |
| `DetachTagFromTaskCommand` | `taskId`, `tagId`, `requesterMemberId`   |

---

## Results

| DTO         | Fields                    |
| ----------- | ------------------------- |
| `TagResult` | `id`, `projectId`, `name` |

---

## API

| Method   | Path                               |           Status | Request            | Response            | 설명     |
| -------- | ---------------------------------- | ---------------: | ------------------ | ------------------- | ------ |
| `POST`   | `/api/projects/{projectId}/tags`   |    `201 Created` | `CreateTagRequest` | `TagResponse`       | Tag 생성 |
| `GET`    | `/api/projects/{projectId}/tags`   |         `200 OK` | 없음                 | `List<TagResponse>` | Tag 목록 |
| `PATCH`  | `/api/tags/{tagId}`                |         `200 OK` | `UpdateTagRequest` | `TagResponse`       | Tag 수정 |
| `DELETE` | `/api/tags/{tagId}`                | `204 No Content` | 없음                 | 없음                  | Tag 삭제 |
| `PUT`    | `/api/tasks/{taskId}/tags/{tagId}` | `204 No Content` | 없음                 | 없음                  | Tag 연결 |
| `DELETE` | `/api/tasks/{taskId}/tags/{tagId}` | `204 No Content` | 없음                 | 없음                  | Tag 제거 |

---

# Milestone

## UseCases

| UseCase                       | 입력                                           | 결과                      | 설명           |
| ----------------------------- | -------------------------------------------- | ----------------------- | ------------ |
| `CreateMilestoneUseCase`      | `CreateMilestoneCommand`                     | `MilestoneResult`       | Milestone 생성 |
| `UpdateMilestoneUseCase`      | `UpdateMilestoneCommand`                     | `MilestoneResult`       | Milestone 수정 |
| `DeleteMilestoneUseCase`      | `Long milestoneId`, `Long requesterMemberId` | `void`                  | Milestone 삭제 |
| `GetProjectMilestonesUseCase` | `Long projectId`, `Long requesterMemberId`   | `List<MilestoneResult>` | Milestone 목록 |

---

## Commands

| DTO                      | Fields                                                                  |
| ------------------------ | ----------------------------------------------------------------------- |
| `CreateMilestoneCommand` | `projectId`, `requesterMemberId`, `name`, `dueDate nullable`            |
| `UpdateMilestoneCommand` | `milestoneId`, `requesterMemberId`, `name nullable`, `dueDate nullable` |

---

## Results

| DTO               | Fields                                        |
| ----------------- | --------------------------------------------- |
| `MilestoneResult` | `id`, `projectId`, `name`, `dueDate nullable` |

---

## API

| Method   | Path                                   |           Status | Request                  | Response                  | 설명           |
| -------- | -------------------------------------- | ---------------: | ------------------------ | ------------------------- | ------------ |
| `POST`   | `/api/projects/{projectId}/milestones` |    `201 Created` | `CreateMilestoneRequest` | `MilestoneResponse`       | Milestone 생성 |
| `GET`    | `/api/projects/{projectId}/milestones` |         `200 OK` | 없음                       | `List<MilestoneResponse>` | Milestone 목록 |
| `PATCH`  | `/api/milestones/{milestoneId}`        |         `200 OK` | `UpdateMilestoneRequest` | `MilestoneResponse`       | Milestone 수정 |
| `DELETE` | `/api/milestones/{milestoneId}`        | `204 No Content` | 없음                       | 없음                        | Milestone 삭제 |

---

## Request DTO

| DTO                      | Fields                              |
| ------------------------ | ----------------------------------- |
| `CreateMilestoneRequest` | `name`, `dueDate nullable`          |
| `UpdateMilestoneRequest` | `name nullable`, `dueDate nullable` |

---

## Response DTO

| DTO                 | Fields                                        |
| ------------------- | --------------------------------------------- |
| `MilestoneResponse` | `id`, `projectId`, `name`, `dueDate nullable` |
