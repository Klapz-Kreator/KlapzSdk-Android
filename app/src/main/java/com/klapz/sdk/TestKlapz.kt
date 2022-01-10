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
        kalpz.Start("8r0qn8tky8j3we8", this, "Production")


        val obj = JSONObject()

        //Direct //Default
        obj.put("Mode", "Default")
        obj.put("appid", "1001")

        obj.put("klapz", 2)
        obj.put("expressionPlaceholder", "Tell us, what do you love about this book summary")
        obj.put("PreferKlapz", "10,20,30")
        obj.put("ThankText", "Thanks for Klapping.")
        obj.put("Url", "")
        // OR
        obj.put("title", "Klapz this content ritesh")
        obj.put("description", "description")
        obj.put("tags", "")
        obj.put("ContentType", "booklet") // as it is
        obj.put("contentId", "ritesh_light") // Chapter Id
        obj.put("creatorId", "1") // Value is always "1"
        obj.put("creatorName", "Amruth Deshmukh") // Amruth Deshmukh
        obj.put("creatorScreenName", "Amruth Deshmukh") // Amruth Deshmukh


//        var callBackPayload = JSONObject()
//        callBackPayload.put("userid","userID")
//        obj.put("callBackPayload", callBackPayload)


        kalpz.Config(obj, this)

//        var klapzButton = KlapzButton(this)

        klapzbutton.setKlapzSucessListener(object : KlapzButton.KlapzSucessListener {
            override fun onKlapzSucess(KlapzObject: JSONObject?) {
                Log.e("Log kalpz", KlapzObject.toString())
                response.text = KlapzObject.toString()
            }
        })

//        klapzButton.ShowKlap()

        // If user Logout use this funtion
//         kalpz.Close(this)

    }
}

