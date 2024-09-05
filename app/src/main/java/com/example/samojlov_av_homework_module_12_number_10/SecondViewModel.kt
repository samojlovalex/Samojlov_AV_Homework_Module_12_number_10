package com.example.samojlov_av_homework_module_12_number_10


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecondViewModel: ViewModel() {
    //TODO SecondActivity
    var person: Person? = null

    val currentPerson: MutableLiveData<Person?> by lazy { MutableLiveData<Person?>() }
}