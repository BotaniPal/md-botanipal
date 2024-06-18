package com.botanipal.botanipal.data.api

import androidx.lifecycle.LiveData
import com.botanipal.botanipal.data.response.AddBookmarkResponse
import com.botanipal.botanipal.data.response.BookmarkResponse
import com.botanipal.botanipal.data.response.ForecastResponse
import com.botanipal.botanipal.data.response.LoginResponse
import com.botanipal.botanipal.data.response.ProfileResponse
import com.botanipal.botanipal.data.response.RegisterResponse
import com.botanipal.botanipal.data.response.ScanResponse
import com.botanipal.botanipal.data.response.TransactionResponse
import com.botanipal.botanipal.data.response.WeatherResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // register
    @POST("/auth/register-user")
    suspend fun registerUser(
        @Query("username") username: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("confirmPassword") confirmPassword: String
    ): RegisterResponse

    @POST("/auth/login")
    suspend fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): LoginResponse

    @POST("/auth/forgot-password")
    suspend fun forgotPassword(
        @Query("email") email: String
    ): LoginResponse

    @POST("/auth/reset-password")
    suspend fun resetPassword(
        @Query("username") username: String,
        @Query("otp") otp: String,
        @Query("newPassword") newPassword: String,
        @Query("newConfirmPassword") newConfirmPassword: String
    ): LoginResponse

    @GET("/profile/user")
    suspend fun getUserProfile(
//        @Header("Authorization") token: String
    ): ProfileResponse

    @Multipart
    @POST("/profile/user/update")
    suspend fun updateUserProfile(
//        @Header("Authorization") token: String,
        @Query("username") username: String,
        @Query("name") name: String,
        @Query("bio") bio: String,
        @Part profileImage: MultipartBody.Part
    ): ProfileResponse

    @POST("/transactions")
    suspend fun getTransactions(
//        @Header("Authorization") token: String,
        @Query("uid_expert")  uid_expert: String,
        @Query("uid_user") uid_user: String,
        @Query("paymentNominal") paymentNominal: Int,
        @Query("paymentMethod") paymentMethod: String,
        @Query("transactionProgress") transactionProgress: String,
        @Query("timestamp") timestamp: String
    ): TransactionResponse

    @GET("/transactions/users/{userid}/transactions")
    suspend fun getTransactionsByUser(
//        @Header("Authorization") token: String,
        @Path("userid") userid: String
    ): TransactionResponse

    @GET("/transactions/{transactionid}")
    suspend fun getTransactionById(
//        @Header("Authorization") token: String,
        @Path("transactionid") transactionid: String
    ): TransactionResponse

    @PUT("/transactions/{transactionId}")
    suspend fun updateTransaction(
//        @Header("Authorization") token: String,
        @Path("transactionId") transactionId: String,
        @Query("transactionProgress") transactionProgress: String
        ): TransactionResponse

    //    plant
    @Multipart
    @POST("/predict/plant")
    suspend fun uploadTypeImage(
        @Part file: MultipartBody.Part
    ): ScanResponse

    //    disease
    @Multipart
    @POST("/predict/disease")
    suspend fun uploadDiseaseImage(
        @Part file: MultipartBody.Part
    ): ScanResponse

    @POST("/predict/bookmark")
    suspend fun bookmarkPlant(
//        @Header("Authorization") token: String,
        @Query("predictionId") predictionId: String,
        @Query("prediction") prediction: String,
        @Query("imageUrl") imageUrl: String,
        @Query("predictionType") predictionType: String
    ): AddBookmarkResponse

    @POST("/forecast")
    suspend fun uploadForecast(
        @Query("future_date") future_date: String,
        @Query("commodity") commodity: String,
    ) : ForecastResponse

    @GET("/bookmarks")
    suspend fun getBookmarks(
//        @Header("Authorization") token: String
    ): BookmarkResponse

    @DELETE("/bookmarks/{bookmarkId}")
    suspend fun deleteBookmark(
//        @Header("Authorization") token: String,
        @Path("bookmarkId") bookmarkId: String
    ): BookmarkResponse

    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): Call<WeatherResponse>
}