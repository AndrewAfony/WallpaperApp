package andrewafony.test.wallpaperapp.data.local

import andrewafony.test.wallpaperapp.data.local.entities.WallpaperEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WallpaperEntity::class],
    version = 1
)
abstract class WallpaperDatabase: RoomDatabase() {

    abstract fun wallpaperDao(): WallpaperDao
}