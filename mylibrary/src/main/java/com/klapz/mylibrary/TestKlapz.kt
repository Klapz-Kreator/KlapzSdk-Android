package com.klapz.mylibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klapz.mylibrary.api.KlapzConfig

import org.json.JSONObject

class TestKlapz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_klapz)

        var  kalpz = KlapzConfig();


//        var klapzButton = KlapzButton(this)
//        klapzButton.ShowKlap()

        val obj = JSONObject()
        obj.put("title", "Klapz this content")
        obj.put("klapz", 2)
        obj.put("createrID", "createrID")
        obj.put("Url", "https://www.youtube.com/watch?v=LnNS_Gb4Mhk")
        obj.put("PreferKlapz", "10,20,30")

        //Direct //Default
        obj.put("Mode", "Default")

        //SendBox /Production
        kalpz.Start("XXXXXXXX",this,"SendBox")
        kalpz.Config(obj,this)

        // If user Logout use this funtion
//         kalpz.Close(this)

    }
}

