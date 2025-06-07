package com.luzia.core_data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luzia.core_data.api.SwApi
import com.luzia.core_data.dto.toPlanetSummary
import com.luzia.core_domain.model.PlanetSummary
import com.luzia.core_domain.utils.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext

internal class PlanetsPagingSource(
    private val swApi: SwApi,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val pageSize: Int = 10,
) : PagingSource<Int, PlanetSummary>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlanetSummary> {
        return withContext(coroutineDispatcherProvider.io) {
            val page = params.key ?: 1
            try {
                val response = swApi.getPlanets(page = page, limit = pageSize)
                val results = response.results.map { it.toPlanetSummary() }

                val nextPage = if (response.next != null) page + 1 else null
                val prevPage = if (page > 1) page - 1 else null

                LoadResult.Page(
                    data = results,
                    prevKey = prevPage,
                    nextKey = nextPage
                )
            } catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }

    }

    override fun getRefreshKey(state: PagingState<Int, PlanetSummary>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}