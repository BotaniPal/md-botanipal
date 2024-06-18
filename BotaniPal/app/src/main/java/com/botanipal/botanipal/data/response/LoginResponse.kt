package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

// buat login, forgot, dan reset password
data class LoginResponse(

	@field:SerializedName("data")
	val data: UserData? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class User(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class UserData(

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null
)
