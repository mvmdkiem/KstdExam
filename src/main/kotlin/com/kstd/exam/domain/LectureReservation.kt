
package com.kstd.exam.domain

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "lectures_reservation")
class LectureReservation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "lecture_id", nullable = false)
    var lectureId: Long = 0,

    @Column(name = "employee_id", nullable = false, length = 5)
    var employeeId: String = "",

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Column(name = "cancel_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    var cancelYn: Char = 'N'
) {
    /**
     * 기본 생성자
     * 모든 필드를 기본값으로 초기화합니다.
     */
    constructor() : this(
        id = null,
        lectureId = 0,
        employeeId = "",
        createdAt = Instant.now(),
        updatedAt = Instant.now(),
        cancelYn = 'N'
    )

    /**
     * 복사 메서드
     * 기존 예약 객체를 기반으로 새로운 예약 객체를 생성합니다.
     * @param id 예약 ID
     * @param lectureId 강연 ID
     * @param employeeId 사원 ID
     * @param createdAt 생성 시간
     * @param updatedAt 수정 시간
     * @param cancelYn 취소 여부
     * @return 새로운 예약 객체
     */
    fun copy(
        id: Long? = this.id,
        lectureId: Long = this.lectureId,
        employeeId: String = this.employeeId,
        createdAt: Instant = this.createdAt,
        updatedAt: Instant = this.updatedAt,
        cancelYn: Char = this.cancelYn
    ): LectureReservation {
        return LectureReservation(id, lectureId, employeeId, createdAt, updatedAt, cancelYn)
    }
}