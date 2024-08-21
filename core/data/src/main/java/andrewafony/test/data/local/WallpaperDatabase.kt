package andrewafony.test.data.local

import andrewafony.test.data.local.entities.WallpaperEntity
import androidx.room.RoomDatabase

@androidx.room.Database(
    entities = [WallpaperEntity::class],
    version = 1
)
abstract class WallpaperDatabase: RoomDatabase() {

    abstract fun wallpaperDao(): WallpaperDao
}