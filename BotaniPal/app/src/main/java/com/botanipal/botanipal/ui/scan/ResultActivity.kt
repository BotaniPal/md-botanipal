package com.botanipal.botanipal.ui.scan

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.botanipal.botanipal.R
import com.botanipal.botanipal.data.model.Prediction
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
    private lateinit var continueButton: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fabSave: ExtendedFloatingActionButton = binding.fabSave
        val colorSecondary = ContextCompat.getColor(this, R.color.secondary_1000)
        val textColorStatic = ContextCompat.getColor(this, R.color.black)
        fabSave.backgroundTintList = ColorStateList.valueOf(colorSecondary)
        fabSave.setTextColor(textColorStatic)
        fabSave.iconTint = ColorStateList.valueOf(textColorStatic)

        val image = intent.getStringExtra(EXTRA_IMAGE_URI) ?: return
        val result = intent.getStringExtra(EXTRA_RESULT) ?: return
        val id = intent.getStringExtra(EXTRA_ID) ?: return
        val type = intent.getStringExtra(EXTRA_TYPE) ?: return

        val imageUri = Uri.parse(image)
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
            binding.resultText.text = intent.getStringExtra(EXTRA_RESULT)
        }

        savedButton = findViewById(R.id.fab_save)
        continueButton = binding.fabContinue

        bindData(Prediction(id, result, image, type))

//        resultViewModel.fetchPredictionByUri(EXTRA_IMAGE_URI)

//        isAutoSave(image, result)

//        resultViewModel.predictionData.observe(this) { prediction ->
//            prediction?.let {
//                bindData(Prediction(image, result))
//            }
//        }

        // gatau bener ta ga blom nyoba aokwowk
        continueButton.setOnClickListener {
            finish()
        }

//        resultViewModel.getPredictionByUri(image).observe(this) { result ->
//            isSaved = result != null
//            updateSaveButtonState()
//        }
    }

    private fun bindData(prediction: Prediction) {
        Glide.with(this)
            .load(prediction.imageUrl)
//            .circleCrop()
            .into(binding.resultImage)
        binding.resultText.text = prediction.prediction
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.result_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.share -> {
                // Handle settings
//                val accUrl = userDetailsViewModel.userDetails.value?.url
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, EXTRA_IMAGE_URI)
                    putExtra(Intent.EXTRA_TEXT, EXTRA_RESULT)
                    type = "text/plain"
                }

                val chooser = Intent.createChooser(shareIntent, "Bagikan dengan...")
                startActivity(chooser)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

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
        const val EXTRA_ID = "extra_id"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
       const val EXTRA_TYPE = "extra_type"
    }
}