package com.example.unscramble.data

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        viewModel.updateUserGuess("wrong_word_123")
        viewModel.checkUserGuess()

        val updatedState = viewModel.uiState.value
        assertTrue(updatedState.isGuessedWordWrong)
        assertEquals(0, updatedState.score)
        assertEquals(1, updatedState.currentWordCount)
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        assertTrue(unScrambledWord != gameUiState.currentScrambledWord)
        assertEquals(1, gameUiState.currentWordCount)
        assertEquals(0, gameUiState.score)
        assertFalse(gameUiState.isGuessedWordWrong)
        assertFalse(gameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentState.currentScrambledWord)

        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentState = viewModel.uiState.value
            if (it < MAX_NO_OF_WORDS - 1) {
                correctPlayerWord = getUnscrambledWord(currentState.currentScrambledWord)
            }
        }

        assertTrue(currentState.isGameOver)
        assertEquals(expectedScore, currentState.score)
        assertEquals(MAX_NO_OF_WORDS, currentState.currentWordCount)
    }

    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        var currentState = viewModel.uiState.value
        val initialScore = currentState.score
        val initialWordCount = currentState.currentWordCount

        viewModel.skipWord()
        currentState = viewModel.uiState.value

        assertEquals(initialScore, currentState.score)
        assertEquals(initialWordCount + 1, currentState.currentWordCount)
    }
}