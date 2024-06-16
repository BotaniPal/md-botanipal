package com.botanipal.botanipal.data.api

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("prediction")
	val prediction: String
)
