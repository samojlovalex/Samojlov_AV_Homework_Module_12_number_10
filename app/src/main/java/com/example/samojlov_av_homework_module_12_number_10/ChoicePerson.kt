package com.example.samojlov_av_homework_module_12_number_10

import android.annotation.SuppressLint
import android.content.Context
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ChoicePerson {


    @SuppressLint("StringFormatMatches")
    fun choice(
        context: Context,
        name: String,
        surname: String,
        day: String,
        month: String,
        year: String
    ): String {
        val date = LocalDate.now()
        var choiceOut = ""
        while (choiceOut != "0") {

            if (name.isEmpty() || surname.isEmpty() || day.isEmpty() || month.isEmpty() || year.isEmpty()){
                choiceOut = context.getString(R.string.choiceEmpty)
                break
            }

            if (name.length < 2) {
                choiceOut = context.getString(R.string.name_length_limit)
                break
            }

            if (surname.length < 2) {
                choiceOut = context.getString(R.string.surname_length_limit)
                break
            }
            val choiceYearToInt = year.toIntOrNull() != null

            if (!choiceYearToInt) {
                choiceOut = context.getString(R.string.checkYearToInt)
                break
            }

            if (year.toInt() < 0){
                choiceOut = context.getString(R.string.checkYearToInt)
                break
            }

            val choiceMonthToInt = month.toIntOrNull() != null

            if (!choiceMonthToInt) {
                choiceOut = R.string.checkMonthToInt.toString()
                break
            }

            if (month.toInt() !in 1..12){
                choiceOut = context.getString(R.string.checkMonth)
                break
            }

            val choiceDayToInt = day.toIntOrNull() != null

            if (!choiceDayToInt) {
                choiceOut = context.getString(R.string.checkDayToInt)
                break

            }

            if (year.toInt() > date.year) {
                choiceOut = context.getString(R.string.choiceLastYear, date.year)
                break
            }


            val checkThisMonth = year.toInt() == date.year && month.toInt() > date.month.value
            val formatMonth = DateTimeFormatter.ofPattern("MM.yyyy")

            if (checkThisMonth){
                choiceOut = context.getString(R.string.choiceLastMonth, date.format(formatMonth))
                break
            }

            val checkThisDay = year.toInt() == date.year && month.toInt() == date.month.value && day.toInt() > date.dayOfMonth
            val formatDay = DateTimeFormatter.ofPattern("dd.MM.yyyy")

            if (checkThisDay){
                choiceOut = context.getString(R.string.choiceLastDay, date.format(formatDay))
                break
            }

            val isLeapYear = LocalDate.of(year.toInt(),1,1).isLeapYear

            if (!isLeapYear) {
                if (month.toInt() == 2) {
                    if (day.toInt() !in 1..28) {
                        choiceOut = context.getString(R.string.checkFebruaryIsALeapYear)
                        break
                    }
                }
            } else if (month.toInt() == 2) {
                if (day.toInt() !in 1..29) {
                    choiceOut = context.getString(R.string.checkFebruaryIsNotALeapYear)
                    break
                }
            }

            when(month.toInt()){
                1,3,5,7,8,10,12 -> if (day.toInt() !in 1..31) {
                    choiceOut = context.getString(R.string.checkALongMonth)
                    break
                }
                4,6,9,11 -> if (day.toInt() !in 1..30){
                    choiceOut = context.getString(R.string.checkAShortMonth)
                    break
                }

            }

            choiceOut = "0"
        }
        return choiceOut
    }
}