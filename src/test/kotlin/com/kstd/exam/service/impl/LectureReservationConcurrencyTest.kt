import com.kstd.exam.domain.Lecture
import com.kstd.exam.domain.LectureReservation
import com.kstd.exam.repository.LectureRepository
import com.kstd.exam.repository.LectureReservationRepository
import com.kstd.exam.service.LectureReservationServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Instant
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@ExtendWith(MockitoExtension::class)
class LectureReservationConcurrencyTest {

    @Mock
    private lateinit var lectureRepository: LectureRepository

    @Mock
    private lateinit var lectureReservationRepository: LectureReservationRepository

    @InjectMocks
    private lateinit var lectureReservationService: LectureReservationServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        val now = Instant.now()
        val lecture = Lecture(
            id = 1,
            lecturer = "테스트 강연자",
            location = "테스트 장소",
            maxSeats = 100,
            lectureTime = now,
            createdAt = now,
            updatedAt = now,
            description = "테스트 설명"
        )

        // 강연 및 강연 예약에 대한 Mock 설정
        Mockito.`when`(lectureRepository.findById(1L)).thenReturn(Optional.of(lecture))
        Mockito.`when`(lectureReservationRepository.findByEmployeeIdAndLectureId(Mockito.anyString(), Mockito.anyLong())).thenReturn(null)
        Mockito.`when`(lectureReservationRepository.save(Mockito.any(LectureReservation::class.java))).thenAnswer { invocation ->
            val reservation = invocation.getArgument<LectureReservation>(0)
            reservation.id = 1L
            reservation
        }
    }

    /**
     * 동시 강연 예약 테스트
     */
    @Test
    fun `동시 강연 예약 테스트`() {
        println("동시 강연 예약 테스트 시작")

        val executor = Executors.newFixedThreadPool(10)
        val futures = mutableListOf<CompletableFuture<Void>>()

        for (i in 1..10) {
            val future = CompletableFuture.runAsync({
                println("강연 예약 시도 중: employee$i")
                lectureReservationService.reserveLecture("employee$i", 1L)
                println("강연 예약 완료: employee$i")
            }, executor)
            futures.add(future)
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        // 강연 조회 및 예약 시 Mock 호출 검증
        println("Mock 호출 검증 중")
        Mockito.verify(lectureRepository, Mockito.times(10)).findById(1L)
        println("강연 조회 검증 완료")
        Mockito.verify(lectureReservationRepository, Mockito.times(10)).findByEmployeeIdAndLectureId(Mockito.anyString(), Mockito.anyLong())
        println("강연 예약 조회 검증 완료")
        Mockito.verify(lectureReservationRepository, Mockito.times(10)).save(Mockito.any(LectureReservation::class.java))
        println("강연 예약 저장 검증 완료")

        println("동시 강연 예약 테스트 완료")
    }
}
