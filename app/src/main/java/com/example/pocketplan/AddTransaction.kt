package com.example.pocketplan


import android.R.attr.id
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTransaction : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var balanceEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var categorySpinner: Spinner
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
                val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
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
        categorySpinner = findViewById(R.id.categorySpinner)
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
        val categories = listOf("Food", "Transport", "Other") // Example
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        val saveButton: Button = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {

            val intent = Intent(this, Reports::class.java)

            startActivity(intent)
        }

        // Set up receipt upload
        uploadReceiptButton.setOnClickListener {
            val intent = Intent(this, AddReceipt::class.java)

            startActivity(intent)
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

//   private fun dispatchTakePictureIntent() {
//    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//        takePictureIntent.resolveActivity(packageManager)?.also {
//            val photoFile: File? = try {
//                createImageFile()
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//                null
//            }
//            photoFile?.also {
//                val photoURI: Uri = FileProvider.getUriForFile(
//                    this,
//                    "com.example.pocketplan.fileprovider",
//                    it
//                )
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                takePictureLauncher.launch(takePictureIntent)
//
//                // 🔁 Navigate to AddReceiptActivity
//
//            }
//        }
//    }
//}

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

            // Navigate to ExpensesActivity
            val intent = Intent(this, Expenses::class.java)
            startActivity(intent)
            finish() // Optional: closes current activity
        } else {
            Toast.makeText(this, "Error saving transaction", Toast.LENGTH_SHORT).show()
        }
    }
}

private fun AddTransaction.dispatchTakePictureIntent() {
    TODO("Not yet implemented")
}
