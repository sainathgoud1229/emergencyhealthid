package com.example.emergencyhealthid

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var bloodGroupInput: EditText
    private lateinit var allergiesInput: EditText
    private lateinit var emergencyContactInput: EditText
    private lateinit var generateButton: Button
    private lateinit var shareButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        nameInput = findViewById(R.id.nameInput)
        bloodGroupInput = findViewById(R.id.bloodGroupInput)
        allergiesInput = findViewById(R.id.allergiesInput)
        emergencyContactInput = findViewById(R.id.emergencyContactInput)
        generateButton = findViewById(R.id.generateButton)
        shareButton = findViewById(R.id.shareButton)

        // Set up click listeners
        generateButton.setOnClickListener {
            generateQrCode()
        }

        shareButton.setOnClickListener {
            shareQrCode()
        }
    }

    private fun generateQrCode() {
        val name = nameInput.text.toString().trim()
        val bloodGroup = bloodGroupInput.text.toString().trim()
        val allergies = allergiesInput.text.toString().trim()
        val contact = emergencyContactInput.text.toString().trim()

        if (name.isEmpty() || bloodGroup.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Combine data into a single string for the QR code
        val data = "Name: $name\nBlood Group: $bloodGroup\nAllergies: $allergies\nEmergency Contact: $contact"

        try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val pixels = IntArray(width * height)

            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (bitMatrix.get(x, y)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
                }
            }

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)

            saveBitmapToCache(bitmap)
            Toast.makeText(this, "QR Code Generated!", Toast.LENGTH_SHORT).show()

        } catch (e: WriterException) {
            Toast.makeText(this, "Error generating QR Code", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveBitmapToCache(bitmap: Bitmap) {
        val cachePath = File(cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "qr_code.png")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
    }

    private fun shareQrCode() {
        val file = File(File(cacheDir, "images"), "qr_code.png")
        if (!file.exists()) {
            Toast.makeText(this, "Please generate QR code first", Toast.LENGTH_SHORT).show()
            return
        }

        val fileUri: Uri? = try {
            FileProvider.getUriForFile(this, "com.example.emergencyhealthid.fileprovider", file)
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, "The selected file can't be shared.", Toast.LENGTH_SHORT).show()
            return
        }

        fileUri?.let {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, it)
                type = "image/png"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(shareIntent, "Share QR Code via"))
        }
    }
}


