package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("predictionResult")
	val predictionResult: String? = null,

	@field:SerializedName("predictionType")
	val predictionType: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: Timestamp? = null
)

data class Timestamp(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)
