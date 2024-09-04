package com.example.samojlov_av_homework_module_12_number_10

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.samojlov_av_homework_module_12_number_10.databinding.ActivitySecondBinding
import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var currentViewModel: CurrentViewModel
    private var person: Person? = null
    private var photo: Bitmap? = null

    private lateinit var toolbarSecond: androidx.appcompat.widget.Toolbar
    private lateinit var photoCircleImageViewSecondCIV: ImageView
    private lateinit var nameAndSurnameTextVieWSecondTW: TextView
    private lateinit var birthdayTextVieWSecondTW: TextView
    private lateinit var daysBeforeTheBirthdayTextVieWSecondTW: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        gettingData()

        currentData()

        printData()
    }

    @SuppressLint("StringFormatMatches")
    private fun init() {
        toolbarSecond = binding.toolbarSecond
        setSupportActionBar(toolbarSecond)
        title = getString(R.string.titleToolbarsecond)
        toolbarSecond.setLogo(R.drawable.happybirthday_60)

        photoCircleImageViewSecondCIV = binding.photoCircleImageViewSecondCIV
        nameAndSurnameTextVieWSecondTW = binding.nameAndSurnameTextVieWSecondTW
        birthdayTextVieWSecondTW = binding.birthdayTextVieWSecondTW
        daysBeforeTheBirthdayTextVieWSecondTW = binding.daysBeforeTheBirthdayTextVieWSecondTW

        currentViewModel = ViewModelProvider(this)[CurrentViewModel::class.java]
    }

    private fun printData() {
        nameAndSurnameTextVieWSecondTW.text = currentViewModel.nameAndSurname
        birthdayTextVieWSecondTW.text = currentViewModel.birthday
        daysBeforeTheBirthdayTextVieWSecondTW.text = currentViewModel.daysBeforeTheBirthday
    }

    private fun currentData() {
        currentViewModel.nameAndSurname =
            getString(
                R.string.nameAndSurnamePrint_Second,
                currentViewModel.person.name!!,
                currentViewModel.person.surname!!
            )

        currentViewModel.birthdayTime = LocalDate.of(
            currentViewModel.person.year!!,
            currentViewModel.person.month!!,
            currentViewModel.person.day!!
        )

        val formatTime = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        currentViewModel.birthday =
            getString(
                R.string.birthdayPrint_Second,
                currentViewModel.birthdayTime.format(formatTime)
            )

        currentViewModel.currentPhotoSecond.observe(this) {
            photo = it
            photoCircleImageViewSecondCIV.setImageBitmap(it)
        }

        photo = currentViewModel.person.image
        currentViewModel.currentPhotoSecond.value =
            (photo.also { currentViewModel.photoSecond = it })

        if (currentViewModel.person.image == null) photoCircleImageViewSecondCIV.setImageResource(
            R.drawable.empty_photo
        )

        currentViewModel.daysBeforeTheBirthday = getString(
            R.string.daysBeforeTheBirthday,
            daysBeforeTheBirthday(currentViewModel.birthdayTime)
        )
    }

    private fun daysBeforeTheBirthday(birthday: LocalDate): String {
        val result =
            if ((birthday.dayOfYear - LocalDate.now().dayOfYear) < 0) {
                birthday.dayOfYear + (LocalDate.of(
                    LocalDate.now().year,
                    12,
                    31
                ).dayOfYear - LocalDate.now().dayOfYear)
            } else birthday.dayOfYear - LocalDate.now().dayOfYear
        return result.toString()
    }

    private fun gettingData() {
        val type = typeOf<Person?>().javaType
        val personString: String? = intent.getStringExtra("person")
        person = Gson().fromJson(personString, type)

        currentViewModel.person = person!!
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("SetTextI18n", "ShowToast")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.toast_exit),
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}