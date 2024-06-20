package com.botanipal.botanipal.ui.scan

import androidx.lifecycle.MutableLiveData
import com.botanipal.botanipal.data.api.UserRepository
import com.botanipal.botanipal.data.model.Prediction

class ResultViewModal(private val repository: UserRepository) {
    private val predictionData = MutableLiveData<Prediction>()
}