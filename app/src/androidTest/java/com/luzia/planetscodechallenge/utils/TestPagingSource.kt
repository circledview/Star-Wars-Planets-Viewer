package com.luzia.planetscodechallenge.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luzia.core_domain.model.PlanetSummary

/**
 * A [PagingSource] implementation for testing purposes.
 *
 * This class allows providing a static list of items to be loaded, simulating the behavior
 * of a real PagingSource without relying on network or database operations.
 *
 * @param items The list of [PlanetSummary] objects to be used as the data source.
 */
class TestPagingSource(
    private val items: List<PlanetSummary>
) : PagingSource<Int, PlanetSummary>() {
    override fun getRefreshKey(state: PagingState<Int, PlanetSummary>) = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlanetSummary> {
        return LoadResult.Page(
            data = items,
            prevKey = null,
            nextKey = null
        )
    }
}