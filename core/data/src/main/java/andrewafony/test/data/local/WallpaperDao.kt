package andrewafony.test.data.local

import andrewafony.test.data.local.entities.WallpaperEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {

    @Query("SELECT * FROM wallpaperentity")
    fun wallpapers() : Flow<List<WallpaperEntity>>

    @Query("SELECT * FROM wallpaperentity WHERE id=:id")
    suspend fun getWallpaperById(id: String) : WallpaperEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWallpaper(wallpaper: WallpaperEntity)

    @Delete
    suspend fun deleteWallpaper(wallpaper: WallpaperEntity)

    @Query("SELECT EXISTS(SELECT * FROM wallpaperentity WHERE id = :id)")
    suspend fun exists(id: String) : Boolean
}