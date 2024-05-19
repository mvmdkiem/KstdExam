package com.kstd.exam.repository

import com.kstd.exam.domain.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import jakarta.persistence.LockModeType
import java.time.Instant
import java.util.Optional

/**
 * Lecture 엔티티를 위한 JPA 레포지토리 인터페이스
 * 강연 데이터를 데이터베이스에서 조회, 저장, 삭제 등의 작업을 수행합니다.
 */
interface LectureRepository : JpaRepository<Lecture, Long> {

    /**
     * PESSIMISTIC_WRITE 락 모드를 사용하여 ID로 강연을 조회합니다.
     * 이 메서드는 동시에 여러 트랜잭션이 같은 강연을 수정하지 않도록 락을 겁니다.
     *
     * @param id 강연 ID
     * @return ID에 해당하는 강연을 포함하는 Optional 객체
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l WHERE l.id = :id ORDER BY l.lectureTime ASC")
    fun findByIdWithLock(id: Long): Optional<Lecture>

    /**
     * 신청 가능한 강연 목록을 조회합니다.
     * 강연 시작 1주일 전부터 강연 시작 시간 1일 후까지의 강연을 조회합니다.
     * 강의 시간별로 오름차순 정렬합니다.
     *
     * @param oneWeekBefore 강연 시작 1주일 전
     * @param oneDayAfter 강연 시작 시간 1일 후
     * @return 신청 가능한 강연 목록
     */
    @Query("SELECT l FROM Lecture l WHERE l.lectureTime BETWEEN :oneWeekBefore AND :oneDayAfter ORDER BY l.lectureTime ASC")
    fun findAvailableLectures(oneWeekBefore: Instant, oneDayAfter: Instant): List<Lecture>
}
