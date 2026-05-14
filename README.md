# task-api
# Gateway Browser Routes & DTO

## Auth / Member Routes

| Method | Path | Status | 설명 |
|---|---|---:|---|
| `GET` | `/login` | `200 OK` | 로그인 화면 |
| `POST` | `/login` | `302 Found` | 로그인 처리 |
| `POST` | `/logout` | `302 Found` | 로그아웃 |
| `GET` | `/members/new` | `200 OK` | 회원가입 화면 |
| `POST` | `/members` | `302 Found` | 회원가입 |
| `GET` | `/members?loginId={loginId}` | `200 OK` | 회원 검색 |
| `GET` | `/members?email={email}` | `200 OK` | 회원 검색 |

## Project Routes

| Method | Path | Status | 설명 |
|---|---|---:|---|
| `GET` | `/projects` | `200 OK` | 내 프로젝트 목록 |
| `GET` | `/projects/new` | `200 OK` | 프로젝트 생성 화면 |
| `POST` | `/projects` | `302 Found` | 프로젝트 생성 |
| `GET` | `/projects/{projectId}` | `200 OK` | 프로젝트 상세 |
| `GET` | `/projects/{projectId}/edit` | `200 OK` | 프로젝트 수정 화면 |
| `PATCH` | `/projects/{projectId}` | `302 Found` | 프로젝트 이름/상태 수정 |
| `GET` | `/projects/{projectId}/members` | `200 OK` | 프로젝트 멤버 목록 |
| `PUT` | `/projects/{projectId}/members/{memberId}` | `302 Found` | 프로젝트 멤버 추가 |

## Task Routes

| Method | Path | Status | 설명 |
|---|---|---:|---|
| `GET` | `/projects/{projectId}/tasks/new` | `200 OK` | Task 생성 화면 |
| `POST` | `/projects/{projectId}/tasks` | `302 Found` | Task 생성 |
| `GET` | `/tasks/{taskId}` | `200 OK` | Task 상세 |
| `GET` | `/tasks/{taskId}/edit` | `200 OK` | Task 수정 화면 |
| `PATCH` | `/tasks/{taskId}` | `302 Found` | Task 수정 |
| `DELETE` | `/tasks/{taskId}` | `302 Found` | Task 삭제 |
| `PUT` | `/tasks/{taskId}/milestone/{milestoneId}` | `302 Found` | Milestone 지정 |
| `DELETE` | `/tasks/{taskId}/milestone` | `302 Found` | Milestone 제거 |
| `PUT` | `/tasks/{taskId}/tags/{tagId}` | `302 Found` | Tag 연결 |
| `DELETE` | `/tasks/{taskId}/tags/{tagId}` | `302 Found` | Tag 제거 |

## Comment Routes

| Method | Path | Status | 설명 |
|---|---|---:|---|
| `POST` | `/tasks/{taskId}/comments` | `302 Found` | 댓글 생성 |
| `PATCH` | `/comments/{commentId}` | `302 Found` | 댓글 수정 |
| `DELETE` | `/comments/{commentId}` | `302 Found` | 댓글 삭제 |

## Tag Routes

| Method | Path | Status | 설명 |
|---|---|---:|---|
| `POST` | `/projects/{projectId}/tags` | `302 Found` | Tag 생성 |
| `PATCH` | `/tags/{tagId}` | `302 Found` | Tag 수정 |
| `DELETE` | `/tags/{tagId}` | `302 Found` | Tag 삭제 |

## Milestone Routes

| Method | Path | Status | 설명 |
|---|---|---:|---|
| `POST` | `/projects/{projectId}/milestones` | `302 Found` | Milestone 생성 |
| `PATCH` | `/milestones/{milestoneId}` | `302 Found` | Milestone 수정 |
| `DELETE` | `/milestones/{milestoneId}` | `302 Found` | Milestone 삭제 |

## Form DTO

| DTO | Fields |
|---|---|
| `LoginFormRequest` | `loginId`, `password` |
| `RegisterMemberFormRequest` | `loginId`, `email`, `password` |
| `CreateProjectFormRequest` | `name` |
| `UpdateProjectFormRequest` | `name nullable`, `status nullable` |
| `CreateTaskFormRequest` | `title`, `content nullable`, `milestoneId nullable` |
| `UpdateTaskFormRequest` | `title nullable`, `content nullable` |
| `CreateCommentFormRequest` | `content` |
| `UpdateCommentFormRequest` | `content` |
| `CreateTagFormRequest` | `name` |
| `UpdateTagFormRequest` | `name` |
| `CreateMilestoneFormRequest` | `name` |
| `UpdateMilestoneFormRequest` | `name` |

## Client DTO

| DTO | Fields |
|---|---|
| `AccountApiLoginRequest` | `loginId`, `password` |
| `AccountApiLoginResponse` | `memberId`, `loginId`, `email`, `status` |
| `AccountApiRegisterMemberRequest` | `loginId`, `email`, `password` |
| `AccountApiMemberResponse` | `id`, `loginId`, `email`, `status` |
| `TaskApiCreateProjectRequest` | `name` |
| `TaskApiUpdateProjectRequest` | `name nullable`, `status nullable` |
| `TaskApiProjectResponse` | `id`, `name`, `status`, `adminMemberId` |
| `TaskApiProjectMemberResponse` | `id`, `projectId`, `memberId` |
| `TaskApiCreateTaskRequest` | `title`, `content nullable`, `milestoneId nullable` |
| `TaskApiUpdateTaskRequest` | `title nullable`, `content nullable` |
| `TaskApiTaskResponse` | `id`, `projectId`, `title`, `content`, `writerMemberId`, `milestoneId nullable` |
| `TaskApiTaskDetailResponse` | `id`, `projectId`, `title`, `content`, `writerMemberId`, `milestoneId nullable`, `tags`, `comments` |
| `TaskApiCreateCommentRequest` | `content` |
| `TaskApiUpdateCommentRequest` | `content` |
| `TaskApiCommentResponse` | `id`, `taskId`, `writerMemberId`, `content` |
| `TaskApiCreateTagRequest` | `name` |
| `TaskApiUpdateTagRequest` | `name` |
| `TaskApiTagResponse` | `id`, `projectId`, `name` |
| `TaskApiCreateMilestoneRequest` | `name` |
| `TaskApiUpdateMilestoneRequest` | `name` |
| `TaskApiMilestoneResponse` | `id`, `projectId`, `name` |