package com.kstd.exam.service

import com.kstd.exam.domain.Lecture
import com.kstd.exam.repository.LectureRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class LectureServiceImpl(private val lectureRepository: LectureRepository) : LectureService {

    private val logger: Logger = LoggerFactory.getLogger(LectureServiceImpl::class.java)

    /**
     * 새로운 강연 생성
     *
     * @param lecture 강연 정보
     * @return 생성된 강연 정보
     */
    override fun createLecture(lecture: Lecture): Lecture {
        logger.info("새로운 강연 생성: {}", lecture)
        return try {
            // 강연 생성 시간과 업데이트 시간을 현재 시간으로 설정
            lecture.createdAt = Instant.now()
            lecture.updatedAt = Instant.now()
            // 강연 정보 저장 및 로그 출력
            lectureRepository.save(lecture).also {
                logger.info("강연 생성 성공: {}", it)
            }
        } catch (ex: IllegalArgumentException) {
            // 잘못된 인수로 인한 예외 처리
            logger.error("강연 생성 중 오류 발생 - 잘못된 인수: ", ex)
            throw ex
        } catch (ex: IllegalStateException) {
            // 잘못된 상태로 인한 예외 처리
            logger.error("강연 생성 중 오류 발생 - 잘못된 상태: ", ex)
            throw ex
        } catch (ex: Exception) {
            // 내부 서버 오류 처리
            logger.error("강연 생성 중 내부 서버 오류 발생: ", ex)
            throw ex
        }
    }

    /**
     * 모든 강연 조회
     *
     * @return 강연 목록
     */
    override fun getAllLectures(): List<Lecture> {
        logger.info("모든 강연 조회 요청")
        return try {
            // 모든 강연 정보 조회 및 로그 출력
            lectureRepository.findAll().also {
                logger.info("총 {}개의 강연을 찾았습니다.", it.size)
            }
        } catch (ex: IllegalArgumentException) {
            // 잘못된 인수로 인한 예외 처리
            logger.error("모든 강연 조회 중 오류 발생 - 잘못된 인수: ", ex)
            throw ex
        } catch (ex: IllegalStateException) {
            // 잘못된 상태로 인한 예외 처리
            logger.error("모든 강연 조회 중 오류 발생 - 잘못된 상태: ", ex)
            throw ex
        } catch (ex: Exception) {
            // 내부 서버 오류 처리
            logger.error("모든 강연 조회 중 내부 서버 오류 발생: ", ex)
            throw ex
        }
    }

    /**
     * 신청 가능한 강연 목록 조회
     *
     * @return 신청 가능한 강연 목록
     */
    override fun getAvailableLectures(): List<Lecture> {
        val now = Instant.now()
        val oneWeekLater = now.plus(7, ChronoUnit.DAYS) // 1주일 후
        val oneDayBefore = now.minus(1, ChronoUnit.DAYS) // 1일 전

        logger.info("신청 가능한 강연 목록 조회 요청 - 쿼리: findAvailableLectures, 파라미터: oneDayBefore={}, oneWeekLater={}", oneDayBefore, oneWeekLater)

        return try {
            lectureRepository.findAvailableLectures(oneDayBefore, oneWeekLater).also {
                logger.info("총 {}개의 신청 가능한 강연을 찾았습니다.", it.size)
                it.forEach { lecture ->
                    logger.info("신청 가능한 강연 - ID: {}, 제목: {}, 시간: {}", lecture.id, lecture.description, lecture.lectureTime)
                }
            }
        } catch (ex: IllegalArgumentException) {
            // 잘못된 인수로 인한 예외 처리
            logger.error("신청 가능한 강연 목록 조회 중 오류 발생 - 잘못된 인수: ", ex)
            throw ex
        } catch (ex: IllegalStateException) {
            // 잘못된 상태로 인한 예외 처리
            logger.error("신청 가능한 강연 목록 조회 중 오류 발생 - 잘못된 상태: ", ex)
            throw ex
        } catch (ex: Exception) {
            // 내부 서버 오류 처리
            logger.error("신청 가능한 강연 목록 조회 중 내부 서버 오류 발생: ", ex)
            throw ex
        }
    }
}
