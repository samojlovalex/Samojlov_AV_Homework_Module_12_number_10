package com.example.samojlov_av_homework_module_12_number_10

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class CurrentViewModel : ViewModel() {
    //TODO MainActivity
    var name: String = ""
    var surname: String = ""
    var photo: Bitmap? = null
    var day: String = ""
    var month: String = ""
    var year: String = ""

    val currentPhoto: MutableLiveData<Bitmap?> by lazy { MutableLiveData<Bitmap?>() }

    //TODO SecondActivity
    var person = Person()
    var nameAndSurname = ""
    var birthday = ""
    var photoString = "null"
    var photoSecond: Bitmap? = null
    var birthdayTime: LocalDate = LocalDate.now()
    var daysBeforeTheBirthday = ""

    val currentPhotoSecond: MutableLiveData<Bitmap?> by lazy { MutableLiveData<Bitmap?>() }
}