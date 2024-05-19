package com.kstd.exam.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant

class LectureReservationTest {

    /**
     * LectureReservation 엔터티 생성 테스트
     */
    @Test
    fun `LectureReservation 엔터티 생성 테스트`() {
        val now = Instant.now()
        val reservation = LectureReservation(
            id = 1,
            lectureId = 101,
            employeeId = "12345",
            createdAt = now,
            updatedAt = now,
            cancelYn = 'N'
        )

        // 단계별 출력
        println("강연 예약 엔터티 생성 테스트 시작")
        println("예약 ID: ${reservation.id}")
        println("강연 ID: ${reservation.lectureId}")
        println("사원 ID: ${reservation.employeeId}")
        println("예약 생성 시간: ${reservation.createdAt}")
        println("예약 수정 시간: ${reservation.updatedAt}")
        println("예약 취소 여부: ${reservation.cancelYn}")

        assertEquals(1, reservation.id)
        assertEquals(101, reservation.lectureId)
        assertEquals("12345", reservation.employeeId)
        assertEquals(now, reservation.createdAt)
        assertEquals(now, reservation.updatedAt)
        assertEquals('N', reservation.cancelYn)

        println("강연 예약 엔터티 생성 테스트 종료")
    }
}
