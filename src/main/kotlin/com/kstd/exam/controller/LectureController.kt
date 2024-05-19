package com.kstd.exam.controller

import com.kstd.exam.controller.dto.MultiResultDto
import com.kstd.exam.controller.dto.SingleResultDto
import com.kstd.exam.domain.Lecture
import com.kstd.exam.service.LectureService
import com.kstd.exam.util.ResponseUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lecture")
class LectureController(private val lectureService: LectureService) {

    private val logger: Logger = LoggerFactory.getLogger(LectureController::class.java)

    /**
     * 강연 생성 API
     * @param lecture 생성할 강연 정보
     * @return 생성된 강연 정보와 성공 메시지 또는 오류 메시지
     */
    @PostMapping
    fun createLecture(@RequestBody lecture: Lecture): SingleResultDto<Lecture?> {
        logger.info("강연 생성 요청을 받았습니다: {}", lecture)
        return try {
            val createdLecture = lectureService.createLecture(lecture)
            ResponseUtils.success(createdLecture, "강연이 성공적으로 생성되었습니다")
        } catch (ex: IllegalArgumentException) {
            logger.error("강연 생성 중 오류 발생 - 잘못된 인수: ", ex)
            ResponseUtils.error("잘못된 인수입니다: ${ex.message}")
        } catch (ex: IllegalStateException) {
            logger.error("강연 생성 중 오류 발생 - 잘못된 상태: ", ex)
            ResponseUtils.error("잘못된 상태입니다: ${ex.message}")
        } catch (ex: Exception) {
            logger.error("강연 생성 중 내부 서버 오류 발생: ", ex)
            ResponseUtils.error("내부 서버 오류: ${ex.message}")
        }
    }

    /**
     * 모든 강연 목록 조회 API
     * @return 모든 강연 정보와 성공 메시지 또는 오류 메시지
     */
    @GetMapping
    fun getAllLectures(): MultiResultDto<Lecture> {
        logger.info("모든 강연 조회 요청을 받았습니다")
        return try {
            val lectures = lectureService.getAllLectures()
            ResponseUtils.multiSuccess(lectures, "강연 목록 조회 성공")
        } catch (ex: IllegalArgumentException) {
            logger.error("강연 목록 조회 중 오류 발생 - 잘못된 인수: ", ex)
            ResponseUtils.multiError("잘못된 인수입니다: ${ex.message}")
        } catch (ex: IllegalStateException) {
            logger.error("강연 목록 조회 중 오류 발생 - 잘못된 상태: ", ex)
            ResponseUtils.multiError("잘못된 상태입니다: ${ex.message}")
        } catch (ex: Exception) {
            logger.error("강연 목록 조회 중 내부 서버 오류 발생: ", ex)
            ResponseUtils.multiError("내부 서버 오류: ${ex.message}")
        }
    }

    /**
     * 신청 가능한 강연 목록 조회 API
     * @return 신청 가능한 강연 정보와 성공 메시지 또는 오류 메시지
     */
    @GetMapping("/available")
    fun getAvailableLectures(): MultiResultDto<Lecture> {
        logger.info("신청 가능한 강연 목록 조회 요청을 받았습니다")
        return try {
            val lectures = lectureService.getAvailableLectures()
            ResponseUtils.multiSuccess(lectures, "신청 가능한 강연 목록 조회 성공")
        } catch (ex: IllegalArgumentException) {
            logger.error("신청 가능한 강연 목록 조회 중 오류 발생 - 잘못된 인수: ", ex)
            ResponseUtils.multiError("잘못된 인수입니다: ${ex.message}")
        } catch (ex: IllegalStateException) {
            logger.error("신청 가능한 강연 목록 조회 중 오류 발생 - 잘못된 상태: ", ex)
            ResponseUtils.multiError("잘못된 상태입니다: ${ex.message}")
        } catch (ex: Exception) {
            logger.error("신청 가능한 강연 목록 조회 중 내부 서버 오류 발생: ", ex)
            ResponseUtils.multiError("내부 서버 오류: ${ex.message}")
        }
    }
}
