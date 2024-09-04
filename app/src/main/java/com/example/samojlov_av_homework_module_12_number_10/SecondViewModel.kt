package com.example.samojlov_av_homework_module_12_number_10

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class SecondViewModel: ViewModel() {
    var person = Person()
    var nameAndSurname = ""
    var birthday = ""
    var daysBeforeTheBirthday = ""

    val currentPhotoSecond: MutableLiveData<Uri?> by lazy { MutableLiveData<Uri?>() }
}