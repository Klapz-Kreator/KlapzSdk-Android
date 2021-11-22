package com.klapz.sdk

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.klapz.mylibrary.api.KlapzConfig
import kotlinx.android.synthetic.main.activity_test_klapz.*
import org.json.JSONObject

class TestKlapz : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_klapz)

        var  kalpz = KlapzConfig();
        //SendBox /Production
        kalpz.Start("kuaduekwamk1ah", this, "SandBox")


        val obj = JSONObject()
        obj.put("title", "Summary Title")
        obj.put("klapz", 2)
        obj.put("createrID", "Your creator id")
        obj.put("Url", "")
        obj.put("PreferKlapz", "10,20,30")
        obj.put("ThankText", "Thanks for Klapping.")
        obj.put("ContentType", "summary")
        obj.put("expressionPlaceholder", "Tell us, what do you love about this book summary")
        //Direct //Default
        obj.put("Mode", "Default")
        obj.put("appId", "1001")
        obj.put("contentId","contentId")
//        var callBackPayload = JSONObject()
//        callBackPayload.put("userid","userID")
//        obj.put("callBackPayload", callBackPayload)


        kalpz.Config(obj, this)

//        var klapzButton = KlapzButton(this)

        klapzbutton.setKlapzSucessListener(object : KlapzButton.KlapzSucessListener {
            override fun onKlapzSucess(KlapzObject: JSONObject?) {
               Log.e("Log kalpz",KlapzObject.toString())
                response.text = KlapzObject.toString()
            }
        })

//        klapzButton.ShowKlap()

        // If user Logout use this funtion
//         kalpz.Close(this)

    }
}

