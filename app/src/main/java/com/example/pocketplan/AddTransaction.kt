package com.example.pocketplan


import android.R.attr.id
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTransaction : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var balanceEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var categoryTextView: TextView
    private lateinit var dateEditText: EditText
    private lateinit var uploadReceiptButton: ImageButton
    private lateinit var saveButton: Button

    private var selectedCategory: Category? = null
    private var currentPhotoPath: String? = null
    private var receiptId: Int? = null

    private val calendar = Calendar.getInstance()

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Receipt captured, save to database
            currentPhotoPath?.let {
                receiptId = dbHelper.addReceipt(it).toInt()
                Toast.makeText(this, "Receipt saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val selectCategoryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                val categoryId = data.getIntExtra("category_id", 0)
                val categoryName = data.getStringExtra("category_name") ?: "Other"
                selectedCategory = Category(categoryId, categoryName)
                categoryTextView.text = categoryName
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        dbHelper = DatabaseHelper(this)

        // Initialize views
        balanceEditText = findViewById(R.id.balanceEditText)
        amountEditText = findViewById(R.id.amountEditText)
        categoryTextView = findViewById(R.id.categorySpinner)
        dateEditText = findViewById(R.id.dateEditText)
        uploadReceiptButton = findViewById(R.id.uploadButton)
        saveButton = findViewById(R.id.saveButton)

        // Set current balance
        balanceEditText.setText(String.format("%.2f", dbHelper.getBalance()))
        balanceEditText.isEnabled = false // Display-only

        // Set default date to today
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateEditText.setText(dateFormat.format(Date()))

        // Set up date picker
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        dateEditText.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Set up category selection
        categoryTextView.setOnClickListener {
            // Here you would launch a category selection dialog or activity
            // For simplicity, we'll just use the default category in this example
            val intent = Intent(this, CategorySelectionActivity::class.java)
            selectCategoryLauncher.launch(intent)
        }

        // Set up receipt upload
        uploadReceiptButton.setOnClickListener {
            dispatchTakePictureIntent()
        }

        // Set up save button
        saveButton.setOnClickListener {
            saveTransaction()
        }
    }

    private fun updateDateInView() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateEditText.setText(dateFormat.format(calendar.time))
    }

//    private fun dispatchTakePictureIntent() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            // Ensure that there's a camera activity to handle the intent
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                // Create the File where the photo should go
//                val photoFile: File? = try {
//                    createImageFile()
//                } catch (ex: Exception) {
//                    // Error occurred while creating the File
//                    null
//                }
//                // Continue only if the File was successfully created
//                photoFile?.also {
//                    val photoURI: Uri = FileProvider.getUriForFile(
//                        this,
//                        "com.example.pocketplan.fileprovider",
//                        it
//                    )
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                    takePictureLauncher.launch(takePictureIntent)
//                }
//            }
//        }
//    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun saveTransaction() {
        // Validate input
        val amountText = amountEditText.text.toString()
        if (amountText.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
            return
        }

        // Create transaction
        val transaction = Transaction(
            id = id.toLong(), // Make expenses negative
            amount = if (amount < 0) amount else -amount, // Default to "Other" if none selected
            category = selectedCategory?.name ?: "Other",
            date = dateEditText.text.toString(),
            receiptUri = receiptId?.let { Uri.parse("content://com.example.pocketplan.fileprovider/$it") }
        )

        // Save to database
        val id = dbHelper.addTransaction(transaction)
        if (id > 0) {
            Toast.makeText(this, "Transaction saved successfully", Toast.LENGTH_SHORT).show()
            finish() // Return to previous screen
        } else {
            Toast.makeText(this, "Error saving transaction", Toast.LENGTH_SHORT).show()
        }
    }
}

private fun AddTransaction.dispatchTakePictureIntent() {
    TODO("Not yet implemented")
}
