package com.luzia.feature_planet_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luzia.core_domain.model.Failed
import com.luzia.core_domain.model.LoadableData
import com.luzia.core_domain.model.Loaded
import com.luzia.core_domain.model.Loading
import com.luzia.core_domain.model.NotLoaded
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.repository.PlanetsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanetDetailViewModel(
    private val repository: PlanetsRepository,
    private val uid: String
) : ViewModel() {

    private val _state = MutableStateFlow<LoadableData<PlanetProperties>>(NotLoaded)
    val state: StateFlow<LoadableData<PlanetProperties>> = _state

    init {
        fetchPlanetDetails()
    }

    private fun fetchPlanetDetails() {
        _state.value = Loading
        viewModelScope.launch {
            try {
                val detail = repository.getPlanetDetail(uid)
                _state.value = Loaded(detail)
            } catch (e: Exception) {
                _state.value = Failed(
                    throwble = e,
                )
            }
        }
    }

    fun retry() {
        fetchPlanetDetails()
    }

}