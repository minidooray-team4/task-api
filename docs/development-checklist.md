# Development Checklist

## Common

- [ ] 요청자의 프로젝트 접근 권한을 검증한다.
- [ ] 존재하지 않는 리소스는 `404 Not Found` 계열 비즈니스 예외로 처리한다.
- [ ] 권한이 없는 요청은 `403 Forbidden` 계열 비즈니스 예외로 처리한다.
- [ ] 생성/수정 요청 DTO에 Bean Validation을 적용한다.
- [ ] 서비스 메서드에 트랜잭션 경계를 설정한다.
- [ ] 조회 전용 서비스에는 read-only 트랜잭션을 적용한다.
- [ ] Result DTO와 Response DTO 변환 메서드를 일관되게 유지한다.
- [ ] API 성공 상태 코드가 문서와 컨트롤러에서 일치하는지 확인한다.

## Project

- [X] 프로젝트 생성 API를 구현한다.
- [X] 내 프로젝트 목록 조회 API를 구현한다.
- [X] 프로젝트 상세 조회 API를 구현한다.
- [X] 프로젝트 이름/상태 수정 API를 구현한다.
- [X] 프로젝트 멤버 추가 API를 구현한다.
- [X] 프로젝트 멤버 목록 조회 API를 구현한다.
- [X] 프로젝트 관리자만 멤버 추가/프로젝트 수정이 가능하도록 검증한다.
- [X] 중복 프로젝트 멤버 추가를 방지한다.

## Task

- [X] Task 생성 API를 구현한다.
- [X] Task 수정 API를 구현한다.
- [ ] Task 삭제 API를 구현한다.
- [ ] Task 상세 조회 API를 구현한다.
- [ ] 프로젝트별 Task 목록 조회 API를 구현한다.
- [ ] milestoneId 필터를 적용한다.
- [ ] tagId 필터를 적용한다.
- [ ] Task에 Milestone 지정 API를 구현한다.
- [ ] Task에서 Milestone 제거 API를 구현한다.
- [ ] Task와 Milestone이 같은 프로젝트에 속하는지 서비스와 DB 제약으로 검증한다.

## Comment

- [ ] 댓글 생성 API를 구현한다.
- [ ] 댓글 목록 조회 API를 구현한다.
- [ ] 댓글 수정 API를 구현한다.
- [ ] 댓글 삭제 API를 구현한다.
- [ ] 댓글 작성자 또는 프로젝트 권한 기준 수정/삭제 정책을 확정한다.

## Tag

- [ ] Tag 생성 API를 구현한다.
- [ ] Tag 목록 조회 API를 구현한다.
- [ ] Tag 수정 API를 구현한다.
- [ ] Tag 삭제 API를 구현한다.
- [ ] Task에 Tag 연결 API를 구현한다.
- [ ] Task에서 Tag 제거 API를 구현한다.
- [ ] 같은 프로젝트 내 Tag 이름 중복을 방지한다.
- [ ] Task와 Tag가 같은 프로젝트에 속하는지 서비스와 DB 제약으로 검증한다.

## Milestone

- [ ] Milestone 생성 API를 구현한다.
- [ ] Milestone 목록 조회 API를 구현한다.
- [ ] Milestone 수정 API를 구현한다.
- [ ] Milestone 삭제 API를 구현한다.
- [ ] 같은 프로젝트 내 Milestone 이름 중복을 방지한다.
- [ ] Milestone 삭제 시 연결된 Task 처리 정책을 확정한다.

