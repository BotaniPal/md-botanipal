package com.botanipal.botanipal.data.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class Prediction(

    @SerializedName("predictionId")
    val predictionId: String,

    @SerializedName("prediction")
    val prediction: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("predictionType")
    val predictionType: String
)
