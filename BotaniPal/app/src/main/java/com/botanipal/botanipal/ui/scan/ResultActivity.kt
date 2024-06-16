package com.botanipal.botanipal.ui.scan

import android.content.Context
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.botanipal.botanipal.R
import com.botanipal.botanipal.data.Prediction
import com.botanipal.botanipal.data.api.Response
import com.botanipal.botanipal.databinding.ActivityResultBinding
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
//    private val resultViewModel: ResultViewModel by viewModels {
//        ViewModelFactory.getInstance(application)
//    }
    private var isSaved: Boolean = false
    private lateinit var savedButton: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fabSave: ExtendedFloatingActionButton = binding.fabSave
        val colorSecondary = ContextCompat.getColor(this, R.color.secondary_1000)
        val textColorStatic = ContextCompat.getColor(this, R.color.black)
        fabSave.backgroundTintList = ColorStateList.valueOf(colorSecondary)
        fabSave.setTextColor(textColorStatic)
        fabSave.iconTint = ColorStateList.valueOf(textColorStatic)

        val image = intent.getStringExtra(EXTRA_IMAGE_URI) ?: return
        val result = intent.getStringExtra(EXTRA_RESULT) ?: return

        val imageUri = Uri.parse(image)
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
            binding.resultText.text = intent.getStringExtra(EXTRA_RESULT)
        }

        savedButton = findViewById(R.id.fab_save)

        bindData(Prediction(image, result))

//        resultViewModel.fetchPredictionByUri(EXTRA_IMAGE_URI)

//        isAutoSave(image, result)

//        resultViewModel.predictionData.observe(this) { prediction ->
//            prediction?.let {
//                bindData(Prediction(image, result))
//            }
//        }

//        savedButton.setOnClickListener {
//            toggleSaveState(image, result)
//        }

//        resultViewModel.getPredictionByUri(image).observe(this) { result ->
//            isSaved = result != null
//            updateSaveButtonState()
//        }
    }

    private fun bindData(prediction: Prediction) {
        Glide.with(this)
            .load(prediction.photo)
//            .circleCrop()
            .into(binding.resultImage)
        binding.resultText.text = prediction.result
    }

//    private fun isAutoSave(imageUri: String, result: String) {
//        val pref = getSharedPreferences("autosave", Context.MODE_PRIVATE)
//        val isAutosaveActive = pref.getBoolean("is_autosave_active", false)
//        if (isAutosaveActive) {
//            resultViewModel.addPrediction(Prediction(resultViewModel.predictionData.value?.id, imageUri, result, resultViewModel.predictionData.value?.timestamp))
//            showToast("Image saved")
//            isSaved = true
//        }
//    }

//    private fun toggleSaveState(imageUri: String, result: String) {
//        if (isSaved){
//            resultViewModel.deletePrediction(imageUri)
//            showToast("Image unsaved")
//        } else {
//            resultViewModel.addPrediction(Prediction(resultViewModel.predictionData.value?.id, imageUri, result, resultViewModel.predictionData.value?.timestamp))
//            showToast("Image saved")
//        }
//        isSaved = !isSaved
//        updateSaveButtonState()
//    }

    private fun updateSaveButtonState() {
        if (isSaved) {
            savedButton.setIconResource(R.drawable.icons8_save_filled)
        } else {
            savedButton.setIconResource(R.drawable.icons8_save)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}