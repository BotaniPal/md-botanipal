package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("token")
	val token: String
)

data class User(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
