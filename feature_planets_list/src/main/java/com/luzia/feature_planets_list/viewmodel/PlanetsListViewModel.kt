package com.luzia.feature_planets_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.luzia.core_domain.model.Failed
import com.luzia.core_domain.model.LoadableData
import com.luzia.core_domain.model.Loaded
import com.luzia.core_domain.model.Loading
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.model.PlanetSummary
import com.luzia.core_domain.repository.PlanetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlanetsListViewModel(
    private val repository: PlanetsRepository
) : ViewModel() {

    val planetsPagingFlow: Flow<PagingData<PlanetSummary>> =
        repository.getPlanetsPaged().cachedIn(viewModelScope)

    private val _planetDetailsState =
        MutableStateFlow<Map<String, LoadableData<PlanetProperties>>>(emptyMap())
    val planetDetailsState: StateFlow<Map<String, LoadableData<PlanetProperties>>> =
        _planetDetailsState

    fun planetDetailsRequested(uid: String) {
        val currentState = _planetDetailsState.value[uid]
        if (currentState is Loaded || currentState is Loading) return

        _planetDetailsState.update { it + (uid to Loading) }

        viewModelScope.launch {
            try {
                val detail = repository.getPlanetDetail(uid)
                _planetDetailsState.update { it + (uid to Loaded(detail)) }
            } catch (e: Exception) {
                _planetDetailsState.update {
                    it + (uid to Failed(e, title = "Failed to load details for planet $uid"))
                }
            }
        }
    }

}