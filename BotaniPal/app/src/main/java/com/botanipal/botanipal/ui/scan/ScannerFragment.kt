package com.botanipal.botanipal.ui.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.botanipal.botanipal.R
import com.botanipal.botanipal.data.model.Prediction
import com.botanipal.botanipal.data.api.ApiConfig
import com.botanipal.botanipal.data.response.ScanResponse
import com.botanipal.botanipal.databinding.FragmentScannerBinding
import com.botanipal.botanipal.helper.uriToFile
import com.botanipal.botanipal.ui.scan.CameraDiseaseActivity.Companion.CAMERAX_RESULT
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class ScannerFragment : Fragment() {
    private val viewModel: ScannerViewModel by viewModels()
    private lateinit var binding: FragmentScannerBinding

    private var currentImageUri: Uri? = null
    private var displayResult: String = ""

    private lateinit var prediction: Prediction

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA

        fun newInstance() = ScannerFragment()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentScannerBinding.bind(view)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        // TODO: Use the ViewModel
        binding.scanDisease.setOnClickListener { startCameraDisease() }
        binding.scanType.setOnClickListener { startCameraType() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

//    private fun startGallery() {
//        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }
//
//    private val launcherGallery = registerForActivityResult(
//        ActivityResultContracts.PickVisualMedia()
//    ) { uri: Uri? ->
//        if (uri != null) {
//            currentImageUri = uri
//            showImage()
//        } else {
//            Log.d("Photo Picker", "No media selected")
//        }
//    }

    private fun startCameraType() {
        val intent = Intent(requireContext(), CameraTypeActivity()::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startCameraDisease() {
        val intent = Intent(requireContext(), CameraDiseaseActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraDiseaseActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
//            showImage()

//            prediction.photo = CameraActivity.EXTRA_CAMERAX_IMAGE
//            prediction.result =
//            val intent = Intent(requireContext(), ResultActivity::class.java)
//            intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, file.absolutePath)
//            startActivity(intent)
        }
    }


}