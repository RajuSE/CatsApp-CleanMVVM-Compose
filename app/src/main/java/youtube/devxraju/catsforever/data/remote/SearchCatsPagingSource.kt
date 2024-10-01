package youtube.devxraju.catsforever.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import youtube.devxraju.catsforever.data.remote.mapper.toModel
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem
import java.lang.Exception

class SearchCatsPagingSource(
    private val api: CatsApi,
    private val searchQuery: String,
) : PagingSource<Int, CatBreedsResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, CatBreedsResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPage ->
            val page = state.closestPageToPosition(anchorPage)
            page?.nextKey?.minus(1) ?: page?.prevKey?.plus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatBreedsResponseItem> {
        val page = params.key ?: 1
        return try {
            println("searchCats called q:$searchQuery")
            val catsResponse = api.searchCats(searchQuery = searchQuery).map {
                it.toModel()
            }

            val cats = catsResponse.distinctBy { it.id } //Remove duplicates
            val catsMutable=cats.toMutableList()
            for((i,v) in catsMutable.withIndex()){
                catsMutable[i].price = (i+1)*100+(i+1)*(i+1)
            }

            LoadResult.Page(
                data = catsMutable,
                nextKey = null,//if (totalCount == 10) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            println("searchCats error e:$e")
            LoadResult.Error(throwable = e)
        }
    }


}