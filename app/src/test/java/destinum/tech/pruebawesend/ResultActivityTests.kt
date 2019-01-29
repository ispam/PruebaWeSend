package destinum.tech.pruebawesend

import destinum.tech.pruebawesend.Data.Local.Entities.ListData
import destinum.tech.pruebawesend.Data.Local.Entities.Log
import destinum.tech.pruebawesend.Data.Local.Entities.Profile
import destinum.tech.pruebawesend.Data.Local.ViewModels.ListDataViewModel
import destinum.tech.pruebawesend.Data.Local.ViewModels.LogViewModel
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random

class ResultActivityTests {

    val listDataVM = mockk<ListDataViewModel>()
    val logVM = mockk<LogViewModel>()

    @BeforeEach
    fun clear() {
        clearMocks(listDataVM)
    }

    @Test
    fun `getListDataBasedOnLogID() method is not empty`() {

        val randomId = Random.nextInt(10)

        val profile1 = Profile("test1", 87, "100", "01/28/2019", "testName1")
        val listData1 = ListData(profile1, 1001, "1001VES", "101USD", 1)
        val listData2 = ListData(profile1, 1002, "1002VES", "102USD", 2)
        val listData3 = ListData(profile1, 1003, "1003VES", "103USD", 3)
        val listData4 = ListData(profile1, 1004, "1004VES", "101USD", 4)
        val listData5 = ListData(profile1, 1005, "1005VES", "102USD", 5)
        val listData6 = ListData(profile1, 1006, "1006VES", "103USD", 6)
        val listData = listOf(listData1, listData2, listData3, listData4, listData5, listData6)

        every { listDataVM.getListDataBasedOnLogID(randomId.toLong()) } answers { Single.just(listData) }

        listDataVM.getListDataBasedOnLogID(randomId.toLong())
            .test()
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
            .assertResult(listData)
            .dispose()

    }

    @Test
    fun `insertLog() creates log in DB`() {

        val log = Log("29/01/2019", "200USD", "2000VES", "76", "24")

        every { logVM.insertLog(log) } answers { Completable.fromObservable(Observable.just(log).doOnNext { println(it) })}

        logVM.insertLog(log)
            .test()
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
            .dispose()
    }
}