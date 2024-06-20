package com.botanipal.botanipal.data.api

import androidx.lifecycle.LiveData
import com.botanipal.botanipal.data.model.ForecastRequest
import com.botanipal.botanipal.data.model.LoginRequest
import com.botanipal.botanipal.data.model.RegisterRequest
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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
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
//        @Query("username") username: String,
//        @Query("email") email: String,
//        @Query("password") password: String,
//        @Query("confirmPassword") confirmPassword: String
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("/auth/login")
    suspend fun loginUser(
//        @Query("username") username: String,
//        @Query("password") password: String
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("/auth/forgot-password")
    suspend fun forgotPassword(
        @Query("email") email: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/auth/reset-password")
    suspend fun resetPassword(
        @Field("username") username: String,
        @Field("otp") otp: String,
        @Field("newPassword") newPassword: String,
        @Field("newConfirmPassword") newConfirmPassword: String
    ): LoginResponse

    @GET("/profile/user")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): ProfileResponse

    @FormUrlEncoded
    @Multipart
    @POST("/profile/user/update")
    suspend fun updateUserProfile(
//        @Header("Authorization") token: String,
        @Field("username") username: String,
        @Field("name") name: String,
        @Field("bio") bio: String,
        @Part profileImage: MultipartBody.Part
    ): ProfileResponse

    @FormUrlEncoded
    @POST("/transactions")
    suspend fun getTransactions(
//        @Header("Authorization") token: String,
        @Field("uid_expert")  uid_expert: String,
        @Field("uid_user") uid_user: String,
        @Field("paymentNominal") paymentNominal: Int,
        @Field("paymentMethod") paymentMethod: String,
        @Field("transactionProgress") transactionProgress: String,
        @Field("timestamp") timestamp: String
    ): TransactionResponse

    @GET("/transactions/users/{userid}/transactions")
    suspend fun getTransactionsByUser(
        @Header("Authorization") token: String,
        @Path("userid") userid: String
    ): TransactionResponse

    @GET("/transactions/{transactionid}")
    suspend fun getTransactionById(
        @Header("Authorization") token: String,
        @Path("transactionid") transactionid: String
    ): TransactionResponse

    @FormUrlEncoded
    @PUT("/transactions/{transactionId}")
    suspend fun updateTransaction(
//        @Header("Authorization") token: String,
        @Path("transactionId") transactionId: String,
        @Field("transactionProgress") transactionProgress: String
        ): TransactionResponse

    //    plant
    @Multipart
    @POST("/predict/plant")
    fun uploadTypeImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Call<ScanResponse>

    //    disease
    @Multipart
    @POST("/predict/disease")
    fun uploadDiseaseImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Call<ScanResponse>

    @FormUrlEncoded
    @POST("/predict/bookmark")
    suspend fun bookmarkPlant(
        @Header("Authorization") token: String,
        @Field("predictionId") predictionId: String,
        @Field("prediction") prediction: String,
        @Field("imageUrl") imageUrl: String,
        @Field("predictionType") predictionType: String
    ): AddBookmarkResponse

//    @FormUrlEncoded
    @POST("/forecast")
    fun uploadForecast(
        @Header("Authorization") token: String,
//        @Field("future_date") future_date: String,
//        @Field("commodity") commodity: String,
        @Body forecastRequest: ForecastRequest
    ) : Call<ForecastResponse>

    @GET("/bookmarks")
    suspend fun getBookmarks(
        @Header("Authorization") token: String
    ): BookmarkResponse

    @DELETE("/bookmarks/{bookmarkId}")
    suspend fun deleteBookmark(
        @Header("Authorization") token: String,
        @Path("bookmarkId") bookmarkId: String
    ): BookmarkResponse

    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): Call<WeatherResponse>
}