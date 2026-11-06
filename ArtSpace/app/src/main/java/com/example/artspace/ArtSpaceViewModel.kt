package com.example.artspace

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ArtSpaceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ArtSpaceUiState())
    val uiState: StateFlow<ArtSpaceUiState> = _uiState.asStateFlow()

    fun nextArtwork() {
        if (_uiState.value.currentArtworkIndex < 3) {
            _uiState.update { currentState ->
                currentState.copy(currentArtworkIndex = currentState.currentArtworkIndex + 1)
            }
        }
    }

    fun previousArtwork() {
        if (_uiState.value.currentArtworkIndex > 1) {
            _uiState.update { currentState ->
                currentState.copy(currentArtworkIndex = currentState.currentArtworkIndex - 1)
            }
        }
    }
}