package com.luzia.planetscodechallenge.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luzia.core_domain.model.PlanetSummary

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