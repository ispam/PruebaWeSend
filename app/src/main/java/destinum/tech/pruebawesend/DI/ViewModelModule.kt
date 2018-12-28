package destinum.tech.pruebawesend.DI

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import destinum.tech.pruebawesend.Data.Local.TestDB
import destinum.tech.pruebawesend.Data.Local.ViewModels.ListDataViewModel
import destinum.tech.pruebawesend.Data.Local.ViewModels.LogViewModel
import javax.inject.Singleton

@Module
class ViewModelModule {

    companion object {
        const val DB_NAME = "test.db"
    }

    @Singleton @Provides
    fun provideDB(context: Context): TestDB =
            Room.databaseBuilder(
                context.applicationContext,
                TestDB::class.java,
                DB_NAME
                ).build()

    @Singleton @Provides
    fun provideLogsVM(db: TestDB) = LogViewModel(db)

    @Singleton @Provides
    fun provideListDataVM(db: TestDB) = ListDataViewModel(db)

}