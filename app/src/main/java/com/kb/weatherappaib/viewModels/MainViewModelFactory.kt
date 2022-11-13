import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kb.weatherappaib.repository.MainRepository
import com.kb.weatherappaib.viewModels.MainViewModel

class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}