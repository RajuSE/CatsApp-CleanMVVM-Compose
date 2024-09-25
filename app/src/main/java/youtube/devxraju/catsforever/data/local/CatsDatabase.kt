package youtube.devxraju.catsforever.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import youtube.devxraju.catsforever.data.remote.dto.CartItem
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem

@Database(entities = [CatBreedsResponseItem::class, CartItem::class],version = 1,)
abstract class CatsDatabase : RoomDatabase() {

    abstract val catsDao: CatsDao

}