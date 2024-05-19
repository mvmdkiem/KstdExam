package com.kstd.exam.controller

import com.kstd.exam.controller.dto.MultiResultDto
import com.kstd.exam.controller.dto.PopularLectureResponse
import com.kstd.exam.controller.dto.ReserveLectureRequest
import com.kstd.exam.controller.dto.SingleResultDto
import com.kstd.exam.domain.LectureReservation
import com.kstd.exam.service.LectureReservationService
import com.kstd.exam.util.ResponseUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lecture")
class LectureReservationController(private val lectureReservationService: LectureReservationService) {

    private val logger: Logger = LoggerFactory.getLogger(LectureReservationController::class.java)

    /**
     * 강연 예약 API
     * @param lectureId 예약할 강연의 ID
     * @param request 예약 요청 정보 (사원 ID 포함)
     * @return 예약된 강연 정보와 성공 메시지 또는 오류 메시지
     */
    @PostMapping("/{lectureId}/reserve")
    fun reserveLecture(@PathVariable lectureId: Long, @RequestBody request: ReserveLectureRequest): SingleResultDto<LectureReservation?> {
        logger.info("강연 예약 요청을 받았습니다: lectureId={}, employeeId={}", lectureId, request.employeeId)
        return try {
            val reservation = lectureReservationService.reserveLecture(request.employeeId, lectureId)
            ResponseUtils.success(reservation, "강연이 성공적으로 예약되었습니다")
        } catch (ex: IllegalArgumentException) {
            logger.error("강연 예약 중 오류 발생 - 잘못된 인수: ", ex)
            ResponseUtils.error("잘못된 인수입니다: ${ex.message}")
        } catch (ex: IllegalStateException) {
            logger.error("강연 예약 중 오류 발생 - 잘못된 상태: ", ex)
            ResponseUtils.error("잘못된 상태입니다: ${ex.message}")
        } catch (ex: Exception) {
            logger.error("강연 예약 중 내부 서버 오류 발생: ", ex)
            ResponseUtils.error("내부 서버 오류: ${ex.message}")
        }
    }

    /**
     * 신청 내역 조회 API
     * @param employeeId 신청 내역을 조회할 사번
     * @return 신청한 강연 목록과 성공 메시지 또는 오류 메시지
     */
    @GetMapping("/reservations/{employeeId}")
    fun getReservationsByEmployeeId(@PathVariable employeeId: String): MultiResultDto<LectureReservation> {
        logger.info("신청 내역 조회 요청을 받았습니다: employeeId={}", employeeId)
        return try {
            val reservations = lectureReservationService.getReservationsByEmployeeId(employeeId)
            ResponseUtils.multiSuccess(reservations, "신청 내역 조회 성공")
        } catch (ex: IllegalArgumentException) {
            logger.error("신청 내역 조회 중 오류 발생 - 잘못된 인수: ", ex)
            ResponseUtils.multiError("잘못된 인수입니다: ${ex.message}")
        } catch (ex: IllegalStateException) {
            logger.error("신청 내역 조회 중 오류 발생 - 잘못된 상태: ", ex)
            ResponseUtils.multiError("잘못된 상태입니다: ${ex.message}")
        } catch (ex: Exception) {
            logger.error("신청 내역 조회 중 내부 서버 오류 발생: ", ex)
            ResponseUtils.multiError("내부 서버 오류: ${ex.message}")
        }
    }

    /**
     * 강연 예약 목록 조회 API
     * @param lectureId 예약 목록을 조회할 강연의 ID
     * @return 예약된 강연 목록과 성공 메시지 또는 오류 메시지
     */
    @GetMapping("/{lectureId}/reservations")
    fun getLectureReservations(@PathVariable lectureId: Long): MultiResultDto<LectureReservation> {
        logger.info("강연 예약 목록 조회 요청을 받았습니다: lectureId={}", lectureId)
        return try {
            val reservations = lectureReservationService.getReservationsByLectureId(lectureId)
            ResponseUtils.multiSuccess(reservations, "강연 예약 목록 조회 성공")
        } catch (ex: IllegalArgumentException) {
            logger.error("강연 예약 목록 조회 중 오류 발생 - 잘못된 인수: ", ex)
            ResponseUtils.multiError("잘못된 인수입니다: ${ex.message}")
        } catch (ex: IllegalStateException) {
            logger.error("강연 예약 목록 조회 중 오류 발생 - 잘못된 상태: ", ex)
            ResponseUtils.multiError("잘못된 상태입니다: ${ex.message}")
        } catch (ex: Exception) {
            logger.error("강연 예약 목록 조회 중 내부 서버 오류 발생: ", ex)
            ResponseUtils.multiError("내부 서버 오류: ${ex.message}")
        }
    }

    /**
     * 강연 예약 취소 API
     * @param lectureId 예약을 취소할 강연의 ID
     * @param request 예약 취소 요청 정보 (사원 ID 포함)
     * @return 취소된 예약 정보와 성공 메시지 또는 오류 메시지
     */
    @PutMapping("/{lectureId}/cancel")
    fun cancelLectureReservation(@PathVariable lectureId: Long, @RequestBody request: ReserveLectureRequest): SingleResultDto<LectureReservation?> {
        logger.info("강연 예약 취소 요청을 받았습니다: lectureId={}, employeeId={}", lectureId, request.employeeId)
        return try {
            val reservation = lectureReservationService.cancelLectureReservation(request.employeeId, lectureId)
            ResponseUtils.success(reservation, "강연 예약이 성공적으로 취소되었습니다")
        } catch (ex: IllegalArgumentException) {
            logger.error("강연 예약 취소 중 오류 발생 - 잘못된 인수: ", ex)
            ResponseUtils.error("잘못된 인수입니다: ${ex.message}")
        } catch (ex: IllegalStateException) {
            logger.error("강연 예약 취소 중 오류 발생 - 잘못된 상태: ", ex)
            ResponseUtils.error("잘못된 상태입니다: ${ex.message}")
        } catch (ex: Exception) {
            logger.error("강연 예약 취소 중 내부 서버 오류 발생: ", ex)
            ResponseUtils.error("내부 서버 오류: ${ex.message}")
        }
    }

    /**
     * 인기 강연 조회 API
     * @return 인기 강연 목록과 성공 메시지 또는 오류 메시지
     */
    @GetMapping("/popular")
    fun getPopularLectures(): MultiResultDto<PopularLectureResponse> {
        logger.info("실시간 인기 강연 조회 요청을 받았습니다")
        return try {
            val popularLectures = lectureReservationService.getPopularLectures()
            ResponseUtils.multiSuccess(popularLectures, "실시간 인기 강연 조회 성공")
        } catch (ex: Exception) {
            logger.error("실시간 인기 강연 조회 중 내부 서버 오류 발생: ", ex)
            ResponseUtils.multiError("내부 서버 오류: ${ex.message}")
        }
    }
}
