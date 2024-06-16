package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("profileImage")
	val profileImage: String? = null,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
