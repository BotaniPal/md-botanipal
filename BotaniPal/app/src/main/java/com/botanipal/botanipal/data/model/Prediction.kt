package com.botanipal.botanipal.data.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class Prediction(
    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("prediction")
    val prediction: String,

    @field:SerializedName("predictionId")
    val predictionId: String
)
