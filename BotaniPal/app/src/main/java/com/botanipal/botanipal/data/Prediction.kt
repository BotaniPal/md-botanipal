package com.botanipal.botanipal.data

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class Prediction(
    @SerializedName("photo")
    var photo: String?,
    @SerializedName("result")
    var result: String?,
)
