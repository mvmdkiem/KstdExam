package com.kstd.exam.service

import com.kstd.exam.controller.dto.PopularLectureResponse
import com.kstd.exam.domain.LectureReservation

/**
 * 강연 예약 서비스를 위한 인터페이스
 * 강연 예약 관련 기능을 제공합니다.
 */
interface LectureReservationService {
    /**
     * 강연을 예약합니다.
     *
     * @param employeeId 사원 ID
     * @param lectureId 강연 ID
     * @return 생성된 강연 예약 정보
     */
    fun reserveLecture(employeeId: String, lectureId: Long): LectureReservation

    /**
     * 강연 ID로 강연 예약 목록을 조회합니다.
     *
     * @param lectureId 강연 ID
     * @return 강연 예약 목록
     */
    fun getReservationsByLectureId(lectureId: Long): List<LectureReservation>

    /**
     * 회원 ID로 강연 예약 목록을 조회합니다.
     *
     * @param employeeId 회원 ID
     * @return 강연 예약 목록
     */
    fun getReservationsByEmployeeId(employeeId: String): List<LectureReservation>

    /**
     * 강연 예약을 취소합니다.
     *
     * @param employeeId 사원 ID
     * @param lectureId 강연 ID
     * @return 취소된 강연 예약 정보
     */
    fun cancelLectureReservation(employeeId: String, lectureId: Long): LectureReservation

    /**
     * 인기 강연 목록을 조회합니다.
     *
     * @return 인기 강연 목록
     */
    fun getPopularLectures(): List<PopularLectureResponse>
}