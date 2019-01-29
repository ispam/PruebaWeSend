package destinum.tech.pruebawesend

import destinum.tech.pruebawesend.Data.Local.Entities.*
import destinum.tech.pruebawesend.Data.Local.ViewModels.ListDataViewModel
import destinum.tech.pruebawesend.Data.Local.ViewModels.LogViewModel
import destinum.tech.pruebawesend.Data.Remote.LocalBitcoinsAPI
import io.mockk.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.TestObserver

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ExampleUnitTest {

    @Nested
    inner class HomeActivityTests {

        val logVM = mockk<LogViewModel>()
        val provideAPI = mockk<LocalBitcoinsAPI>()
        val listDataVM = mockk<ListDataViewModel>()

        @BeforeEach
        fun init() {
            clearMocks(logVM, provideAPI, listDataVM)
        }

        @Test
        fun `getAllLogs() method is not empty`() {

            val singleList = arrayListOf(
                Log("28/01/2019", "100USD", "1000VES", "51", "49"),
                Log("28/01/2019", "200USD", "2000VES", "49", "51")
                )

            every { logVM.getAllLogs() } answers { Single.just(singleList)}

            logVM.getAllLogs()
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(singleList)
//                .assertValue{ it[0].date == "28/01/2019" }
//                .assertValue{ it[0].rate_usd == "100USD" }
//                .assertValue{ it[0].rate_ves == "1000VES" }
//                .assertValue{ it[0].above == "51" }
//                .assertValue{ it[0].below == "49" }
                .dispose()


        }

        @Test
        fun `getAllLogs() method throws error`() {

            val response = Throwable("Generic error from Rxjava")

            every { logVM.getAllLogs() } answers { Single.error(response)}

            logVM.getAllLogs()
                .test()
                .assertError(response)
                .dispose()
        }

        @Test
        fun `getCurrentLogsCount() method is not empty`() {

            val randomNum = Random.nextInt(10)
            every { logVM.getCurrentLogsCount() } returns Single.just(randomNum)

            logVM.getCurrentLogsCount()
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .assertResult(randomNum)
                .dispose()
        }

        @Test
        fun `getResults() method == combine multiple lists and insert data in DB`() {

            // Dummy List 1
            val profile1 = Profile("test1", 87, "100", "01/28/2019", "testName1")
            val listData1 = AdList(ListData(profile1, 1001, "1001VES", "101USD", 1))
            val listData2 = AdList(ListData(profile1, 1002, "1002VES", "102USD", 2))
            val listData3 = AdList(ListData(profile1, 1003, "1003VES", "103USD", 3))
            val adList1 = listOf(listData1, listData2, listData3)
            val list1 = DataResult(Data(adList1, Actions("public view1")))

            // Dummy List 2
            val profile2 = Profile("test2", 87, "100", "01/28/2019", "testName2")
            val listData4 = AdList(ListData(profile2, 1001, "1001VES", "101USD", 1))
            val listData5 = AdList(ListData(profile2, 1002, "1002VES", "102USD", 2))
            val listData6 = AdList(ListData(profile2, 1003, "1003VES", "103USD", 3))
            val adList = listOf(listData4, listData5, listData6)
            val list2 = DataResult(Data(adList, Actions("public view2")))


            val combinedList = listOf(listData1, listData2, listData3, listData4, listData5, listData6)

            every { provideAPI.getList(1) } answers { Observable.just(list1) }
            every { provideAPI.getList(2) } answers { Observable.just(list2) }

            Observable.zip(provideAPI.getList(1), provideAPI.getList(2),
                BiFunction { data1: DataResult, data2: DataResult ->
                    val list = arrayListOf<DataResult>()
                    list.add(data1)
                    list.add(data2)
                    return@BiFunction list
                }).flatMapCompletable {
                    val adapterList = mutableListOf<ListData>()

                    for (dataResult in it) {
                        for (adList in dataResult.data.ad_list) {
                            adapterList.add(
                                ListData(
                                    adList.listData.profile,
                                    adList.listData.ad_id,
                                    adList.listData.temp_price,
                                    adList.listData.temp_price_usd,
                                    1
                                )
                            )
                        }
                    }
                    every { listDataVM.insertListData(adapterList) } answers { Completable.fromObservable(Observable.just(combinedList)).doOnComplete { println(combinedList) }}
                    listDataVM.insertListData(adapterList)
                }.test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .dispose()


        }
    }

    @Nested inner class RxJavaTests {

        @Test
        fun `Using Test Observer`() {

            val observable = Observable.interval(1, TimeUnit.SECONDS).take(5)
            val testObserver = TestObserver<Long>()

            testObserver.assertNotSubscribed()
            observable.subscribe(testObserver)
            testObserver.assertSubscribed()
            testObserver.awaitTerminalEvent()
            testObserver.assertComplete()
            testObserver.assertNoErrors()
            testObserver.assertValueCount(5)
            testObserver.assertValues(0L, 1L, 2L, 3L, 4L)
            testObserver.dispose()

//            observable
//                .test()
//                .awaitDone(6, TimeUnit.SECONDS)
//                .assertSubscribed()
//                .assertComplete()
//                .assertNoErrors()
//                .assertValueCount(5)
//                .assertValues(0L, 1L, 2L, 3L, 4L)
        }
    }
}
