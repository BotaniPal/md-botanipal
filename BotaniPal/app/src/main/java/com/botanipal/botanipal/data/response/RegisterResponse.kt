package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("message")
	val message: String
)
