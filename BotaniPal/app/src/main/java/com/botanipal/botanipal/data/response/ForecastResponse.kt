package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("predicted_price")
	val predictedPrice: Int? = null
)
