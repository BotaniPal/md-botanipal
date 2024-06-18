package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("data")
	val data: ScanData? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ScanData(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("predictionId")
	val predictionId: String? = null
)
