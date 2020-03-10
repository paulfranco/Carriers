package co.paulfran.carriers.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.paulfran.carriers.model.Carrier
import co.paulfran.carriers.model.CarriersService
import kotlinx.coroutines.*

class ListViewModel: ViewModel() {

    val carriersService = CarriersService.getCarriersService()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    val carriers = MutableLiveData<List<Carrier>>()
    val carrierLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCarriers()
    }

    private fun fetchCarriers() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = carriersService.getCarriers()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    carriers.value = response.body()
                    carrierLoadError.value = null
                    loading.value = false

                } else {
                    onError("Error: ${response.message()}")

                }
            }
        }
    }

    private fun onError(message: String) {
        carrierLoadError.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        // cancel job
        job?.cancel()
    }
}