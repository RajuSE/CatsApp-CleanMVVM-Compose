package youtube.devxraju.catsforever.data.local

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem


@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cat: CatBreedsResponseItem)

    @Delete
    suspend fun delete(cat: CatBreedsResponseItem)

    @Query("SELECT * FROM CatBreedsResponseItem")
    fun getCats(): Flow<List<CatBreedsResponseItem>>


    @Query("SELECT * FROM CatBreedsResponseItem WHERE isAddedToCart=1")
    fun getCartCats(): Flow<List<CatBreedsResponseItem>>

    @Query("SELECT * FROM CatBreedsResponseItem WHERE id=:id")
    suspend fun getCat(id: String): CatBreedsResponseItem?

}

