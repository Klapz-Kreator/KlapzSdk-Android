package com.klapz.sdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klapz.mylibrary.api.KlapzConfig
import kotlinx.android.synthetic.main.activity_test_klapz.*

import org.json.JSONObject

class TestKlapz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_klapz)



        var  kalpz = KlapzConfig();

        //SendBox /Production
        kalpz.Start("XXXXXXXX",this,"SendBox")

        val obj = JSONObject()
        obj.put("title", "How to Win Friends and Influence People")
        obj.put("klapz", 2)
        obj.put("createrID", "createrID")
        obj.put("Url", "https://www.youtube.com/watch?v=LnNS_Gb4Mhk")
        obj.put("expressionPlaceholder", "Tell us, what do you love about this book summary")
        obj.put("PreferKlapz", "10,20,30")

        obj.put("ThankText", "Thanks for Klapping.")
        obj.put("ContentType", "summary")

        //Direct //Default
        obj.put("Mode", "Default")


        kalpz.Config(obj,this)


        logouts.setOnClickListener {
           // kalpz.Close(this)
        }

//        var klapzButton = KlapzButton(this)
//        klapzButton.ShowKlap()

        // If user Logout use this funtion
//         kalpz.Close(this)

    }
}

