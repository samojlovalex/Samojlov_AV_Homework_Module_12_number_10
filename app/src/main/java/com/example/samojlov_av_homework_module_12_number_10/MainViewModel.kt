package com.example.samojlov_av_homework_module_12_number_10

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    //TODO MainActivity
    var name: String = ""
    var surname: String = ""
    var photo: Uri? = null
    var day: String = ""
    var month: String = ""
    var year: String = ""

    val currentPhoto: MutableLiveData<Uri?> by lazy { MutableLiveData<Uri?>() }
}