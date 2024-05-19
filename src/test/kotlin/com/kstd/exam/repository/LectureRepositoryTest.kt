package com.kstd.exam.repository

import com.kstd.exam.domain.Lecture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ActiveProfiles
import java.time.Instant

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LectureRepositoryTest @Autowired constructor(
    val lectureRepository: LectureRepository
) {

    private val logger = LoggerFactory.getLogger(LectureRepositoryTest::class.java)

    /**
     * 강연 저장 및 조회 테스트
     */
    @Test
    fun `강연 저장 및 조회 테스트`() {
        val now = Instant.now()
        val lecture = Lecture(
            lecturer = "김태형",
            location = "서울시 동작구 대방동",
            maxSeats = 50,
            lectureTime = now,
            createdAt = now,
            updatedAt = now,
            description = "테스터 1"
        )

        // 강연 저장
        val savedLecture = lectureRepository.save(lecture)
        logger.info("저장된 강연: $savedLecture")
        assertNotNull(savedLecture.id)

        // 강연 조회
        val foundLecture = lectureRepository.findById(savedLecture.id!!)
        logger.info("조회된 강연: $foundLecture")
        assertNotNull(foundLecture)

        // 검증
        logger.info("조회된 강연 세부 정보:")
        logger.info("강연자: ${foundLecture.get().lecturer}")
        logger.info("위치: ${foundLecture.get().location}")
        logger.info("최대 좌석 수: ${foundLecture.get().maxSeats}")
        logger.info("강연 시간: ${foundLecture.get().lectureTime}")
        logger.info("생성 시간: ${foundLecture.get().createdAt}")
        logger.info("수정 시간: ${foundLecture.get().updatedAt}")
        logger.info("설명: ${foundLecture.get().description}")

        assertEquals("김태형", foundLecture.get().lecturer)
        assertEquals("서울시 동작구 대방동", foundLecture.get().location)
        assertEquals(50, foundLecture.get().maxSeats)
        assertEquals(now, foundLecture.get().lectureTime)
        assertEquals(now, foundLecture.get().createdAt)
        assertEquals(now, foundLecture.get().updatedAt)
        assertEquals("테스터 1", foundLecture.get().description)
    }
}
