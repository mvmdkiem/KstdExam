package com.kstd.exam.domain

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "lectures")
class Lecture(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "lecturer", nullable = false)
    var lecturer: String = "",

    @Column(name = "location", nullable = false)
    var location: String = "",

    @Column(name = "max_seats", nullable = false)
    var maxSeats: Int = 0,

    @Column(name = "lecture_time", nullable = false)
    var lectureTime: Instant = Instant.now(),

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Lob
    @Column(name = "description")
    var description: String? = null
) {
    /**
     * 기본 생성자
     * 모든 필드를 기본값으로 초기화합니다.
     */
    constructor() : this(
        id = null,
        lecturer = "",
        location = "",
        maxSeats = 0,
        lectureTime = Instant.now(),
        createdAt = Instant.now(),
        updatedAt = Instant.now(),
        description = null
    )

    /**
     * 복사 메서드
     * 기존 강연 객체를 기반으로 새로운 강연 객체를 생성합니다.
     * @param id 강연 ID
     * @param lecturer 강연자
     * @param location 강연 장소
     * @param maxSeats 최대 좌석 수
     * @param lectureTime 강연 시간
     * @param createdAt 생성 시간
     * @param updatedAt 수정 시간
     * @param description 강연 설명
     * @return 새로운 강연 객체
     */
    fun copy(
        id: Long? = this.id,
        lecturer: String = this.lecturer,
        location: String = this.location,
        maxSeats: Int = this.maxSeats,
        lectureTime: Instant = this.lectureTime,
        createdAt: Instant = this.createdAt,
        updatedAt: Instant = this.updatedAt,
        description: String? = this.description
    ): Lecture {
        return Lecture(id, lecturer, location, maxSeats, lectureTime, createdAt, updatedAt, description)
    }
}