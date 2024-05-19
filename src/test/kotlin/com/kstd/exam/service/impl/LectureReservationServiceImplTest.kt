package com.kstd.exam.service

import com.kstd.exam.domain.Lecture
import com.kstd.exam.domain.LectureReservation
import com.kstd.exam.repository.LectureRepository
import com.kstd.exam.repository.LectureReservationRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*

class LectureReservationServiceImplTest {

    @Mock
    private lateinit var lectureRepository: LectureRepository

    @Mock
    private lateinit var lectureReservationRepository: LectureReservationRepository

    @InjectMocks
    private lateinit var lectureReservationService: LectureReservationServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    /**
     * 강연 예약 성공 테스트
     */
    @Test
    fun `강연 예약 성공 테스트`() {
        // Arrange
        println("강연 예약 성공 테스트 준비 중입니다.")
        val now = Instant.now()
        val lecture = Lecture(
            id = 1,
            lecturer = "김태형",
            location = "서울시 동작구",
            maxSeats = 50,
            lectureTime = now,
            createdAt = now,
            updatedAt = now,
            description = "테스터 1"
        )
        val reservation = LectureReservation(
            id = 1,
            lectureId = 1,
            employeeId = "7777",
            createdAt = now,
            updatedAt = now,
            cancelYn = 'N'
        )

        `when`(lectureRepository.findById(1L)).thenReturn(Optional.of(lecture))
        `when`(lectureReservationRepository.findByEmployeeIdAndLectureId("7777", 1L)).thenReturn(null)
        `when`(lectureReservationRepository.save(any(LectureReservation::class.java))).thenReturn(reservation)

        // Act
        println("강연 예약 시도 중입니다.")
        val result = lectureReservationService.reserveLecture("7777", 1L)

        // Assert
        println("강연 예약 결과 확인 중입니다.")
        assertNotNull(result)
        assertEquals("7777", result.employeeId)
        assertEquals(1L, result.lectureId)
        verify(lectureRepository, times(1)).findById(1L)
        verify(lectureReservationRepository, times(1)).findByEmployeeIdAndLectureId("7777", 1L)
        verify(lectureReservationRepository, times(1)).save(any(LectureReservation::class.java))
        println("강연 예약 성공 테스트 완료")
    }

    /**
     * 강연 예약 중복 시 예외 발생 테스트
     */
    @Test
    fun `강연 예약 중복 테스트`() {
        // Arrange
        println("강연 예약 중복 테스트 준비 중입니다.")
        val now = Instant.now()
        val reservation = LectureReservation(
            id = 1,
            lectureId = 1,
            employeeId = "7777",
            createdAt = now,
            updatedAt = now,
            cancelYn = 'N'
        )

        `when`(lectureReservationRepository.findByEmployeeIdAndLectureId("7777", 1L)).thenReturn(reservation)

        // Act & Assert
        println("강연 예약 중복 시도 중입니다.")
        val exception = assertThrows(IllegalArgumentException::class.java) {
            lectureReservationService.reserveLecture("7777", 1L)
        }
        println("강연 예약 중복 결과 확인 중입니다.")
        assertEquals("이 사원은 이미 이 강연을 예약했습니다.", exception.message)
        verify(lectureReservationRepository, times(1)).findByEmployeeIdAndLectureId("7777", 1L)
        println("강연 예약 중복 테스트 완료")
    }

    /**
     * 강연 찾을 수 없을 때 예외 발생 테스트
     */
    @Test
    fun `강연 찾기 실패 테스트`() {
        // Arrange
        println("강연 찾기 실패 테스트 준비 중입니다.")
        `when`(lectureRepository.findById(1L)).thenReturn(Optional.empty())

        // Act & Assert
        println("강연 찾기 시도 중입니다.")
        val exception = assertThrows(IllegalArgumentException::class.java) {
            lectureReservationService.reserveLecture("7777", 1L)
        }
        println("강연 찾기 실패 결과 확인 중입니다.")
        assertEquals("강연을 찾을 수 없습니다.", exception.message)
        verify(lectureRepository, times(1)).findById(1L)
        println("강연 찾기 실패 테스트 완료")
    }

    /**
     * 특정 강연의 예약 목록 조회 테스트
     */
    @Test
    fun `강연 예약 목록 조회 테스트`() {
        // Arrange
        println("강연 예약 목록 조회 테스트 준비 중입니다.")
        val now = Instant.now()
        val reservation = LectureReservation(
            id = 1,
            lectureId = 1,
            employeeId = "7777",
            createdAt = now,
            updatedAt = now,
            cancelYn = 'N'
        )

        `when`(lectureReservationRepository.findByLectureId(1L)).thenReturn(listOf(reservation))

        // Act
        println("강연 예약 목록 조회 시도 중입니다.")
        val result = lectureReservationService.getReservationsByLectureId(1L)

        // Assert
        println("강연 예약 목록 결과 확인 중입니다.")
        assertEquals(1, result.size)
        assertEquals("7777", result[0].employeeId)
        verify(lectureReservationRepository, times(1)).findByLectureId(1L)
        println("강연 예약 목록 조회 테스트 완료")
    }

    /**
     * 강연 예약 취소 테스트
     */
    @Test
    fun `강연 예약 취소 테스트`() {
        // Arrange
        println("강연 예약 취소 테스트 준비 중입니다.")
        val now = Instant.now()
        val reservation = LectureReservation(
            id = 1,
            lectureId = 1,
            employeeId = "7777",
            createdAt = now,
            updatedAt = now,
            cancelYn = 'N'
        )

        `when`(lectureReservationRepository.findByEmployeeIdAndLectureId("7777", 1L)).thenReturn(reservation)
        `when`(lectureReservationRepository.save(any(LectureReservation::class.java))).thenAnswer { invocation ->
            val updatedReservation = invocation.getArgument<LectureReservation>(0)
            assertEquals('Y', updatedReservation.cancelYn)
            updatedReservation
        }

        // Act
        println("강연 예약 취소 시도 중입니다.")
        val result = lectureReservationService.cancelLectureReservation("7777", 1L)

        // Assert
        println("강연 예약 취소 결과 확인 중입니다.")
        assertEquals('Y', result.cancelYn)
        verify(lectureReservationRepository, times(1)).findByEmployeeIdAndLectureId("7777", 1L)
        verify(lectureReservationRepository, times(1)).save(any(LectureReservation::class.java))
        println("강연 예약 취소 테스트 완료")
    }

    /**
     * 예약을 찾을 수 없을 때 예외 발생 테스트
     */
    @Test
    fun `예약 찾기 실패 테스트`() {
        // Arrange
        println("예약 찾기 실패 테스트 준비 중입니다.")
        `when`(lectureReservationRepository.findByEmployeeIdAndLectureId("7777", 1L)).thenReturn(null)

        // Act & Assert
        println("예약 찾기 시도 중입니다.")
        val exception = assertThrows(IllegalArgumentException::class.java) {
            lectureReservationService.cancelLectureReservation("7777", 1L)
        }
        println("예약 찾기 실패 결과 확인 중입니다.")
        assertEquals("이 사원은 이 강연을 예약하지 않았습니다.", exception.message)
        verify(lectureReservationRepository, times(1)).findByEmployeeIdAndLectureId("7777", 1L)
        println("예약 찾기 실패 테스트 완료")
    }

    /**
     * 인기 강연 조회 테스트
     */
    @Test
    fun `인기 강연 조회 테스트`() {
        // Arrange
        println("인기 강연 조회 테스트 준비 중입니다.")
        val popularLectures = listOf(
            mapOf("lectureId" to 1L, "reservationCount" to 5L),
            mapOf("lectureId" to 2L, "reservationCount" to 3L)
        )

        `when`(lectureReservationRepository.findPopularLectures()).thenReturn(popularLectures)

        // Act
        println("인기 강연 조회 시도 중입니다.")
        val result = lectureReservationService.getPopularLectures()

        // Assert
        println("인기 강연 조회 결과 확인 중입니다.")
        assertEquals(2, result.size)
        assertEquals(1L, result[0].lectureId)
        assertEquals(5L, result[0].reservationCount)
        verify(lectureReservationRepository, times(1)).findPopularLectures()
        println("인기 강연 조회 테스트 완료")
    }

    /**
     * 사번으로 강연 신청 내역 조회 테스트
     */
    @Test
    fun `사번으로 강연 신청 내역 조회 테스트`() {
        // Arrange
        println("사번으로 강연 신청 내역 조회 테스트 준비 중입니다.")
        val now = Instant.now()
        val reservation1 = LectureReservation(
            id = 1,
            lectureId = 1,
            employeeId = "7777",
            createdAt = now,
            updatedAt = now,
            cancelYn = 'N'
        )
        val reservation2 = LectureReservation(
            id = 2,
            lectureId = 2,
            employeeId = "7777",
            createdAt = now,
            updatedAt = now,
            cancelYn = 'N'
        )

        `when`(lectureReservationRepository.findByEmployeeIdAndNotCancelled("7777")).thenReturn(listOf(reservation1, reservation2))

        // Act
        println("사번으로 강연 신청 내역 조회 시도 중입니다.")
        val result = lectureReservationService.getReservationsByEmployeeId("7777")

        // Assert
        println("사번으로 강연 신청 내역 결과 확인 중입니다.")
        assertEquals(2, result.size)
        assertEquals("7777", result[0].employeeId)
        assertEquals("7777", result[1].employeeId)
        verify(lectureReservationRepository, times(1)).findByEmployeeIdAndNotCancelled("7777")
        println("사번으로 강연 신청 내역 조회 테스트 완료")
    }
}
