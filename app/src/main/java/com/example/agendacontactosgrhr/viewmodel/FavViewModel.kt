package com.example.agendacontactosgrhr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import com.example.agendacontactosgrhr.data.repository.ContactoRepository
import com.example.agendacontactosgrhr.data.repository.CropRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    private val contactoRepository: ContactoRepository,
    private val cropRepository: CropRepository
) : ViewModel() {

    val favoriteNpcs: StateFlow<List<ContactoEntity>> = contactoRepository.obtenerFavoritos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteCrops: StateFlow<List<CropEntity>> = cropRepository.obtenerFavoritos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allFavorites: StateFlow<Pair<List<ContactoEntity>, List<CropEntity>>> = combine(
        favoriteNpcs,
        favoriteCrops
    ) { npcs, crops ->
        npcs to crops
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Pair(emptyList(), emptyList()))

    fun toggleFavoriteNpc(id: Int) {
        viewModelScope.launch {
            val contacto = contactoRepository.obtenerContactoPorId(id)
            if (contacto != null) {
                contactoRepository.actualizarFavorito(id, !contacto.isFavorite)
            }
        }
    }

    fun toggleFavoriteCrop(id: Int) {
        viewModelScope.launch {
            val crop = cropRepository.obtenerCultivoPorId(id)
            if (crop != null) {
                cropRepository.actualizarFavorito(id, !crop.isFavorite)
            }
        }
    }
}
