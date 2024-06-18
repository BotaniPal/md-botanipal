package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @SerializedName("message")
    val message: String? = null
)
