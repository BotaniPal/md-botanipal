package com.botanipal.botanipal.ui.scan


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.botanipal.botanipal.data.model.Prediction
import com.botanipal.botanipal.data.api.ApiConfig
import com.botanipal.botanipal.data.response.Response
import com.botanipal.botanipal.databinding.ActivityCameraDiseaseBinding
import com.botanipal.botanipal.helper.createCustomTempFile
import com.botanipal.botanipal.helper.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class CameraDiseaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraDiseaseBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private var currentImageUri: Uri? = null
    private var displayResult: String = ""

    private lateinit var prediction: Prediction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraDiseaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prediction = Prediction(" ", " ")

        binding.switchCamera.setOnClickListener {
            cameraSelector =
                if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()

        }
        binding.captureImage.setOnClickListener {
            takePhoto()
        }

        binding.gallery.setOnClickListener {
            startGallery()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            uploadImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
//        currentImageUri = getImageUri(applicationContext)
        Log.d(TAG, "currentImageUri: $currentImageUri")

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraDiseaseActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createCustomTempFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                    currentImageUri = output.savedUri ?: Uri.fromFile(photoFile)
                    Log.d(TAG, "Photo saved to: ${currentImageUri.toString()}")
                    val intent = Intent().apply {
                        putExtra(EXTRA_CAMERAX_IMAGE, currentImageUri.toString())
                    }
//                    setResult(CAMERAX_RESULT, intent)
//                    finish()
                    uploadImage()
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraDiseaseActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            Log.d("UploadImage", "URI: $uri, File: ${imageFile}, Exists: ${imageFile.exists()}")
            if (!imageFile.exists()) {
                Toast.makeText(this, "File does not exist.", Toast.LENGTH_SHORT).show()
                return
            }

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getDiseaseApiService()
                    val successResponse = apiService.uploadDiseaseImage(multipartBody)
                    successResponse.prediction.let {
                        displayResult = it

                        val intent = Intent(this@CameraDiseaseActivity, ResultActivity::class.java).apply {
                            putExtra(ResultActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
                            putExtra(ResultActivity.EXTRA_RESULT, displayResult)
                        }
                        Log.d("Result", "uploadImage: $currentImageUri  hasil $displayResult")
                        startActivity(intent)

                    }
//                    showLoading(false)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, Response::class.java)
                    Log.e("Upload Error", "HTTP error: ${errorResponse.prediction}")
                }
            }
        } ?: run {
            Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private val orientationEventListener by lazy {
        object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = when (orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }

                imageCapture?.targetRotation = rotation
            }
        }
    }

    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        orientationEventListener.disable()
    }

    companion object {
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
    }
}
