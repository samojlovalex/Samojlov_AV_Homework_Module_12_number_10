package com.example.samojlov_av_homework_module_12_number_10

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.samojlov_av_homework_module_12_number_10.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var photoCircleImageViewMainCIV: ImageView
    private lateinit var nameEditTextMainET: EditText
    private lateinit var surnameEditTextMainET: EditText
    private lateinit var dayMainEditTextET: EditText
    private lateinit var monthMainEditTextET: EditText
    private lateinit var yearMainEditTextET: EditText
    private lateinit var saveButtonBT: Button

    private val GALLERY_REQUEST = 25

    //    private var photo: Uri? = null
    private var photo: Uri? = null
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    @SuppressLint("ResourceType")
    private fun init() {

        photoCircleImageViewMainCIV = binding.photoCircleImageViewMainCIV
        nameEditTextMainET = binding.nameEditTextMainET
        surnameEditTextMainET = binding.surnameEditTextMainET
        dayMainEditTextET = binding.dayMainEditTextET
        monthMainEditTextET = binding.monthMainEditTextET
        yearMainEditTextET = binding.yearMainEditTextET
        saveButtonBT = binding.saveButtonBT

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        photoCircleImageViewMainCIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveCurrentData()

        saveButtonBT.setOnClickListener {
            savePerson()
        }

    }

    private fun saveCurrentData() {
        mainViewModel.name = nameEditTextMainET.text.toString()
        mainViewModel.surname = surnameEditTextMainET.text.toString()
        mainViewModel.day = dayMainEditTextET.text.toString()
        mainViewModel.month = monthMainEditTextET.text.toString()
        mainViewModel.year = yearMainEditTextET.text.toString()

        mainViewModel.currentPhoto.observe(this) {
            photo = it
            photoCircleImageViewMainCIV.setImageURI(it)
        }
    }

    private fun savePerson() {

        saveCurrentData()

        val choicePerson = ChoicePerson()
        val name = mainViewModel.name
        val surname = mainViewModel.surname
        val day = mainViewModel.day
        val month = mainViewModel.month
        val year = mainViewModel.year
        val image = mainViewModel.photo

        val choice = choicePerson.choice(this, name, surname, day, month, year)

        if (choice == "0") {

            val person = Person()
            person.name = name
            person.surname = surname
            person.day = day.toInt()
            person.month = month.toInt()
            person.year = year.toInt()
            person.image = image.toString()

            val type = typeOf<Person>().javaType
            val gson = Gson().toJson(person, type)

            Toast.makeText(this, "${person.name} ${person.surname}", Toast.LENGTH_LONG).show()


            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("person", gson)
            startActivity(intent)
            finish()

        } else {
            val snackbar =
                Snackbar.make(
                    saveButtonBT,
                    choice,
                    Snackbar.LENGTH_LONG
                )
            val snackbarView =
                snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            snackbarView.setMaxLines(10)
            snackbarView.setBackgroundColor(getColor(R.color.Blue))
            snackbarView.setTextColor(getColor(R.color.white))
            snackbarView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.error_image,
                0,
                0,
                0
            )
            snackbar.show()

        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoCircleImageViewMainCIV = binding.photoCircleImageViewMainCIV
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == RESULT_OK) {
                photo = data?.data
                photoCircleImageViewMainCIV.setImageURI(photo)
                mainViewModel.currentPhoto.value =
                    (photo.also { mainViewModel.photo = it })
            }
        }
    }
}