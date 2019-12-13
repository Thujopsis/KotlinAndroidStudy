package com.example.kotlinandroidstudy

import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.prefs.PreferenceChangeEvent

class MainActivity : AppCompatActivity() {
    var sheepCount = 0
    var mp : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var greeting = "こんにちは"
        textview.text = greeting

        val trialTime = Date()
        val calendar = GregorianCalendar()
        calendar.time = trialTime

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        if(hour in 1..11){
            greeting = "おはよう"
        }else if(15 < hour){
            greeting = "こんばんは"
        }

        greeting += "ねむれませんか？"

        textview.text = greeting

        mp = MediaPlayer.create(applicationContext, R.raw.sheep_cry1)

        rootLayout.setOnClickListener {
            sheepCount++
            val sheepText = "ひつじが$sheepCount 匹"
            textview.text = sheepText

            when(sheepCount %2){
                0 ->{
                    imageView1.setImageResource(R.drawable.sheep_1)
                    mp?.start()
                }
                else -> imageView1.setImageResource(R.drawable.sheep_2)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("onPause","眠るまでの回数=" + sheepCount)
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.edit().putInt("SheepCount",sheepCount).apply()
    }

    override fun onResume() {
        super.onResume()
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val count = sp.getInt("SheepCount",-1)
        if(count >= 0){
            textview.text = "前回は $sheepCount 回羊を数えました。"
        }
    }
}
