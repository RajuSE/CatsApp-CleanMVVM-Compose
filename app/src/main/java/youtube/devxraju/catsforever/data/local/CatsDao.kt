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
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem


@Dao
interface CatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cat: CatBreedsResponseItem)

    @Delete
    suspend fun delete(cat: CatBreedsResponseItem)

    @Query("UPDATE CatBreedsResponseItem SET isFavourited=0 WHERE id=:id")
    suspend fun unFavouriteCat(id: String): Int

    @Query("SELECT * FROM CatBreedsResponseItem WHERE isFavourited=1")
    fun getCats(): Flow<List<CatBreedsResponseItem>>

    @Query("UPDATE CatBreedsResponseItem SET isAddedToCart=1 WHERE id=:id")
    suspend fun markAddToCart(id: String): Int

    @Query("UPDATE CatBreedsResponseItem SET isAddedToCart=0 WHERE id=:id")
    suspend fun removeAddToCart(id: String): Int

    @Query("UPDATE CatBreedsResponseItem SET quantity=:qty WHERE id=:id")
    suspend fun updateQty(id: String, qty:Int): Int
    @Query("SELECT * FROM CatBreedsResponseItem WHERE isAddedToCart=1")
    fun getCartCats(): Flow<List<CatBreedsResponseItem>>

    @Query("SELECT * FROM CatBreedsResponseItem WHERE id=:id")
    suspend fun getCat(id: String): CatBreedsResponseItem?

    @Query("SELECT * FROM CatBreedsResponseItem WHERE id=:id and isFavourited=1")
    suspend fun getFavoritedCat(id: String): CatBreedsResponseItem?

}

