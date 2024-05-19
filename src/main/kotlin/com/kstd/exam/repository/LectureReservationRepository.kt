package com.kstd.exam.repository

import com.kstd.exam.domain.LectureReservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * LectureReservation 엔티티를 위한 JPA 레포지토리 인터페이스
 * 강연 예약 데이터를 데이터베이스에서 조회, 저장, 삭제 등의 작업을 수행합니다.
 */
interface LectureReservationRepository : JpaRepository<LectureReservation, Long> {

    /**
     * 사원 ID와 강연 ID로 강연 예약을 조회합니다.
     *
     * @param employeeId 사원 ID
     * @param lectureId 강연 ID
     * @return 사원 ID와 강연 ID에 해당하는 강연 예약을 포함하는 객체
     */
    fun findByEmployeeIdAndLectureId(employeeId: String, lectureId: Long): LectureReservation?

    /**
     * 사원 ID로 취소되지 않은 강연 예약 목록을 조회합니다.
     *
     * @param employeeId 사원 ID
     * @return 취소되지 않은 강연 예약 목록
     */
    @Query("SELECT lr FROM LectureReservation lr WHERE lr.employeeId = :employeeId AND lr.cancelYn = 'N' ORDER BY lr.createdAt ASC")
    fun findByEmployeeIdAndNotCancelled(employeeId: String): List<LectureReservation>


    /**
     * 강연 ID로 강연 예약 목록을 조회합니다.
     *
     * @param lectureId 강연 ID
     * @return 강연 ID에 해당하는 강연 예약 목록
     */
    fun findByLectureId(lectureId: Long): List<LectureReservation>

    /**
     * 인기 강연 목록을 조회합니다.
     * 취소되지 않은 강연 예약 수를 기준으로 인기 강연을 조회하며,
     * 예약 수를 기준으로 내림차순으로 정렬합니다.
     *
     * @return 인기 강연 목록을 포함하는 맵의 리스트 (강연 ID와 예약 수를 포함)
     */
    @Query("SELECT lr.lectureId as lectureId, COUNT(lr.id) as reservationCount FROM LectureReservation lr WHERE lr.cancelYn = 'N' GROUP BY lr.lectureId ORDER BY reservationCount DESC")
    fun findPopularLectures(): List<Map<String, Any>>
}