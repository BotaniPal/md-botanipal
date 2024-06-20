package com.botanipal.botanipal.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.botanipal.botanipal.BuildConfig
import com.botanipal.botanipal.data.model.ForecastRequest
import com.botanipal.botanipal.data.model.LoginRequest
import com.botanipal.botanipal.data.model.Prediction
import com.botanipal.botanipal.data.model.RegisterRequest
import com.botanipal.botanipal.data.pref.UserModel
import com.botanipal.botanipal.data.pref.UserPreference
import com.botanipal.botanipal.data.response.AddBookmarkResponse
import com.botanipal.botanipal.data.response.BookmarkResponse
import com.botanipal.botanipal.data.response.DataPrice
import com.botanipal.botanipal.data.response.ForecastResponse
import com.botanipal.botanipal.data.response.LoginResponse
import com.botanipal.botanipal.data.response.ProfileData
import com.botanipal.botanipal.data.response.ProfileResponse
import com.botanipal.botanipal.data.response.RegisterResponse
import com.botanipal.botanipal.data.response.ScanData
import com.botanipal.botanipal.data.response.ScanResponse
import com.botanipal.botanipal.data.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    var token: String = " "
    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _scanType = MutableLiveData<ScanData>()
    val scanType: LiveData<ScanData> = _scanType

    private val _scanDisease= MutableLiveData<ScanData>()
    val scanDisease: LiveData<ScanData> = _scanDisease

    private val _priceBawang = MutableLiveData<DataPrice>()
    val priceBawang: LiveData<DataPrice> = _priceBawang

    private val _priceCabe = MutableLiveData<DataPrice>()
    val priceCabe: LiveData<DataPrice> = _priceCabe

    private val _priceJagung = MutableLiveData<DataPrice>()
    val priceJagung: LiveData<DataPrice> = _priceJagung

    private val _priceKacang = MutableLiveData<DataPrice>()
    val priceKacang: LiveData<DataPrice> = _priceKacang

    private val _priceKedelai = MutableLiveData<DataPrice>()
    val priceKedelai: LiveData<DataPrice> = _priceKedelai

    private val _priceKentang = MutableLiveData<DataPrice>()
    val priceKentang: LiveData<DataPrice> = _priceKentang

    private val _priceKol = MutableLiveData<DataPrice>()
    val priceKol: LiveData<DataPrice> = _priceKol

    private val _priceTomat = MutableLiveData<DataPrice>()
    val priceTomat: LiveData<DataPrice> = _priceTomat

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

//    private val apiService: ApiService = ApiConfig.getApiService(token)

//    private fun getFutureDate(): String {
//        val future_date = LocalDate.now()
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//        val formatted = future_date.format(formatter)
//        return formatted
//    }
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(name: String, email: String, password: String, confirmPassword: String): RegisterResponse {
//        Log.d("userrepository", "$name $email $password $confirmPassword")
//        return apiService.registerUser(name, email, password, confirmPassword)

        Log.d("userrepository", "$name $email $password $confirmPassword")
        val registerRequest = RegisterRequest(name, email, password, confirmPassword)
        return apiService.registerUser(registerRequest)
    }

    suspend fun login(username: String, password: String): LoginResponse {
//        Log.d("userrepository", "$username $password")
        val loginRequest = LoginRequest(username, password)
        Log.d("userrepository", "$loginRequest")
        return ApiConfig.getApiService().loginUser(loginRequest)
    }

    suspend fun getProfile(): ProfileResponse {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""
//        token = runBlocking {
//            userPreference.getSession().firstOrNull()?.token
//        }
        Log.d("userrepository", "Token getstories: $token")
        return ApiConfig.getApiService().getUserProfile("Bearer $token")
    }

    suspend fun updateProfile(name: String, email: String, password: String, image: MultipartBody.Part): ProfileResponse {
//        token = runBlocking {
//            userPreference.getSession().firstOrNull()?.token
//        }
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""
        Log.d("userrepository", "Token getstories: $token")
        return apiService.updateUserProfile(name, email, password, image)
    }

    fun scanType(image: MultipartBody.Part) {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""
        try {
            val successResponse = ApiConfig.getTypeApiService().uploadTypeImage("Bearer $token", image)
            successResponse.enqueue(
                object : Callback<ScanResponse> {
                    override fun onResponse(
                        call: Call<ScanResponse>,
                        response: Response<ScanResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _scanType.value = response.body()?.data
                            Log.d("ScanType-success-repo", response.toString())
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.d("ScanType-repo", response.message())
                            Result.failure(Exception("Error: ${response.message()}"))
                        }
                    }

                    override fun onFailure(call: Call<ScanResponse>, t: Throwable) {
                        Log.e("ScanType-failure-repo", t.message.toString())
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("ScanType-exception-repo", e.message.toString())
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("ScanType-http-exception-repo", e.message.toString())
            Result.failure<HttpException>(e)
        }
    }

//     fun scanDisease(image: MultipartBody.Part): Result<ScanResponse> {
//         token  =  runBlocking {
//             userPreference.getSession().firstOrNull()?.token
//         } ?: ""
//         if (!token.isNullOrEmpty()) {
//             ApiConfig.getApiService().uploadDiseaseImage("Bearer $token", image).enqueue(
//                 object : Callback<ScanResponse> {
//                     override fun onResponse(
//                         call: Call<ScanResponse>,
//                         response: Response<ScanResponse>
//                     ) {
//                         if (response.isSuccessful) {
//                                _isSuccessful.value = true
//                             Result.success(response.body()!!)
//                         } else {
//                                _isSuccessful.value = false
//                             Result.failure(Exception("Error: ${response.message()}"))
//                         }
//                     }
//                     override fun onFailure(call: Call<ScanResponse>, t: Throwable) {
//                         Log.e("UploadError", "onFailure: ${t.message}")
//                     }
//                 })
//
//         } else {
//             return Result.failure(Exception("Token is null"))
//         }
//         return Result.failure(Exception("Error"))
////        return apiService.uploadDiseaseImage("Bearer $token", image)
//    }

    fun scanDisease(image: MultipartBody.Part) {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""
        try {
            val successResponse = ApiConfig.getDiseaseApiService().uploadDiseaseImage("Bearer $token", image)
            successResponse.enqueue(
                object : Callback<ScanResponse> {
                    override fun onResponse(
                        call: Call<ScanResponse>,
                        response: Response<ScanResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _scanDisease.value = response.body()?.data
                            Log.d("ScanDisease-success-repo", response.toString())
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.d("ScanDisease-repo", response.message())
                            Result.failure(Exception("Error: ${response.message()}"))
                        }
                    }

                    override fun onFailure(call: Call<ScanResponse>, t: Throwable) {
                        Log.e("ScanDisease-failure-repo", t.message.toString())
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("ScanDisease-exception-repo", e.message.toString())
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("ScanDisease-http-exception-repo", e.message.toString())
            Result.failure<HttpException>(e)
        }
    }
    suspend fun getBookmark():BookmarkResponse {
         token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""
        return ApiConfig.getApiService().getBookmarks("Bearer $token")
    }

    suspend fun addBookmark(predictionId: String, prediction: String, imageUrl: String, predictionType: String ): AddBookmarkResponse {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""
        return ApiConfig.getApiService().bookmarkPlant("Bearer $token",predictionId, prediction, imageUrl, predictionType)
    }

//    suspend fun getBawangForecast(date: String) : Int {
//        val token  =  runBlocking {
//            userPreference.getSession().firstOrNull()?.token
//        } ?: ""
////        val date = getFutureDate()
//        val forecastRequest = ForecastRequest(date, "bawang_merah")
//        Log.d("userrepository-bawang", "$token, $date, ${ApiConfig.getApiService().uploadForecast("Bearer $token", forecastRequest)}")
//        return ApiConfig.getApiService().uploadForecast("Bearer $token", forecastRequest).data.predictedPrice
//    }
    suspend fun getBawangForecast(date: String)  {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""

        try {
            val successResponse = ApiConfig.getApiService().uploadForecast("Bearer $token", ForecastRequest(date, "bawang_merah"))
            successResponse.enqueue(
                object : Callback<ForecastResponse> {
                    override fun onResponse(
                        call: Call<ForecastResponse>,
                        response: Response<ForecastResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _priceBawang.value = response.body()?.data
                            Log.d("Forecast-bawang", "Response: $response")
                            Log.d("Forecast-bawang", "Price: ${response.body()?.data?.predictedPrice}")
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.e("Forecast-bawang", "Error: ${response.errorBody()} ${response.body()}")
                            Result.failure(Exception("Error: ${response.errorBody()}"))
                        }
                    }
                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        Log.e("Forecast-bawang", "Error: ${t.message}")
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("Forecast-bawang", "Error: ${e.message}")
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("Forecast-bawang", "Error: ${e.message}")
            Result.failure<HttpException>(e)
        }
    }

    fun getCabeForecast(date: String)  {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""

        try {
            val successResponse = ApiConfig.getApiService().uploadForecast("Bearer $token", ForecastRequest(date, "cabe_rawit_merah"))
            successResponse.enqueue(
                object : Callback<ForecastResponse> {
                    override fun onResponse(
                        call: Call<ForecastResponse>,
                        response: Response<ForecastResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _priceCabe.value = response.body()?.data
                            Log.d("Forecast-cabe", "Response: $response")
                            Log.d("Forecast-cabe", "Price: ${response.body()?.data?.predictedPrice}")
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.e("Forecast-cabe", "Error: ${response.errorBody()} ${response.body()}")
                            Result.failure(Exception("Error: ${response.errorBody()}"))
                        }
                    }
                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        Log.e("Forecast-cabe", "Error: ${t.message}")
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("Forecast-cabe", "Error: ${e.message}")
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("Forecast-cabe", "Error: ${e.message}")
            Result.failure<HttpException>(e)
        }
    }

    fun getJagungForecast(date: String)  {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""

        try {
            val successResponse = ApiConfig.getApiService().uploadForecast("Bearer $token", ForecastRequest(date, "kedelai"))
            successResponse.enqueue(
                object : Callback<ForecastResponse> {
                    override fun onResponse(
                        call: Call<ForecastResponse>,
                        response: Response<ForecastResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _priceJagung.value = response.body()?.data
                            Log.d("Forecast-kedelai", "Response: $response")
                            Log.d("Forecast-kedelai", "Price: ${response.body()?.data?.predictedPrice}")
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.e("Forecast-kedelai", "Error: ${response.errorBody()} ${response.body()}")
                            Result.failure(Exception("Error: ${response.errorBody()}"))
                        }
                    }
                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        Log.e("Forecast-kedelai", "Error: ${t.message}")
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("Forecast-kedelai", "Error: ${e.message}")
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("Forecast-kedelai", "Error: ${e.message}")
            Result.failure<HttpException>(e)
        }
    }

    fun getKacangForecast(date: String)  {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""

        try {
            val successResponse = ApiConfig.getApiService().uploadForecast("Bearer $token", ForecastRequest(date, "kacang_tanah"))
            successResponse.enqueue(
                object : Callback<ForecastResponse> {
                    override fun onResponse(
                        call: Call<ForecastResponse>,
                        response: Response<ForecastResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _priceKacang.value = response.body()?.data
                            Log.d("Forecast-kacang", "Response: $response")
                            Log.d("Forecast-kacang", "Price: ${response.body()?.data?.predictedPrice}")
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.e("Forecast-kacang", "Error: ${response.errorBody()} ${response.body()}")
                            Result.failure(Exception("Error: ${response.errorBody()}"))
                        }
                    }
                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        Log.e("Forecast-kacang", "Error: ${t.message}")
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("Forecast-kacang", "Error: ${e.message}")
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("Forecast-kacang", "Error: ${e.message}")
            Result.failure<HttpException>(e)
        }
    }

    fun getKedelaiForecast(date: String)  {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""

        try {
            val successResponse = ApiConfig.getApiService().uploadForecast("Bearer $token", ForecastRequest(date, "kedelai"))
            successResponse.enqueue(
                object : Callback<ForecastResponse> {
                    override fun onResponse(
                        call: Call<ForecastResponse>,
                        response: Response<ForecastResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _priceKedelai.value = response.body()?.data
                            Log.d("Forecast-kedelai", "Response: $response")
                            Log.d("Forecast-kedelai", "Price: ${response.body()?.data?.predictedPrice}")
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.e("Forecast-kedelai", "Error: ${response.errorBody()} ${response.body()}")
                            Result.failure(Exception("Error: ${response.errorBody()}"))
                        }
                    }
                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        Log.e("Forecast-kedelai", "Error: ${t.message}")
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("Forecast-kedelai", "Error: ${e.message}")
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("Forecast-kedelai", "Error: ${e.message}")
            Result.failure<HttpException>(e)
        }
    }

    fun getKentangForecast(date: String)  {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""

        try {
            val successResponse = ApiConfig.getApiService().uploadForecast("Bearer $token", ForecastRequest(date, "kentang"))
            successResponse.enqueue(
                object : Callback<ForecastResponse> {
                    override fun onResponse(
                        call: Call<ForecastResponse>,
                        response: Response<ForecastResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _priceKentang.value = response.body()?.data
                            Log.d("Forecast-kentang", "Response: $response")
                            Log.d("Forecast-kentang", "Price: ${response.body()?.data?.predictedPrice}")
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.e("Forecast-kentang", "Error: ${response.errorBody()} ${response.body()}")
                            Result.failure(Exception("Error: ${response.errorBody()}"))
                        }
                    }
                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        Log.e("Forecast-kentang", "Error: ${t.message}")
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("Forecast-kentang", "Error: ${e.message}")
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("Forecast-kentang", "Error: ${e.message}")
            Result.failure<HttpException>(e)
        }
    }

    fun getKolForecast(date: String)  {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""

        try {
            val successResponse = ApiConfig.getApiService().uploadForecast("Bearer $token", ForecastRequest(date, "kol"))
            successResponse.enqueue(
                object : Callback<ForecastResponse> {
                    override fun onResponse(
                        call: Call<ForecastResponse>,
                        response: Response<ForecastResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _priceKol.value = response.body()?.data
                            Log.d("Forecast-kol", "Response: $response")
                            Log.d("Forecast-kol", "Price: ${response.body()?.data?.predictedPrice}")
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.e("Forecast-kol", "Error: ${response.errorBody()} ${response.body()}")
                            Result.failure(Exception("Error: ${response.errorBody()}"))
                        }
                    }
                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        Log.e("Forecast-kol", "Error: ${t.message}")
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("Forecast-kol", "Error: ${e.message}")
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("Forecast-kol", "Error: ${e.message}")
            Result.failure<HttpException>(e)
        }
    }

    fun getTomatForecast(date: String)  {
        token  =  runBlocking {
            userPreference.getSession().firstOrNull()?.token
        } ?: ""

        try {
            val successResponse = ApiConfig.getApiService().uploadForecast("Bearer $token", ForecastRequest(date, "tomat"))
            successResponse.enqueue(
                object : Callback<ForecastResponse> {
                    override fun onResponse(
                        call: Call<ForecastResponse>,
                        response: Response<ForecastResponse>
                    ) {
                        if (response.isSuccessful) {
                            _isSuccessful.value = true
                            _priceTomat.value = response.body()?.data
                            Log.d("Forecast-tomat", "Response: $response")
                            Log.d("Forecast-tomat", "Price: ${response.body()?.data?.predictedPrice}")
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Log.e("Forecast-tomat", "Error: ${response.errorBody()} ${response.body()}")
                            Result.failure(Exception("Error: ${response.errorBody()}"))
                        }
                    }
                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        Log.e("Forecast-tomat", "Error: ${t.message}")
                    }
                }
            )
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("Forecast-tomat", "Error: ${e.message}")
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("Forecast-tomat", "Error: ${e.message}")
            Result.failure<HttpException>(e)
        }
    }

    fun getWeatherData(location: String)  {
//        token  =  runBlocking {
//            userPreference.getSession().firstOrNull()?.token
//        } ?: ""
        try {
            ApiConfig.getWeatherApiService().getCurrentWeather(BuildConfig.API_WEATHER, location)
                .enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                        if (response.isSuccessful) {
                            val weatherResponse = response.body()
                            val temp = "${weatherResponse?.current?.temp_c}°C"
                            _isSuccessful.value = true
                            _weatherData.value = response.body()
                            Log.d("Weather", "Response: $weatherResponse")
                            Log.d("Weather", "Temperature: $temp")
                            Result.success(response.body())
                        } else {
                            _isSuccessful.value = false
                            Result.failure<Exception>(Exception("Error: ${response.message()}"))
                            Log.e("Weather", "Error: ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        Result.failure<Exception>(Exception("Error: ${t.message}"))
                        Log.e("Weather", "Error: ${t.message}")
                    }
                })
        } catch (e: Exception) {
            _isSuccessful.value = false
            Log.e("Weather", "Error: ${e.message}")
            Result.failure<Exception>(e)
        } catch (e:HttpException) {
            _isSuccessful.value = false
            Log.e("Weather", "Error: ${e.message}")
            Result.failure<HttpException>(e)
        }


//        return withContext(Dispatchers.IO) {
//            try {
//                token = userPreference.getSession().firstOrNull()?.token
//                val response = ApiConfig.getWeatherApiService().getCurrentWeather(BuildConfig.API_WEATHER, location)
//                Result.success(response)
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
    }




    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService,userPreference)
            }.also { instance = it }
    }

}