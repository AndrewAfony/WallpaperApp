package andrewafony.test.data.local

import andrewafony.test.data.local.entities.WallpaperEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WallpaperEntity::class],
    version = 1
)
internal abstract class WallpaperDatabase: RoomDatabase() {

    abstract fun wallpaperDao(): WallpaperDao
}