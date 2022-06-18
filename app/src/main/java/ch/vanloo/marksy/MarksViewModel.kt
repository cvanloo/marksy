package ch.vanloo.marksy

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MarksViewModel(private val repository: MarksRepository) : ViewModel() {
    val allMarks: LiveData<List<Mark>> = repository.allMarks.asLiveData()

    fun insert(vararg marks: Mark) = viewModelScope.launch {
        repository.insert(*marks)
    }
}

class MarksViewModelFactory(private val repository: MarksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}