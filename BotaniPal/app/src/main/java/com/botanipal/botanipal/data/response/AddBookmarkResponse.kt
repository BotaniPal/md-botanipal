package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class AddBookmarkResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("bookmarkId")
	val bookmarkId: String

)
