package youtube.devxraju.catsforever.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import youtube.devxraju.catsforever.data.local.CatsDao
import youtube.devxraju.catsforever.data.remote.mapper.toModel
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem

class CatsPagingSource(
    private val catsApi: CatsApi,
    private val catsDao: CatsDao
) : PagingSource<Int, CatBreedsResponseItem>() {


    override fun getRefreshKey(state: PagingState<Int, CatBreedsResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private var totalDataCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatBreedsResponseItem> {
        val page = params.key ?: 2
        return try {
            val catsResponse = catsApi.getCatsApi(limit = 10, page = page).map {
                it.toModel()
            }
            totalDataCount += catsResponse.size
            val cats = catsResponse.distinctBy { it.id } //Remove duplicates
            val catsMutable=cats.toMutableList()
            for((i,v) in catsMutable.withIndex()){
                catsMutable[i].price = (i+1)*100+(i+1)*(i+1)
            }
            LoadResult.Page(
                data = catsMutable,
                nextKey = if (totalDataCount == 10) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}