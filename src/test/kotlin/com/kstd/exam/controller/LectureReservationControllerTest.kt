package com.kstd.exam.controller

import com.kstd.exam.controller.dto.ReserveLectureRequest
import com.kstd.exam.controller.dto.PopularLectureResponse
import com.kstd.exam.domain.LectureReservation
import com.kstd.exam.service.LectureReservationService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.time.Instant
import org.mockito.kotlin.whenever
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

/**
 * LectureReservationController 테스트 클래스
 * 강연 예약 관련 API의 동작을 검증합니다.
 */
class LectureReservationControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var lectureReservationService: LectureReservationService

    @InjectMocks
    private lateinit var lectureReservationController: LectureReservationController

    @BeforeEach
    fun setUp() {
        // MockMvc 및 ObjectMapper 설정
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(lectureReservationController).build()
        objectMapper = ObjectMapper().registerModule(JavaTimeModule())
    }

    @Test
    fun `강연 예약 테스트`() {
        // Arrange: Mock 데이터 준비 중입니다.
        println("강연 예약 테스트 준비 중입니다.")
        val lectureReservation = LectureReservation(
            id = 1L,
            lectureId = 1L,
            employeeId = "12345",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        val request = ReserveLectureRequest(employeeId = "12345")

        whenever(lectureReservationService.reserveLecture("12345", 1L)).thenReturn(lectureReservation)

        // Act & Assert: API 호출 및 결과 검증 중입니다.
        println("강연 예약 API 호출 및 결과 검증 중입니다.")
        mockMvc.perform(
            post("/api/lecture/1/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.resultCode").value("200"))
            .andExpect(jsonPath("$.resultData.employeeId").value("12345"))
            .andExpect(jsonPath("$.resultData.lectureId").value(1))
        println("강연 예약 테스트 완료")
    }

    @Test
    fun `강연 예약 취소 테스트`() {
        // Arrange: Mock 데이터 준비 중입니다.
        println("강연 예약 취소 테스트 준비 중입니다.")
        val lectureReservation = LectureReservation(
            id = 1L,
            lectureId = 1L,
            employeeId = "12345",
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            cancelYn = 'Y'
        )

        val request = ReserveLectureRequest(employeeId = "12345")

        whenever(lectureReservationService.cancelLectureReservation("12345", 1L)).thenReturn(lectureReservation)

        // Act & Assert: API 호출 및 결과 검증 중입니다.
        println("강연 예약 취소 API 호출 및 결과 검증 중입니다.")
        mockMvc.perform(
            put("/api/lecture/1/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.resultCode").value("200"))
            .andExpect(jsonPath("$.resultData.employeeId").value("12345"))
            .andExpect(jsonPath("$.resultData.lectureId").value(1))
            .andExpect(jsonPath("$.resultData.cancelYn").value("Y"))
        println("강연 예약 취소 테스트 완료")
    }

    @Test
    fun `인기 강연 조회 테스트`() {
        // Arrange: Mock 데이터 준비 중입니다.
        println("인기 강연 조회 테스트 준비 중입니다.")
        val popularLectures = listOf(
            PopularLectureResponse(lectureId = 1L, reservationCount = 10L),
            PopularLectureResponse(lectureId = 2L, reservationCount = 8L)
        )

        whenever(lectureReservationService.getPopularLectures()).thenReturn(popularLectures)

        // Act & Assert: API 호출 및 결과 검증 중입니다.
        println("인기 강연 조회 API 호출 및 결과 검증 중입니다.")
        mockMvc.perform(
            get("/api/lecture/popular")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.resultCode").value("200"))
            .andExpect(jsonPath("$.resultDatas[0].lectureId").value(1))
            .andExpect(jsonPath("$.resultDatas[0].reservationCount").value(10))
            .andExpect(jsonPath("$.resultDatas[1].lectureId").value(2))
            .andExpect(jsonPath("$.resultDatas[1].reservationCount").value(8))
        println("인기 강연 조회 테스트 완료")
    }
}
