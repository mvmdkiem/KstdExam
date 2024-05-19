package com.kstd.exam.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant

class LectureTest {

    /**
     * Lecture 엔터티 생성 테스트
     */
    @Test
    fun `Lecture 엔터티 생성 테스트`() {
        val now = Instant.now()
        val lecture = Lecture(
            id = 1,
            lecturer = "홍길동",
            location = "강의실 101",
            maxSeats = 100,
            lectureTime = now,
            createdAt = now,
            updatedAt = now,
            description = "예시 강연"
        )

        // 단계별 출력
        println("강연 엔터티 생성 테스트 시작")
        println("강연 ID: ${lecture.id}")
        println("강연자: ${lecture.lecturer}")
        println("강의실 위치: ${lecture.location}")
        println("최대 좌석 수: ${lecture.maxSeats}")
        println("강연 시간: ${lecture.lectureTime}")
        println("생성 시간: ${lecture.createdAt}")
        println("수정 시간: ${lecture.updatedAt}")
        println("강연 설명: ${lecture.description}")

        assertEquals(1, lecture.id)
        assertEquals("홍길동", lecture.lecturer)
        assertEquals("강의실 101", lecture.location)
        assertEquals(100, lecture.maxSeats)
        assertEquals(now, lecture.lectureTime)
        assertEquals(now, lecture.createdAt)
        assertEquals(now, lecture.updatedAt)
        assertEquals("예시 강연", lecture.description)

        println("강연 엔터티 생성 테스트 종료")
    }

    /**
     * Lecture 엔터티 복사 메서드 테스트
     */
    @Test
    fun `Lecture 엔터티 복사 메서드 테스트`() {
        val now = Instant.now()
        val lecture = Lecture(
            id = 1,
            lecturer = "홍길동",
            location = "강의실 101",
            maxSeats = 100,
            lectureTime = now,
            createdAt = now,
            updatedAt = now,
            description = "예시 강연"
        )

        val copiedLecture = lecture.copy(maxSeats = 150)

        // 단계별 출력
        println("강연 엔터티 복사 메서드 테스트 시작")
        println("원본 강연 ID: ${lecture.id}")
        println("원본 강연자: ${lecture.lecturer}")
        println("원본 강의실 위치: ${lecture.location}")
        println("원본 최대 좌석 수: ${lecture.maxSeats}")
        println("원본 강연 시간: ${lecture.lectureTime}")
        println("원본 생성 시간: ${lecture.createdAt}")
        println("원본 수정 시간: ${lecture.updatedAt}")
        println("원본 강연 설명: ${lecture.description}")

        println("복사된 강연 ID: ${copiedLecture.id}")
        println("복사된 강연자: ${copiedLecture.lecturer}")
        println("복사된 강의실 위치: ${copiedLecture.location}")
        println("복사된 최대 좌석 수: ${copiedLecture.maxSeats}")
        println("복사된 강연 시간: ${copiedLecture.lectureTime}")
        println("복사된 생성 시간: ${copiedLecture.createdAt}")
        println("복사된 수정 시간: ${copiedLecture.updatedAt}")
        println("복사된 강연 설명: ${copiedLecture.description}")

        assertEquals(1, copiedLecture.id)
        assertEquals("홍길동", copiedLecture.lecturer)
        assertEquals("강의실 101", copiedLecture.location)
        assertEquals(150, copiedLecture.maxSeats) // 변경된 값
        assertEquals(now, copiedLecture.lectureTime)
        assertEquals(now, copiedLecture.createdAt)
        assertEquals(now, copiedLecture.updatedAt)
        assertEquals("예시 강연", copiedLecture.description)

        println("강연 엔터티 복사 메서드 테스트 종료")
    }
}
