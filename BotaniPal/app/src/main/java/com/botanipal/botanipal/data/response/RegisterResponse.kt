package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: UidData? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UidData(

	@field:SerializedName("uid")
	val uid: String? = null
)
