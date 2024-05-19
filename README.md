# 1. 프로젝트 기술 스택

## 1.1 개발 언어
- **Kotlin**
## 1.2 사용한 프레임워크
- **Spring Boot**
  - **Spring Web**: RESTful API를 개발하기 위해 사용되었습니다.
  - **Spring Data JPA**: 데이터베이스 접근을 위해 사용되었습니다.
  - **Spring Test**: 단위 테스트와 통합 테스트를 위해 사용되었습니다.
## 1.3 사용한 RDBMS
- **MySQL**
## 1.4 주요 라이브러리 및 도구
- **Mockito** : 단위 테스트에서 목 객체(mock object)를 생성하기 위해 사용되었습니다.
- **JUnit 5** : 테스트 프레임워크로 사용되었습니다.
- **SLF4J 및 Logback** : 로깅을 위해 사용되었습니다.
  
## 2. 프로젝트 구조 및 클래스 역할

| 경로                                       | 파일명                              | 설명                                               |
|--------------------------------------------|-------------------------------------|----------------------------------------------------|
| com/kstd/exam/controller                   | LectureController.kt                | 강연 관련 API 요청을 처리하는 컨트롤러 클래스        |
|                                            | LectureReservationController.kt     | 강연 예약 관련 API 요청을 처리하는 컨트롤러 클래스   |
| com/kstd/exam/controller/dto               | MultiResultDto.kt                   | 다중 결과를 반환하기 위한 DTO 클래스                |
|                                            | PopularLectureResponse.kt           | 인기 강연 정보를 담는 DTO 클래스                   |
|                                            | ReserveLectureRequest.kt            | 강연 예약 요청 데이터를 담는 DTO 클래스            |
|                                            | SingleResultDto.kt                  | 단일 결과를 반환하기 위한 DTO 클래스               |
| com/kstd/exam/domain                       | Lecture.kt                          | 강연 엔티티 클래스                                  |
|                                            | LectureReservation.kt               | 강연 예약 엔티티 클래스                             |
| com/kstd/exam/repository                   | LectureRepository.kt                | 강연 엔티티에 대한 CRUD 및 커스텀 쿼리를 처리하는 레포지토리 인터페이스 |
|                                            | LectureReservationRepository.kt     | 강연 예약 엔티티에 대한 CRUD 및 커스텀 쿼리를 처리하는 레포지토리 인터페이스 |
| com/kstd/exam/service                      | LectureReservationService.kt        | 강연 예약 서비스 인터페이스                        |
|                                            | LectureReservationServiceImpl.kt    | 강연 예약 서비스 구현 클래스                       |
|                                            | LectureService.kt                   | 강연 서비스 인터페이스                             |
|                                            | LectureServiceImpl.kt               | 강연 서비스 구현 클래스                            |
| com/kstd/exam/util                         | ResponseUtils.kt                    | API 응답을 표준화하는 유틸리티 클래스                 |
| com/kstd/exam/controller                   | LectureControllerTest.kt            | 강연 관련 API 요청을 테스트하는 클래스              |
|                                            | LectureReservationControllerTest.kt | 강연 예약 관련 API 요청을 테스트하는 클래스         |
| com/kstd/exam/repository                   | LectureRepositoryTest.kt            | 강연 엔티티에 대한 CRUD 작업을 테스트하는 클래스    |
|                                            | LectureReservationRepositoryTest.kt | 강연 예약 엔티티에 대한 CRUD 작업을 테스트하는 클래스 |
| com/kstd/exam/service                      | LectureServiceImplTest.kt           | 강연 서비스 구현 클래스의 비즈니스 로직을 테스트하는 클래스 |
|                                            | LectureReservationServiceImplTest.kt| 강연 예약 서비스 구현 클래스의 비즈니스 로직을 테스트하는 클래스 |
|                                            | LectureReservationConcurrencyTest.kt| 강연 예약 서비스의 동시성 문제를 테스트하는 클래스  |
### 2.1 /main/controller 

- `LectureController.kt`: 강연 생성, 조회 등의 강연 관련 API 요청을 처리하는 컨트롤러 클래스.
- `LectureReservationController.kt`: 강연 예약, 예약 취소, 예약 목록 조회 등의 강연 예약 관련 API 요청을 처리하는 컨트롤러 클래스.
- `dto 패키지`: 데이터 전송 객체를 정의하는 패키지.
    - `MultiResultDto.kt`: 다중 결과를 포함한 응답을 위한 DTO 클래스.
    - `PopularLectureResponse.kt`: 인기 강연 정보를 포함한 응답을 위한 DTO 클래스.
    - `ReserveLectureRequest.kt`: 강연 예약 요청 데이터를 포함한 DTO 클래스.
    - `SingleResultDto.kt`: 단일 결과를 포함한 응답을 위한 DTO 클래스.

### 2.2 /main/domain 

- `Lecture.kt`: 강연 정보를 나타내는 엔티티 클래스.
- `LectureReservation.kt`: 강연 예약 정보를 나타내는 엔티티 클래스.

### 2.3 /main/repository 

- `LectureRepository.kt`: 강연 엔티티에 대한 CRUD 작업 및 커스텀 쿼리를 처리하는 JPA 레포지토리 인터페이스.
- `LectureReservationRepository.kt`: 강연 예약 엔티티에 대한 CRUD 작업 및 커스텀 쿼리를 처리하는 JPA 레포지토리 인터페이스.

### 2.4 /main/service 

- `LectureReservationService.kt`: 강연 예약 관련 비즈니스 로직을 정의하는 서비스 인터페이스.
- `LectureReservationServiceImpl.kt`: 강연 예약 관련 비즈니스 로직을 구현하는 서비스 클래스.
- `LectureService.kt`: 강연 관련 비즈니스 로직을 정의하는 서비스 인터페이스.
- `LectureServiceImpl.kt`: 강연 관련 비즈니스 로직을 구현하는 서비스 클래스.

### 2.5 /main/util 

- `ResponseUtils.kt`: API 응답을 표준화하는 유틸리티 클래스. 응답 메시지와 상태 코드를 포함하는 응답 객체를 생성하는 기능을 제공.

### 2.6 /test/controller 

- `LectureControllerTest.kt`: 강연 생성, 조회 등의 강연 관련 API 요청을 테스트하는 클래스.
- `LectureReservationControllerTest.kt`: 강연 예약, 예약 취소, 예약 목록 조회 등의 강연 예약 관련 API 요청을 테스트하는 클래스.

### 2.7 /test/repository 

- `LectureRepositoryTest.kt`: 강연 엔티티에 대한 CRUD 작업을 테스트하는 클래스. 강연 생성, 조회, 업데이트, 삭제 기능을 포함.
- `LectureReservationRepositoryTest.kt`: 강연 예약 엔티티에 대한 CRUD 작업을 테스트하는 클래스. 예약 생성, 조회, 업데이트, 삭제 기능을 포함.

### 2.8 /test/service 

- `LectureServiceImplTest.kt`: 강연 서비스 구현 클래스의 비즈니스 로직을 테스트하는 클래스. 강연 생성 및 조회 기능을 포함.
- `LectureReservationServiceImplTest.kt`: 강연 예약 서비스 구현 클래스의 비즈니스 로직을 테스트하는 클래스. 강연 예약, 예약 취소, 인기 강연 조회 기능을 포함.
- `LectureReservationConcurrencyTest.kt`: 강연 예약 서비스의 동시성 문제를 테스트하는 클래스. 여러 사용자가 동시에 강연을 예약하는 시나리오를 테스트.


## 3 API 샘플 요청

### 3.1 전체 강연 목록 조회 (GET /api/lecture)
<pre>
<code>
curl -X GET http://localhost:8080/api/lecture \
-H "Content-Type: application/json"
</code>
</pre>
Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "강연 목록 조회 성공",
    "resultCount": 2,
    "resultDatas": [
        {
            "id": 1,
            "lecturer": "John Doe",
            "location": "Room 101",
            "maxSeats": 100,
            "lectureTime": "2024-05-18T10:00:00Z",
            "description": "Lecture on Advanced Mathematics",
            "createdAt": "2024-05-01T12:00:00Z",
            "updatedAt": "2024-05-01T12:00:00Z"
        },
        {
            "id": 2,
            "lecturer": "Jane Smith",
            "location": "Room 102",
            "maxSeats": 50,
            "lectureTime": "2024-05-19T14:00:00Z",
            "description": "Lecture on Quantum Physics",
            "createdAt": "2024-05-02T12:00:00Z",
            "updatedAt": "2024-05-02T12:00:00Z"
        }
    ]
}
</code>
</pre>
Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>
---
### 3.2 강연 등록 (POST /api/lecture)

<pre>
<code>
curl -X POST http://localhost:8080/api/lecture 
-H "Content-Type: application/json" 
-d '{
    "lecturer": "홍길동",
    "location": "서울 삼성동 테스트호",
    "maxSeats": 200,
    "lectureTime": "2024-05-19T12:00:00Z",
    "description": "테스트 강의"
}'
</code>
</pre>

##### Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "강연이 성공적으로 생성되었습니다",
    "resultData": {
        "id": 1,
        "lecturer": "홍길동",
        "location": "서울 삼성동 테스트호",
        "maxSeats": 200,
        "lectureTime": "2024-05-19T12:00:00Z",
        "description": "테스트 강의",
        "createdAt": "2024-05-01T12:00:00Z",
        "updatedAt": "2024-05-01T12:00:00Z"
    }
}
</code>
</pre>

##### Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>

---
#### 3.3 강연 신청자 목록 (강연 별 신청한 사번 목록) (GET /api/lecture/{lectureId}/reservations)

<pre>
<code>
curl -X GET http://localhost:8080/api/lecture/1/reservations
-H "Content-Type: application/json"
</code>
</pre>

##### Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "강연 예약 목록 조회 성공",
    "resultCount": 1,
    "resultDatas": [
        {
            "id": 1,
            "lectureId": 1,
            "employeeId": "12345",
            "createdAt": "2024-05-01T12:00:00Z",
            "updatedAt": "2024-05-01T12:00:00Z",
            "cancelYn": "N"
        }
    ]
}
</code>
</pre>

##### Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>

---
#### 3.4 강연 목록 (시작 1주일 전부터, 강연 시작 시간 1일 후까지 노출) (GET /api/lecture/available)
<pre>
<code>
curl -X GET http://localhost:8080/api/lecture/available 
-H "Content-Type: application/json"
</code>
</pre>
Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "신청 가능한 강연 목록 조회 성공",
    "resultCount": 1,
    "resultDatas": [
        {
            "id": 1,
            "lecturer": "John Doe",
            "location": "Room 101",
            "maxSeats": 100,
            "lectureTime": "2024-05-18T10:00:00Z",
            "description": "Lecture on Advanced Mathematics",
            "createdAt": "2024-05-01T12:00:00Z",
            "updatedAt": "2024-05-01T12:00:00Z"
        }
    ]
}
</code>
</pre>
Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>

#### 3.5 강연 신청 (사번입력, 같은 강연 중복 신청 제한) (POST /api/lecture/{lectureId}/reserve)

<pre>
<code>
curl -X POST http://localhost:8080/api/lecture/1/reserve
-H "Content-Type: application/json"
-d '{
    "employeeId": "12345"
}'
</code>
</pre>

##### Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "강연이 성공적으로 예약되었습니다",
    "resultData": {
        "id": 1,
        "lectureId": 1,
        "employeeId": "12345",
        "createdAt": "2024-05-01T12:00:00Z",
        "updatedAt": "2024-05-01T12:00:00Z",
        "cancelYn": "N"
    }
}
</code>
</pre>

##### Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>

---
#### 3.6 신청 내역 조회 (사번입력) (GET /api/lecture/reservations/{employeeId})

<pre>
<code>
curl -X GET http://localhost:8080/api/lecture/reservations/12345
-H "Content-Type: application/json"
</code>
</pre>

##### Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "신청 내역 조회 성공",
    "resultCount": 1,
    "resultDatas": [
        {
            "id": 1,
            "lectureId": 1,
            "employeeId": "12345",
            "createdAt": "2024-05-01T12:00:00Z",
            "updatedAt": "2024-05-01T12:00:00Z",
            "cancelYn": "N"
        }
    ]
}
</code>
</pre>

##### Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>
---

#### 3.7 강연 예약 취소 (POST /api/lecture/{lectureId}/cancel)

<pre>
<code>
curl -X PUT http://localhost:8080/api/lecture/1/cancel
-H "Content-Type: application/json"
-d '{
    "employeeId": "12345"
}'
</code>
</pre>

##### Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "강연 예약이 성공적으로 취소되었습니다",
    "resultData": {
        "id": 1,
        "lectureId": 1,
        "employeeId": "12345",
        "createdAt": "2024-05-01T12:00:00Z",
        "updatedAt": "2024-05-01T12:00:00Z",
        "cancelYn": "Y"
    }
}
</code>
</pre>

##### Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>
---
#### 3.6 인기 강연 조회 (GET /api/lecture/popular)

<pre>
<code>
curl -X GET http://localhost:8080/api/lecture/popular
-H "Content-Type: application/json"
</code>
</pre>

##### Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "실시간 인기 강연 조회 성공",
    "resultCount": 1,
    "resultDatas": [
        {
            "lectureId": 1,
            "reservationCount": 10
        }
    ]
}
</code>
</pre>

##### Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>
---
## 4. DB 설정

### 4.1 강연 테이블 생성
<pre>
<code>
CREATE TABLE lectures (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lecturer VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    max_seats INT NOT NULL,
    lecture_time DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    description TEXT
);
</code>
</pre>

### 4.2 강연 샘플 데이터 추가
<pre>
<code>
INSERT INTO lectures (lecturer, location, max_seats, lecture_time, created_at, updated_at, description)
VALUES 
    ('김철수', '101호 강의실', 100, '2024-06-01 10:00:00', NOW(), NOW(), '고급 수학 강의'),
    ('이영희', '102호 강의실', 50, '2024-06-02 14:00:00', NOW(), NOW(), '양자 물리학 강의'),
    ('박민수', '103호 강의실', 75, '2024-06-03 09:00:00', NOW(), NOW(), '기계 학습 강의'),
    ('최영수', '104호 강의실', 80, '2024-06-04 11:00:00', NOW(), NOW(), '데이터 과학 강의'),
    ('정혜진', '105호 강의실', 60, '2024-06-05 15:00:00', NOW(), NOW(), '블록체인 기술 강의');
</code>
</pre>

### 4.3 강연 예약 테이블 생성
<pre>
<code>
CREATE TABLE lectures_reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lecture_id BIGINT NOT NULL,
    employee_id VARCHAR(5) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    cancel_yn CHAR(1) NOT NULL DEFAULT 'N',
    FOREIGN KEY (lecture_id) REFERENCES lectures(id)
);
</code>
</pre>

### 4.4 강연 예약 샘플 데이터 추가
<pre>
<code>
-- 김철수 강연 (1명 예약)
INSERT INTO lectures_reservation (lecture_id, employee_id, created_at, updated_at)
VALUES
    (1, '10001', NOW(), NOW());

-- 이영희 강연 (2명 예약)
INSERT INTO lectures_reservation (lecture_id, employee_id, created_at, updated_at)
VALUES
    (2, '10002', NOW(), NOW()),
    (2, '10003', NOW(), NOW());

-- 박민수 강연 (3명 예약)
INSERT INTO lectures_reservation (lecture_id, employee_id, created_at, updated_at)
VALUES
    (3, '10004', NOW(), NOW()),
    (3, '10005', NOW(), NOW()),
    (3, '10006', NOW(), NOW());

-- 최영수 강연 (5명 예약)
INSERT INTO lectures_reservation (lecture_id, employee_id, created_at, updated_at)
VALUES
    (4, '10007', NOW(), NOW()),
    (4, '10008', NOW(), NOW()),
    (4, '10009', NOW(), NOW()),
    (4, '10010', NOW(), NOW()),
    (4, '10011', NOW(), NOW());

-- 정혜진 강연 (20명 예약)
INSERT INTO lectures_reservation (lecture_id, employee_id, created_at, updated_at)
VALUES
    (5, '10012', NOW(), NOW()),
    (5, '10013', NOW(), NOW()),
    (5, '10014', NOW(), NOW()),
    (5, '10015', NOW(), NOW()),
    (5, '10016', NOW(), NOW()),
    (5, '10017', NOW(), NOW()),
    (5, '10018', NOW(), NOW()),
    (5, '10019', NOW(), NOW()),
    (5, '10020', NOW(), NOW()),
    (5, '10021', NOW(), NOW()),
    (5, '10022', NOW(), NOW()),
    (5, '10023', NOW(), NOW()),
    (5, '10024', NOW(), NOW()),
    (5, '10025', NOW(), NOW()),
    (5, '10026', NOW(), NOW()),
    (5, '10027', NOW(), NOW()),
    (5, '10028', NOW(), NOW()),
    (5, '10029', NOW(), NOW()),
    (5, '10030', NOW(), NOW()),
    (5, '10031', NOW(), NOW());
</code>
</pre>

