package com.botanipal.botanipal.ui.price

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botanipal.botanipal.R
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.model.Commodity
import com.botanipal.botanipal.data.response.DataPrice
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class PriceViewModel (private val repository: UserRepository, private val resources: Resources) : ViewModel() {

    private val _listCommodity = MutableLiveData<List<Commodity>>()
    val listCommodity: LiveData<List<Commodity>> = _listCommodity

    private val _bawangPrice = MutableLiveData<Int?>()
    val bawangPrice: LiveData<Int?> = _bawangPrice

    private val _cabePrice = MutableLiveData<Int?>()
    val cabePrice: LiveData<Int?> = _cabePrice

    private val _jagungPrice = MutableLiveData<Int?>()
    val jagungPrice: LiveData<Int?> = _jagungPrice

    private val _kacangPrice = MutableLiveData<Int?>()
    val kacangPrice: LiveData<Int?> = _kacangPrice

    private val _kedelaiPrice = MutableLiveData<Int?>()
    val kedelaiPrice: LiveData<Int?> = _kedelaiPrice

    private val _kentangPrice = MutableLiveData<Int?>()
    val kentangPrice: LiveData<Int?> = _kentangPrice

    private val _kolPrice = MutableLiveData<Int?>()
    val kolPrice: LiveData<Int?> = _kolPrice

    private val _tomatPrice = MutableLiveData<Int?>()
    val tomatPrice: LiveData<Int?> = _tomatPrice

    private val _isLoadingCommodity = MutableLiveData<Boolean>()
    val isLoadingCommodity: LiveData<Boolean> = _isLoadingCommodity

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful


    private fun getFutureDate(): String {
        val future_date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = future_date.format(formatter)
        return formatted
    }
    fun getBawang() {
        viewModelScope.launch {
            repository.getBawangForecast(getFutureDate())
            repository.priceBawang.observeForever {
                Log.d("PriceViewModel", "getBawang: $it")
                _bawangPrice.value = it.predictedPrice
            }
            Log.d("PriceViewModel", "getBawang: ${bawangPrice.value}")
//            Log.d("PriceViewModel", "getBawang: ${repository.priceBawang.value}")
        }
    }

    fun getCabe() {
        repository.getCabeForecast(getFutureDate())
        repository.priceCabe.observeForever {
            _cabePrice.value = it.predictedPrice
        }
        Log.d("PriceViewModel", "getCabe: ${cabePrice.value}")
    }

    fun getJagung() {
        repository.getJagungForecast(getFutureDate())
        repository.priceJagung.observeForever {
            _jagungPrice.value = it.predictedPrice
        }
        Log.d("PriceViewModel", "getJagung: ${jagungPrice.value}")
    }

    fun getKacang() {
        repository.getKacangForecast(getFutureDate())
        repository.priceKacang.observeForever {
            _kacangPrice.value = it.predictedPrice
        }
        Log.d("PriceViewModel", "getKacang: ${kacangPrice.value}")
    }

    fun getKedelai() {
        repository.getKedelaiForecast(getFutureDate())
        repository.priceKedelai.observeForever {
            _kedelaiPrice.value = it.predictedPrice
        }
        Log.d("PriceViewModel", "getKedelai: ${kedelaiPrice.value}")
    }

    fun getKentang() {
        repository.getKentangForecast(getFutureDate())
        repository.priceKentang.observeForever {
            _kentangPrice.value = it.predictedPrice
        }
        Log.d("PriceViewModel", "getKentang: ${kentangPrice.value}")
    }

    fun getKol() {
        repository.getKolForecast(getFutureDate())
        repository.priceKol.observeForever {
            _kolPrice.value = it.predictedPrice
        }
        Log.d("PriceViewModel", "getKol: ${kolPrice.value}")
        }

    fun getTomat() {
        repository.getTomatForecast(getFutureDate())
        repository.priceTomat.observeForever {
            _tomatPrice.value = it.predictedPrice
        }
        Log.d("PriceViewModel", "getTomat: ${tomatPrice.value}")
    }


    fun getListPrice() {
        _isLoadingCommodity.value = true
        getBawang()
        getCabe()
        getJagung()
        getKacang()
        getKedelai()
        getKentang()
        getKol()
        getTomat()
//        val future_date = getFutureDate()
//        Log.d("PriceViewModel", "getListCommodity: $future_date")

//        _listCommodity.value = listOf(bawangPrice.value, cabePrice.value, jagungPrice.value, kacangPrice.value, kedelaiPrice.value, kentangPrice.value, kolPrice.value, tomatPrice.value)
        _listCommodity.value = listOf(
            Commodity(R.drawable.bawang, "Bawang Merah", bawangPrice.value),
            Commodity(R.drawable.lombok, "Cabe Rawit merah",cabePrice.value),
            Commodity(R.drawable.jagungt,"Jagung" ,jagungPrice.value),
            Commodity(R.drawable.kacang_tanah, "Kacang Tanah",kacangPrice.value),
            Commodity(R.drawable.kedelai, "Kedelai",kedelaiPrice.value),
            Commodity(R.drawable.kentang, "Kentang",kentangPrice.value),
            Commodity(R.drawable.kol, "Kol",kolPrice.value),
            Commodity(R.drawable.tomat, "Tomat", tomatPrice.value)
        )
        _isLoadingCommodity.value = false
        Log.d("PriceViewModel", "getListCommodity: ${listCommodity.value}")
    }


}