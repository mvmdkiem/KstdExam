package com.kstd.exam.repository

import com.kstd.exam.domain.Lecture
import com.kstd.exam.domain.LectureReservation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.Instant

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LectureReservationRepositoryTest @Autowired constructor(
    val lectureRepository: LectureRepository,
    val lectureReservationRepository: LectureReservationRepository
) {

    private val logger = LoggerFactory.getLogger(LectureReservationRepositoryTest::class.java)

    /**
     * 강연 예약 저장 및 조회 테스트
     */
    @Test
    fun `강연 예약 저장 및 조회 테스트`() {
        val now = Instant.now()
        val lecture = Lecture(
            lecturer = "김태형",
            location = "서울시 동작구 대방동 현대 아파트 102동",
            maxSeats = 50,
            lectureTime = now,
            createdAt = now,
            updatedAt = now,
            description = "테스터 1"
        )

        val savedLecture = lectureRepository.save(lecture)
        val reservation = LectureReservation(
            lectureId = savedLecture.id!!,
            employeeId = "12345",
            createdAt = now,
            updatedAt = now
        )

        val savedReservation = lectureReservationRepository.save(reservation)

        logger.info("저장된 강연 예약: $savedReservation")
        assertNotNull(savedReservation.id)

        val foundReservation = lectureReservationRepository.findByEmployeeIdAndLectureId("12345", savedLecture.id!!)
        logger.info("조회된 강연 예약: $foundReservation")
        assertNotNull(foundReservation)

        assertEquals("12345", foundReservation?.employeeId)
        assertEquals(savedLecture.id, foundReservation?.lectureId)
        assertEquals(now, foundReservation?.createdAt)
        assertEquals(now, foundReservation?.updatedAt)
        assertEquals('N', foundReservation?.cancelYn)
    }

    /**
     * 인기 강연 조회 테스트
     */
    @Test
    fun `인기 강연 조회 테스트`() {
        val now = Instant.now()
        val lecture1 = Lecture(
            lecturer = "김태형",
            location = "서울시 동작구 대방동 현대 아파트 102동",
            maxSeats = 50,
            lectureTime = now,
            createdAt = now,
            updatedAt = now,
            description = "테스터 1"
        )
        val lecture2 = Lecture(
            lecturer = "이민호",
            location = "서울시 강남구 삼성동",
            maxSeats = 100,
            lectureTime = now,
            createdAt = now,
            updatedAt = now,
            description = "테스터 2"
        )

        val savedLecture1 = lectureRepository.save(lecture1)
        val savedLecture2 = lectureRepository.save(lecture2)

        val reservation1 = LectureReservation(
            lectureId = savedLecture1.id!!,
            employeeId = "12345",
            createdAt = now,
            updatedAt = now
        )
        val reservation2 = LectureReservation(
            lectureId = savedLecture2.id!!,
            employeeId = "67890",
            createdAt = now,
            updatedAt = now
        )
        val reservation3 = LectureReservation(
            lectureId = savedLecture2.id!!,
            employeeId = "54321",
            createdAt = now,
            updatedAt = now
        )

        lectureReservationRepository.saveAll(listOf(reservation1, reservation2, reservation3))

        val popularLectures = lectureReservationRepository.findPopularLectures()
        logger.info("인기 강연: $popularLectures")

        assertEquals(2, popularLectures.size)

        // 추가된 출력 부분
        popularLectures.forEachIndexed { index, lecture ->
            logger.info("강연 $index: 강연 ID=${lecture["lectureId"]}, 예약 수=${lecture["reservationCount"]}")
        }

        assertEquals(savedLecture2.id, popularLectures[0]["lectureId"])
        assertEquals(2L, popularLectures[0]["reservationCount"])
        assertEquals(savedLecture1.id, popularLectures[1]["lectureId"])
        assertEquals(1L, popularLectures[1]["reservationCount"])
    }
}
