package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(

	@field:SerializedName("bookmarkId")
	val bookmarkId: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("AddBookmarkResponse")
	val addBookmarkResponse: List<BookmarkResponseItem>
)

data class BookmarkResponseItem(

	@field:SerializedName("predictionResult")
	val predictionResult: String,

	@field:SerializedName("predictionType")
	val predictionType: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("timestamp")
	val timestamp: Timestamp
)

data class Timestamp(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)



