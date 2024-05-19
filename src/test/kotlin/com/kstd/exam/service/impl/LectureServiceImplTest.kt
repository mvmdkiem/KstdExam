package com.kstd.exam.service

import com.kstd.exam.domain.Lecture
import com.kstd.exam.repository.LectureRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import java.time.Instant

class LectureServiceImplTest {

    @Mock
    private lateinit var lectureRepository: LectureRepository

    @InjectMocks
    private lateinit var lectureService: LectureServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    /**
     * 강연 생성 테스트
     */
    @Test
    fun `강연 생성 테스트`() {
        // Arrange
        println("강연 생성 테스트 준비 중입니다.")
        val lecture = Lecture(
            lecturer = "김철수",
            location = "101호 강의실",
            maxSeats = 100,
            lectureTime = Instant.now(),
            description = "강의 1"
        )
        val savedLecture = lecture.copy(id = 1L)

        `when`(lectureRepository.save(lecture)).thenReturn(savedLecture)

        // Act
        println("강연 생성 시도 중입니다.")
        val result = lectureService.createLecture(lecture)

        // Assert
        println("강연 생성 결과 확인 중입니다.")
        assertNotNull(result)
        assertEquals(1L, result.id)
        assertEquals("김철수", result.lecturer)
        verify(lectureRepository, times(1)).save(lecture)
        println("강연 생성 테스트 완료")
    }
}
