package com.kstd.exam.controller

import com.kstd.exam.domain.Lecture
import com.kstd.exam.service.LectureService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.time.Instant
import org.mockito.kotlin.whenever

/**
 * LectureController 테스트 클래스
 * 강연 관련 API의 동작을 검증합니다.
 */
class LectureControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var lectureService: LectureService

    @InjectMocks
    private lateinit var lectureController: LectureController

    @BeforeEach
    fun setUp() {
        // MockMvc 및 ObjectMapper 설정
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(lectureController).build()
        objectMapper = ObjectMapper().registerModule(JavaTimeModule())
    }

    @Test
    fun `모든 강연 조회 테스트`() {
        // Arrange: Mock 데이터 준비 중입니다.
        println("모든 강연 조회 테스트 준비 중입니다.")
        val lecture1 = Lecture(
            id = 1L,
            lecturer = "홍길동",
            location = "101호 강의실",
            maxSeats = 100,
            lectureTime = Instant.now(),
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            description = "강연 1"
        )
        val lecture2 = Lecture(
            id = 2L,
            lecturer = "김철수",
            location = "102호 강의실",
            maxSeats = 200,
            lectureTime = Instant.now(),
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            description = "강연 2"
        )

        whenever(lectureService.getAllLectures()).thenReturn(listOf(lecture1, lecture2))

        // Act & Assert: API 호출 및 결과 검증 중입니다.
        println("모든 강연 조회 API 호출 및 결과 검증 중입니다.")
        mockMvc.perform(get("/api/lecture")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.resultCode").value("200"))
            .andExpect(jsonPath("$.resultCount").value(2))
            .andExpect(jsonPath("$.resultDatas[0].lecturer").value("홍길동"))
            .andExpect(jsonPath("$.resultDatas[1].lecturer").value("김철수"))
        println("모든 강연 조회 테스트 완료")
    }

    @Test
    fun `신청 가능한 강연 목록 조회 테스트`() {
        // Arrange: Mock 데이터 준비 중입니다.
        println("신청 가능한 강연 목록 조회 테스트 준비 중입니다.")
        val lecture1 = Lecture(
            id = 1L,
            lecturer = "이영희",
            location = "103호 강의실",
            maxSeats = 150,
            lectureTime = Instant.now().plusSeconds(3600),
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            description = "강연 3"
        )
        val lecture2 = Lecture(
            id = 2L,
            lecturer = "박민수",
            location = "104호 강의실",
            maxSeats = 300,
            lectureTime = Instant.now().plusSeconds(7200),
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            description = "강연 4"
        )

        whenever(lectureService.getAvailableLectures()).thenReturn(listOf(lecture1, lecture2))

        // Act & Assert: API 호출 및 결과 검증 중입니다.
        println("신청 가능한 강연 목록 조회 API 호출 및 결과 검증 중입니다.")
        mockMvc.perform(get("/api/lecture/available")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.resultCode").value("200"))
            .andExpect(jsonPath("$.resultCount").value(2))
            .andExpect(jsonPath("$.resultDatas[0].lecturer").value("이영희"))
            .andExpect(jsonPath("$.resultDatas[1].lecturer").value("박민수"))
        println("신청 가능한 강연 목록 조회 테스트 완료")
    }
}
