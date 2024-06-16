package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class Response(

	@SerializedName("predictionId")
	val predictionId: String,

	@SerializedName("prediction")
	val prediction: String,

	@SerializedName("imageUrl")
	val imageUrl: String
)
