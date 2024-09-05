package com.example.samojlov_av_homework_module_12_number_10

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    //TODO MainActivity
    var name: String? = null
    var surname: String? = null
    var photo: Uri? = null
    var day: String? = null
    var month: String? = null
    var year: String? = null

    val currentName: MutableLiveData<String?> by lazy { MutableLiveData<String?>() }
    val currentSurname: MutableLiveData<String?> by lazy { MutableLiveData<String?>() }
    val currentDay: MutableLiveData<String?> by lazy { MutableLiveData<String?>() }
    val currentMonth: MutableLiveData<String?> by lazy { MutableLiveData<String?>() }
    val currentYear: MutableLiveData<String?> by lazy { MutableLiveData<String?>() }
    val currentPhoto: MutableLiveData<Uri?> by lazy { MutableLiveData<Uri?>() }
}