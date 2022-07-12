package ch.vanloo.marksy.db

import androidx.lifecycle.*
import ch.vanloo.marksy.entity.*
import kotlinx.coroutines.launch

class MarksViewModel(private val repository: MarksRepository) : ViewModel() {
    val allMarks: LiveData<List<Mark>> = repository.allMarks.asLiveData()
    val allMarksWithSubject: LiveData<List<MarkWithSubject>> =
        repository.allMarksWithSubject.asLiveData()

    fun insert(vararg marks: Mark) = viewModelScope.launch {
        repository.insert(*marks)
    }
}

class SubjectsViewModel(private val repository: MarksRepository) : ViewModel() {
    val allSubjects: LiveData<List<Subject>> = repository.allSubjects.asLiveData()
    val allSubjectsWithMarks: LiveData<List<SubjectWithMarks>> =
        repository.allSubjectsWithMarks.asLiveData()
    val allSubjectsWithMarksFromCurrentSemester: LiveData<List<SubjectWithMarks>> =
        repository.allSubjectsWithMarksFromCurrentSemester.asLiveData()

    fun insert(vararg subjects: Subject) = viewModelScope.launch {
        repository.insert(*subjects)
    }
}

class SemestersViewModel(private val repository: MarksRepository) : ViewModel() {
    val allSemesters: LiveData<List<Semester>> = repository.allSemesters.asLiveData()
    val allSemestersWithSubjects: LiveData<List<SemesterWithSubjects>> =
        repository.allSemestersWithSubjects.asLiveData()

    fun insert(vararg semesters: Semester) = viewModelScope.launch {
        repository.insert(*semesters)
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
        if (modelClass.isAssignableFrom(SemestersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SemestersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
