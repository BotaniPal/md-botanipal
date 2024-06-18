package com.botanipal.botanipal.ui.price

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.botanipal.botanipal.data.model.Commodity

class PriceViewModel : ViewModel() {
    private val _commodity = MutableLiveData<List<Commodity>>()
    val commodity: LiveData<List<Commodity>> = _commodity

    private val _isLoadingCommodity = MutableLiveData<Boolean>()
    val isLoadingCommodity: LiveData<Boolean> = _isLoadingCommodity

    fun getCommodity() {

    }
}