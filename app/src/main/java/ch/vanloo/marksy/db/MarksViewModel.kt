package ch.vanloo.marksy

import androidx.lifecycle.*
import ch.vanloo.marksy.db.MarksRepository
import ch.vanloo.marksy.entity.Mark
import ch.vanloo.marksy.entity.Subject
import ch.vanloo.marksy.entity.SubjectAndMarks
import kotlinx.coroutines.launch

class MarksViewModel(private val repository: MarksRepository) : ViewModel() {
    val allMarks: LiveData<List<Mark>> = repository.allMarks.asLiveData()

    fun insert(vararg marks: Mark) = viewModelScope.launch {
        repository.insert(*marks)
    }
}

class SubjectsViewModel(private val repository: MarksRepository) : ViewModel() {
    val allSubjects: LiveData<List<SubjectAndMarks>> = repository.allSubjects.asLiveData()

    fun insert(vararg subjects: Subject) = viewModelScope.launch {
        repository.insert(*subjects)
    }
}

class MarksViewModelFactory(private val repository: MarksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarksViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(SubjectsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubjectsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
