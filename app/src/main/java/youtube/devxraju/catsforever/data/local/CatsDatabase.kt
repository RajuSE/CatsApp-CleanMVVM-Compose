package youtube.devxraju.catsforever.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem

@Database(entities = [CatBreedsResponseItem::class],version = 2,)
abstract class CatsDatabase : RoomDatabase() {

    abstract val catsDao: CatsDao

}