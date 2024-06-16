package com.botanipal.botanipal.data.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(

	@field:SerializedName("uid_user")
	val uidUser: String,

	@field:SerializedName("uid_expert")
	val uidExpert: String,

	@field:SerializedName("paymentNominal")
	val paymentNominal: Int,

	@field:SerializedName("paymentMethod")
	val paymentMethod: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("transactionProgress")
	val transactionProgress: String,

	@field:SerializedName("timestamp")
	val timestamp: String
)
