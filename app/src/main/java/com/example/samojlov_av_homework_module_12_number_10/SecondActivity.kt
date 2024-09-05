package com.example.samojlov_av_homework_module_12_number_10

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.samojlov_av_homework_module_12_number_10.databinding.ActivitySecondBinding
import com.google.gson.Gson
import java.time.LocalDate
import java.time.Period
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var secondViewModel: SecondViewModel
    private var person: Person? = null
    private var photo: Uri? = null
    private var nameAndSurname: String? = null
    private var age: String? = null
    private var daysBeforeTheBirthday: String? = null

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

        saveData()

        gettingData()

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

        secondViewModel = ViewModelProvider(this)[SecondViewModel::class.java]
    }

    private fun printData() {

        nameAndSurname = getString(
            R.string.nameAndSurnamePrint_Second,
            person?.name!!,
            person?.surname!!
        )

        val birthdayTime = LocalDate.of(
            person?.year!!,
            person?.month!!,
            person?.day!!
        )

        age =
            getString(
                R.string.birthdayPrint_Second,
                age(birthdayTime)
            )

        daysBeforeTheBirthday = getString(
            R.string.daysBeforeTheBirthday,
            daysBeforeTheBirthday(birthdayTime)
        )

        if (person?.image != "null") {
            photo = person?.image!!.toUri()
            photoCircleImageViewSecondCIV.setImageURI(photo)
        } else photoCircleImageViewSecondCIV.setImageResource(R.drawable.empty_photo)

        nameAndSurnameTextVieWSecondTW.text = nameAndSurname
        birthdayTextVieWSecondTW.text = age
        daysBeforeTheBirthdayTextVieWSecondTW.text = daysBeforeTheBirthday
    }

    private fun saveData() {
        secondViewModel.currentPerson.observe(this) {
            person = it
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun daysBeforeTheBirthday(birthday: LocalDate): String {

        val thisData = LocalDate.now()
        val birthdayLocalDate =
            LocalDate.of(LocalDate.now().year, birthday.month, birthday.dayOfMonth)
        val birthdayLocalDatePlus =
            LocalDate.of(LocalDate.now().year + 1, birthday.month, birthday.dayOfMonth)
        val period = Period.between(thisData, birthdayLocalDate)
        val periodPlus = Period.between(thisData, birthdayLocalDatePlus)
        val day: Int
        val month: Int
        val year: Int

        if ((birthday.dayOfYear - LocalDate.now().dayOfYear) < 0) {
            day = periodPlus.days
            month = periodPlus.months
            year = periodPlus.years
        } else {
            day = period.days
            month = period.months
            year = period.years
        }

        val dayString = getString(R.string.periodDay, day)
        val monthString = getString(R.string.periodMonth, month)
        val yearString = getString(R.string.periodYear, year)

        val periodList = mutableListOf<String>()
        val timeMap = mapOf(
            year to yearString,
            month to monthString,
            day to dayString
        )

        for (i in timeMap) {
            if (i.key != 0) periodList.add(i.value)
        }

        val result = periodList.joinToString(separator = " ")
        return result
    }

    @SuppressLint("StringFormatMatches")
    private fun age(birthday: LocalDate): String {
        val thisData = LocalDate.now()
        val period = Period.between(birthday, thisData)

        val day = period.days
        val month = period.months
        val year = period.years

        val dayString = getString(R.string.periodDay, day)
        val monthString = getString(R.string.periodMonth, month)
        val yearString = getString(R.string.ageYear, year)

        val periodList = mutableListOf<String>()
        val timeMap = mapOf(
            year to yearString,
            month to monthString,
            day to dayString
        )

        for (i in timeMap) {
            if (i.key != 0) periodList.add(i.value)
        }

        val result = periodList.joinToString(separator = " ")
        return result
    }

    private fun gettingData() {
        val type = typeOf<Person?>().javaType
        val personString: String? = intent.getStringExtra("person")
        person = Gson().fromJson(personString, type)
        secondViewModel.currentPerson.value =
            (person.also { secondViewModel.person = it })
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