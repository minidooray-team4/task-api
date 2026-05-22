# Error Status Codes

컨트롤러 테스트 작성 시 오류 응답 검증 기준으로 사용한다.

## Response Body

`BusinessException`과 validation 오류는 `ProblemDetail` 형식으로 내려간다.

### BusinessException

```json
{
  "type": "about:blank",
  "title": "PROJECT_NOT_FOUND",
  "status": 404,
  "detail": "프로젝트를 찾을 수 없습니다.",
  "instance": "/api/projects/1",
  "code": "PROJECT_NOT_FOUND"
}
```

### Validation Error

```json
{
  "type": "about:blank",
  "title": "INVALID_REQUEST",
  "status": 400,
  "detail": "요청 값이 올바르지 않습니다.",
  "instance": "/api/projects",
  "code": "INVALID_REQUEST",
  "fieldErrors": [
    {
      "field": "name",
      "message": "..."
    }
  ]
}
```

## Common Controller Errors

| Situation | Expected Status | Handler | Test Hint |
| --- | ---: | --- | --- |
| `@Valid @RequestBody` 검증 실패 | `400 Bad Request` | `GlobalExceptionHandler.handleValidationException` | `jsonPath("$.code").value("INVALID_REQUEST")` |
| 필수 request header 누락, 예: `X-MEMBER-ID` | `400 Bad Request` | Spring MVC default | body 형식은 커스텀 `ProblemDetail`과 다를 수 있음 |
| path variable 타입 불일치, 예: `/api/tasks/abc` | `400 Bad Request` | Spring MVC default | 상태 코드 중심으로 검증 권장 |
| request param 타입 불일치 | `400 Bad Request` | Spring MVC default | 상태 코드 중심으로 검증 권장 |
| JSON 문법 오류 | `400 Bad Request` | Spring MVC default | 상태 코드 중심으로 검증 권장 |
| 지원하지 않는 HTTP method | `405 Method Not Allowed` | Spring MVC default | 상태 코드 중심으로 검증 권장 |
| 존재하지 않는 URL | `404 Not Found` | Spring MVC default | 상태 코드 중심으로 검증 권장 |

## Business Error Codes

| ErrorCode | Status | Meaning | Common Test Case |
| --- | ---: | --- | --- |
| `TEMP_HOLDER` | `400 Bad Request` | 임시 에러 코드 | 임시 구현에서 명시적으로 throw 하는 경우 |
| `INVALID_REQUEST` | `400 Bad Request` | 잘못된 요청 | 서비스에서 요청 조합이 유효하지 않다고 판단하는 경우 |
| `INVALID_MILESTONE_PROJECT` | `400 Bad Request` | 마일스톤이 해당 프로젝트에 속하지 않음 | 다른 프로젝트의 milestone을 task에 지정 |
| `FORBIDDEN` | `403 Forbidden` | 권한 없음 | 프로젝트 멤버가 아니거나 관리자 권한이 필요한 작업 |
| `PROJECT_NOT_FOUND` | `404 Not Found` | 프로젝트 없음 | 존재하지 않는 `projectId` 조회/수정/하위 리소스 생성 |
| `TASK_NOT_FOUND` | `404 Not Found` | Task 없음 | 존재하지 않는 `taskId` 조회/수정/삭제 |
| `TAG_NOT_FOUND` | `404 Not Found` | Tag 없음 | 존재하지 않는 `tagId` 수정/삭제/연결 |
| `TASK_TAG_NOT_FOUND` | `404 Not Found` | Task-Tag 연결 없음 | 연결되지 않은 tag detach |
| `MILESTONE_NOT_FOUND` | `404 Not Found` | Milestone 없음 | 존재하지 않는 `milestoneId` 수정/삭제/지정 |
| `COMMENT_NOT_FOUND` | `404 Not Found` | Comment 없음 | 존재하지 않는 `commentId` 수정/삭제 |
| `PROJECT_MEMBER_ALREADY_EXISTS` | `409 Conflict` | 이미 프로젝트 멤버임 | 같은 멤버를 프로젝트에 다시 추가 |
| `TAG_ALREADY_EXISTS` | `409 Conflict` | 이미 같은 이름의 Tag가 있음 | 같은 프로젝트에 동일 tag name 생성 |
| `MILESTONE_ALREADY_EXISTS` | `409 Conflict` | 이미 같은 이름의 Milestone이 있음 | 같은 프로젝트에 동일 milestone name 생성 |

## Endpoint Error Checklist

### Project

| Endpoint | Error Case | Expected Status | ErrorCode |
| --- | --- | ---: | --- |
| `POST /api/projects` | request body validation 실패 | `400` | `INVALID_REQUEST` |
| `GET /api/projects/{projectId}` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `GET /api/projects/{projectId}` | 접근 권한 없음 | `403` | `FORBIDDEN` |
| `PATCH /api/projects/{projectId}` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `PATCH /api/projects/{projectId}` | 관리자 권한 없음 | `403` | `FORBIDDEN` |
| `PUT /api/projects/{projectId}/members/{memberId}` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `PUT /api/projects/{projectId}/members/{memberId}` | 관리자 권한 없음 | `403` | `FORBIDDEN` |
| `PUT /api/projects/{projectId}/members/{memberId}` | 이미 멤버임 | `409` | `PROJECT_MEMBER_ALREADY_EXISTS` |
| `GET /api/projects/{projectId}/members` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `GET /api/projects/{projectId}/members` | 접근 권한 없음 | `403` | `FORBIDDEN` |

### Task

| Endpoint | Error Case | Expected Status | ErrorCode |
| --- | --- | ---: | --- |
| `POST /api/projects/{projectId}/tasks` | request body validation 실패 | `400` | `INVALID_REQUEST` |
| `POST /api/projects/{projectId}/tasks` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `POST /api/projects/{projectId}/tasks` | milestone 없음 | `404` | `MILESTONE_NOT_FOUND` |
| `POST /api/projects/{projectId}/tasks` | milestone이 다른 프로젝트 소속 | `400` | `INVALID_MILESTONE_PROJECT` |
| `GET /api/projects/{projectId}/tasks` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `GET /api/projects/{projectId}/tasks` | 접근 권한 없음 | `403` | `FORBIDDEN` |
| `GET /api/tasks/{taskId}` | Task 없음 | `404` | `TASK_NOT_FOUND` |
| `GET /api/tasks/{taskId}` | 접근 권한 없음 | `403` | `FORBIDDEN` |
| `PATCH /api/tasks/{taskId}` | Task 없음 | `404` | `TASK_NOT_FOUND` |
| `DELETE /api/tasks/{taskId}` | Task 없음 | `404` | `TASK_NOT_FOUND` |
| `PUT /api/tasks/{taskId}/milestone/{milestoneId}` | Task 없음 | `404` | `TASK_NOT_FOUND` |
| `PUT /api/tasks/{taskId}/milestone/{milestoneId}` | Milestone 없음 | `404` | `MILESTONE_NOT_FOUND` |
| `PUT /api/tasks/{taskId}/milestone/{milestoneId}` | Milestone이 Task 프로젝트와 다름 | `400` | `INVALID_MILESTONE_PROJECT` |
| `DELETE /api/tasks/{taskId}/milestone` | Task 없음 | `404` | `TASK_NOT_FOUND` |

### Comment

| Endpoint | Error Case | Expected Status | ErrorCode |
| --- | --- | ---: | --- |
| `POST /api/tasks/{taskId}/comments` | request body validation 실패 | `400` | `INVALID_REQUEST` |
| `POST /api/tasks/{taskId}/comments` | Task 없음 | `404` | `TASK_NOT_FOUND` |
| `GET /api/tasks/{taskId}/comments` | Task 없음 | `404` | `TASK_NOT_FOUND` |
| `PATCH /api/comments/{commentId}` | request body validation 실패 | `400` | `INVALID_REQUEST` |
| `PATCH /api/comments/{commentId}` | Comment 없음 | `404` | `COMMENT_NOT_FOUND` |
| `DELETE /api/comments/{commentId}` | Comment 없음 | `404` | `COMMENT_NOT_FOUND` |

### Tag

| Endpoint | Error Case | Expected Status | ErrorCode |
| --- | --- | ---: | --- |
| `POST /api/projects/{projectId}/tags` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `POST /api/projects/{projectId}/tags` | 같은 프로젝트에 동일 tag name 존재 | `409` | `TAG_ALREADY_EXISTS` |
| `GET /api/projects/{projectId}/tags` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `PATCH /api/tags/{tagId}` | Tag 없음 | `404` | `TAG_NOT_FOUND` |
| `PATCH /api/tags/{tagId}` | 같은 프로젝트에 동일 tag name 존재 | `409` | `TAG_ALREADY_EXISTS` |
| `DELETE /api/tags/{tagId}` | Tag 없음 | `404` | `TAG_NOT_FOUND` |
| `PUT /api/tasks/{taskId}/tags/{tagId}` | Task 없음 | `404` | `TASK_NOT_FOUND` |
| `PUT /api/tasks/{taskId}/tags/{tagId}` | Tag 없음 | `404` | `TAG_NOT_FOUND` |
| `PUT /api/tasks/{taskId}/tags/{tagId}` | Task와 Tag의 프로젝트가 다름 | `400` | `INVALID_REQUEST` |
| `DELETE /api/tasks/{taskId}/tags/{tagId}` | Task-Tag 연결 없음 | `404` | `TASK_TAG_NOT_FOUND` |

### Milestone

| Endpoint | Error Case | Expected Status | ErrorCode |
| --- | --- | ---: | --- |
| `POST /api/projects/{projectId}/milestones` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `POST /api/projects/{projectId}/milestones` | 같은 프로젝트에 동일 milestone name 존재 | `409` | `MILESTONE_ALREADY_EXISTS` |
| `GET /api/projects/{projectId}/milestones` | 프로젝트 없음 | `404` | `PROJECT_NOT_FOUND` |
| `PATCH /api/milestones/{milestoneId}` | Milestone 없음 | `404` | `MILESTONE_NOT_FOUND` |
| `PATCH /api/milestones/{milestoneId}` | 같은 프로젝트에 동일 milestone name 존재 | `409` | `MILESTONE_ALREADY_EXISTS` |
| `DELETE /api/milestones/{milestoneId}` | Milestone 없음 | `404` | `MILESTONE_NOT_FOUND` |

## MockMvc Assertions

```java
mockMvc.perform(get("/api/projects/{projectId}", 999L)
                .header("X-MEMBER-ID", 1L))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("PROJECT_NOT_FOUND"))
        .andExpect(jsonPath("$.title").value("PROJECT_NOT_FOUND"))
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.instance").value("/api/projects/999"));
```

```java
mockMvc.perform(post("/api/projects")
                .header("X-MEMBER-ID", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("INVALID_REQUEST"))
        .andExpect(jsonPath("$.fieldErrors").isArray());
```
