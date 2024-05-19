package com.kstd.exam.service

import com.kstd.exam.controller.dto.PopularLectureResponse
import com.kstd.exam.domain.LectureReservation
import com.kstd.exam.repository.LectureReservationRepository
import com.kstd.exam.repository.LectureRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class LectureReservationServiceImpl(
    private val lectureRepository: LectureRepository,
    private val lectureReservationRepository: LectureReservationRepository
) : LectureReservationService {

    private val logger: Logger = LoggerFactory.getLogger(LectureReservationServiceImpl::class.java)

    /**
     * 강연 예약 메서드
     *
     * @param employeeId 사원 ID
     * @param lectureId 강연 ID
     * @return 예약된 강연 정보
     * @throws IllegalArgumentException 잘못된 인수로 인한 예외
     * @throws IllegalStateException 잘못된 상태로 인한 예외
     * @throws Exception 기타 예외
     */
    @Transactional
    override fun reserveLecture(employeeId: String, lectureId: Long): LectureReservation {
        logger.info("강연 예약: lectureId={}, employeeId={}", lectureId, employeeId)
        try {
            // 사원이 이미 강연을 예약했는지 확인
            val existingReservation = lectureReservationRepository.findByEmployeeIdAndLectureId(employeeId, lectureId)

            // 강연 정보 조회 및 잠금 설정
            val lecture = lectureRepository.findByIdWithLock(lectureId).orElseThrow { IllegalArgumentException("강연을 찾을 수 없습니다.") }

            if (existingReservation != null) {
                return if (existingReservation.cancelYn == 'Y') {
                    // 기존 예약이 취소된 경우, 예약 정보를 업데이트
                    existingReservation.cancelYn = 'N'
                    existingReservation.updatedAt = Instant.now()
                    lectureReservationRepository.save(existingReservation).also {
                        logger.info("강연 재예약 성공: {}", it)
                    }
                } else {
                    // 이미 예약된 경우 예외 발생
                    throw IllegalArgumentException("이 사원은 이미 이 강연을 예약했습니다.")
                }
            } else {
                // 새로운 강연 예약 생성
                val reservation = LectureReservation(
                    lectureId = lecture.id!!,
                    employeeId = employeeId,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    cancelYn = 'N'
                )

                // 강연 예약 저장 및 로그 출력
                return lectureReservationRepository.save(reservation).also {
                    logger.info("강연 예약 성공: {}", it)
                }
            }
        } catch (ex: IllegalArgumentException) {
            // 잘못된 인수로 인한 예외 처리
            logger.error("강연 예약 중 오류 발생 - 잘못된 인수: ", ex)
            throw ex
        } catch (ex: IllegalStateException) {
            // 잘못된 상태로 인한 예외 처리
            logger.error("강연 예약 중 오류 발생 - 잘못된 상태: ", ex)
            throw ex
        } catch (ex: Exception) {
            // 기타 예외 처리
            logger.error("강연 예약 중 내부 서버 오류 발생: ", ex)
            throw ex
        }
    }

    /**
     * 사번으로 강연 신청 내역 조회
     *
     * @param employeeId 사원 ID
     * @return 강연 예약 목록
     * @throws IllegalArgumentException 잘못된 인수로 인한 예외
     * @throws IllegalStateException 잘못된 상태로 인한 예외
     * @throws Exception 기타 예외
     */
    override fun getReservationsByEmployeeId(employeeId: String): List<LectureReservation> {
        logger.info("강연 신청 내역 조회: employeeId={}", employeeId)
        return try {
            lectureReservationRepository.findByEmployeeIdAndNotCancelled(employeeId).also {
                logger.info("강연 신청 내역 조회 성공: {}", it.size)
            }
        } catch (ex: IllegalArgumentException) {
            logger.error("강연 신청 내역 조회 중 오류 발생 - 잘못된 인수: ", ex)
            throw ex
        } catch (ex: IllegalStateException) {
            logger.error("강연 신청 내역 조회 중 오류 발생 - 잘못된 상태: ", ex)
            throw ex
        } catch (ex: Exception) {
            logger.error("강연 신청 내역 조회 중 내부 서버 오류 발생: ", ex)
            throw ex
        }
    }

    /**
     * 특정 강연에 대한 모든 예약 조회
     *
     * @param lectureId 강연 ID
     * @return 강연 예약 목록
     * @throws IllegalArgumentException 잘못된 인수로 인한 예외
     * @throws IllegalStateException 잘못된 상태로 인한 예외
     * @throws Exception 기타 예외
     */
    override fun getReservationsByLectureId(lectureId: Long): List<LectureReservation> {
        logger.info("강연 예약 목록 조회: lectureId={}", lectureId)
        return try {
            // 강연 예약 목록 조회 및 로그 출력
            lectureReservationRepository.findByLectureId(lectureId).also {
                logger.info("강연 예약 목록 조회 성공: {}", it.size)
            }
        } catch (ex: IllegalArgumentException) {
            // 잘못된 인수로 인한 예외 처리
            logger.error("강연 예약 목록 조회 중 오류 발생 - 잘못된 인수: ", ex)
            throw ex
        } catch (ex: IllegalStateException) {
            // 잘못된 상태로 인한 예외 처리
            logger.error("강연 예약 목록 조회 중 오류 발생 - 잘못된 상태: ", ex)
            throw ex
        } catch (ex: Exception) {
            // 기타 예외 처리
            logger.error("강연 예약 목록 조회 중 내부 서버 오류 발생: ", ex)
            throw ex
        }
    }

    /**
     * 강연 예약 취소 메서드
     *
     * @param employeeId 사원 ID
     * @param lectureId 강연 ID
     * @return 취소된 강연 예약 정보
     * @throws IllegalArgumentException 잘못된 인수로 인한 예외
     * @throws IllegalStateException 잘못된 상태로 인한 예외
     * @throws Exception 기타 예외
     */
    @Transactional
    override fun cancelLectureReservation(employeeId: String, lectureId: Long): LectureReservation {
        logger.info("강연 예약 취소: lectureId={}, employeeId={}", lectureId, employeeId)
        try {
            // 강연 예약 정보 조회 및 잠금 설정
            val reservation = lectureReservationRepository.findByEmployeeIdAndLectureId(employeeId, lectureId)
                ?: throw IllegalArgumentException("이 사원은 이 강연을 예약하지 않았습니다.")

            // 예약 취소 상태로 업데이트
            reservation.cancelYn = 'Y'
            reservation.updatedAt = Instant.now()

            // 강연 예약 저장 및 로그 출력
            return lectureReservationRepository.save(reservation).also {
                logger.info("강연 예약 취소 성공: {}", it)
            }
        } catch (ex: IllegalArgumentException) {
            // 잘못된 인수로 인한 예외 처리
            logger.error("강연 예약 취소 중 오류 발생 - 잘못된 인수: ", ex)
            throw ex
        } catch (ex: IllegalStateException) {
            // 잘못된 상태로 인한 예외 처리
            logger.error("강연 예약 취소 중 오류 발생 - 잘못된 상태: ", ex)
            throw ex
        } catch (ex: Exception) {
            // 기타 예외 처리
            logger.error("강연 예약 취소 중 내부 서버 오류 발생: ", ex)
            throw ex
        }
    }

    /**
     * 인기 강연 조회 메서드
     *
     * @return 인기 강연 목록
     * @throws Exception 기타 예외
     */
    override fun getPopularLectures(): List<PopularLectureResponse> {
        logger.info("실시간 인기 강연 조회")
        return try {
            // 인기 강연 조회 및 변환
            val results = lectureReservationRepository.findPopularLectures()
            results.map { result ->
                PopularLectureResponse(
                    lectureId = result["lectureId"] as Long,
                    reservationCount = result["reservationCount"] as Long
                )
            }
        } catch (ex: Exception) {
            // 기타 예외 처리
            logger.error("실시간 인기 강연 조회 중 내부 서버 오류 발생: ", ex)
            throw ex
        }
    }
}
