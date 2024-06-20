package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@SerializedName("data")
	val data: ScanData = ScanData(),

	@SerializedName("error")
	val error: Any? = null,

	@SerializedName("message")
	val message: String? = null
)

data class ScanData(

	@SerializedName("imageUrl")
	val imageUrl: String? = null,

	@SerializedName("prediction")
	val prediction: String = "",

	@SerializedName("predictionId")
	val predictionId: String? = null
)
