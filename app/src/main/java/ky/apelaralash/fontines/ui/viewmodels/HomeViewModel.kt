package ky.apelaralash.fontines.ui.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ky.apelaralash.fontines.ui.provider.FileProvider
import javax.inject.Inject

/**
 * ViewModel для главного экрана
 * Управляет выбором изображения
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fileProvider: FileProvider,
) : ViewModel() {

    fun createTempImageUri(context: Context): Uri {
        return fileProvider.createTempImageUri(context)
    }
}