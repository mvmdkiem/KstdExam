package com.kstd.exam.service

import com.kstd.exam.domain.Lecture

/**
 * 강연 서비스를 위한 인터페이스
 * 강연 관련 기능을 제공합니다.
 */
interface LectureService {
    /**
     * 새로운 강연을 생성합니다.
     *
     * @param lecture 강연 정보
     * @return 생성된 강연 정보
     */
    fun createLecture(lecture: Lecture): Lecture

    /**
     * 모든 강연 목록을 조회합니다.
     *
     * @return 강연 목록
     */
    fun getAllLectures(): List<Lecture>

    /**
     * 신청 가능한 강연 목록을 조회합니다.
     *
     * @return 신청 가능한 강연 목록
     */
    fun getAvailableLectures(): List<Lecture>
}
