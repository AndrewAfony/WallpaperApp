package andrewafony.test.wallpaperapp

import andrewafony.test.data.di.dataModule
import andrewafony.test.wallpaper_detail.wallpaperDetailModule
import andrewafony.test.wallpaper_saved.savedWallpapersModule
import andrewafony.test.wallpaper_search.searchWallpapersModule
import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule, dataModule, searchWallpapersModule, savedWallpapersModule,
                wallpaperDetailModule
            )
        }

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            );
        }
    }
}

/**
 *
 * todo 1) Добавить Pager
 *      2) Детальный просмотр обоев
 *      3) Сохранение обоев локально
 *      4) Информация об обое
 *      5) Фильтр для поиска
 *      * 6) История поиска
 *      7) Тесты
 *      8) Benchmarks
 *
 */