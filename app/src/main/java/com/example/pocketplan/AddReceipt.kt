package com.example.pocketplan

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddReceipt : BaseActivity() {

    private lateinit var imageView: ImageView
    private lateinit var takePhotoButton: Button
    private lateinit var uploadButton: Button

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageView.setImageURI(uri)
        } ?: showToast("No image selected")
    }

    private val takePhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photo = result.data?.extras?.get("data") as? Bitmap
            photo?.let {
                imageView.setImageBitmap(it)
            } ?: showToast("No photo captured")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_receipt)

        imageView = findViewById(R.id.imageViewReceipt)
        uploadButton = findViewById(R.id.uploadButton)
        takePhotoButton = findViewById(R.id.btnOpenCamera)


        takePhotoButton.setOnClickListener {
            takePhoto()
        }
        uploadButton.setOnClickListener {
            // Get bitmap from ImageView
            val bitmap = (imageView.drawable as? BitmapDrawable)?.bitmap
            if (bitmap != null) {
                val imageBytes = getBitmapAsByteArray(bitmap)
                val dbHelper = PocketPlanDBHelper(this)
                val id = dbHelper.insertImage(imageBytes)
                if (id != -1L) {
                    showToast(getString(R.string.image_saved_successfully))
                    
                    // Return the image ID to the calling activity
                    val resultIntent = Intent().apply {
                        putExtra("image_id", id)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                } else {
                    showToast("Failed to save image")
                }
            } else {
                showToast("No image to upload")
            }
        }
    }

    private fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            takePhotoLauncher.launch(takePictureIntent)
        } catch (e: Exception) {
            showToast("Camera not available")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}
