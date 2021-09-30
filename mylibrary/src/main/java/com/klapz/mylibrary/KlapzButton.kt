package com.klapz.mylibrary

import android.R.attr.button
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.klapz.mylibrary.api.KlapzConfig
import com.klapz.mylibrary.api.Urls
import com.lamudi.phonefield.PhoneInputLayout
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*


class KlapzButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {
    lateinit var Phoneedit: PhoneInputLayout;

    lateinit var klapznext :TextView
    lateinit  var klapzlogin :TextView
    lateinit var klapzmain:TextView
    lateinit var user_detail_layout:LinearLayout
    lateinit var otpfinal  :LinearLayout
    lateinit var login  :LinearLayout
    lateinit var thncyou  :LinearLayout
    lateinit var plain_text_input : EditText
     var klapzimnage: ImageView? = null
              var titlecontect :TextView
              var klapcounte :EditText
              var expression:EditText
              var klapzDownload:TextView
              var klapzDownloaderror:TextView
              lateinit var errorpopup :LinearLayout
              lateinit var titlemain : TextView
                lateinit var prefretreffres:LinearLayout
    var token = ""
    var title = ""
    var klapz = 0;
    var createrID = "";
    var Url = "";
    var KlapEnvirment = ""
    var key = ""
    var apiurl = ""
    var Mode = ""
    var preferKlapz = ""
    lateinit var bottomSheetDialog: BottomSheetDialog
    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.activity_main, this, true)

        bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottomsheet)
        val pref = context.getSharedPreferences("MyPref", 0)
        try{
            klapzimnage =  findViewById(R.id.klapz)
        }catch (e: NullPointerException){

        }

         klapznext = bottomSheetDialog.findViewById<TextView>(R.id.klapznext)!!
         klapzlogin = bottomSheetDialog.findViewById<TextView>(R.id.klapzlogin)!!
        try{
            klapzmain = bottomSheetDialog.findViewById<TextView>(com.klapz.mylibrary.R.id.klapz)!!
        }catch (e: NullPointerException){

        }
         user_detail_layout = bottomSheetDialog.findViewById<LinearLayout>(R.id.user_detail_layout)!!
         otpfinal = bottomSheetDialog.findViewById<LinearLayout>(R.id.otpfinal)!!
         login = bottomSheetDialog.findViewById<LinearLayout>(R.id.login)!!
         thncyou = bottomSheetDialog.findViewById<LinearLayout>(R.id.thncyou)!!
         Phoneedit = bottomSheetDialog.findViewById<PhoneInputLayout>(R.id.edit_text)!!
         plain_text_input = bottomSheetDialog.findViewById<EditText>(R.id.plain_text_input)!!
         titlecontect  = bottomSheetDialog.findViewById<EditText>(R.id.title)!!
         klapcounte = bottomSheetDialog.findViewById<EditText>(R.id.klapcount)!!
         expression = bottomSheetDialog.findViewById<EditText>(R.id.exprestion)!!
         klapzDownload = bottomSheetDialog.findViewById<EditText>(R.id.klapzDownload)!!
        klapzDownloaderror = bottomSheetDialog.findViewById<EditText>(R.id.klapzDownloaderror)!!

        errorpopup = bottomSheetDialog.findViewById<LinearLayout>(R.id.errorpopup)!!
        titlemain = bottomSheetDialog.findViewById<TextView>(R.id.titlemain)!!
        prefretreffres = bottomSheetDialog.findViewById<LinearLayout>(R.id.prefretreffres)!!

//        Phoneedit.ont
        Phoneedit!!.setHint(R.string.phone)
        Phoneedit!!.setDefaultCountry("IN")



        klapzDownload.setOnClickListener {
            val isAppInstalled = appInstalledOrNot("com.klapz.customer")

            if (isAppInstalled) {
                //This intent will help you to launch if the package is already installed
                val LaunchIntent: Intent? = context.getPackageManager()
                        .getLaunchIntentForPackage("com.klapz.customer")
                context.startActivity(LaunchIntent)
            } else {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.klapz.customer")))
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.klapz.customer")))
                }
            }
        }

        if (klapznext != null) {
            klapznext.setOnClickListener {
                Login()
            }
        }

        if (klapzlogin != null) {
            klapzlogin!!.setOnClickListener {
                OTP()
//                otpfinal!!.visibility = View.GONE
//                user_detail_layout!!.visibility = View.VISIBLE
            }
        }

        klapcounte.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    Log.e("Klapz Sdk Error", s.toString())
                    klapz = s.toString().toInt()
                    klapzmain.setText("Give:- " + klapcounte.text.toString() + " Klapz")
                    klapzmain.invalidate()
                } catch (e: Exception) {
                    Log.e("Klapz Sdk Error", e.toString())
                    Toast.makeText(context, "Please enter Valid Klapz", Toast.LENGTH_LONG)
                            .show()
                }
            }
        })

        if (klapzlogin != null) {
            klapzmain!!.setOnClickListener {
                KlapzGive()

            }
        }
        if (klapzimnage != null) {
            klapzimnage!!.setOnClickListener {
                ShowKlap()
            }
        }

        if(pref.getString("token", "test")!="test"){
            token = pref.getString("token", "test").toString()
            otpfinal!!.visibility = View.GONE
            login!!.visibility = View.GONE
            user_detail_layout!!.visibility = View.VISIBLE
        }        
    }


    fun ShowKlap(){
        try{
            val pref = context.getSharedPreferences("MyPref", 0)
            Log.e("okern", pref.getString("Klapztoken", "test").toString())
            if(pref.getString("Klapztoken", "test")!="test"){
                token = pref.getString("Klapztoken", "test").toString()
                otpfinal!!.visibility = View.GONE
                login!!.visibility = View.GONE
                thncyou.visibility =View.GONE
                user_detail_layout!!.visibility = View.VISIBLE
            }else{
                otpfinal!!.visibility = View.GONE
                login!!.visibility = View.VISIBLE
                thncyou.visibility =View.GONE
                user_detail_layout!!.visibility = View.GONE
            }
            errorpopup.visibility =View.GONE
            var DataForKlapz = JSONObject(pref.getString("KlapConfig", "{}"))
            title = DataForKlapz.getString("title")
            titlemain.setText(title)
            titlecontect.setText(title)
            klapz = DataForKlapz.getInt("klapz");
            klapcounte.setText(DataForKlapz.getInt("klapz").toString())
            createrID = DataForKlapz.getString("createrID");
            Url = DataForKlapz.getString("Url");
            key = pref.getString("Klapzkey", "xxx").toString()
            KlapEnvirment = pref.getString("KlapEnvirment", "SendBox").toString()
            Mode = DataForKlapz.getString("Mode")

            var prefferarray = DataForKlapz.getString("PreferKlapz").split(",");

            val btn = arrayOfNulls<Button>(3)
            prefretreffres.removeAllViews()
            for (i in 0..prefferarray.size) {
                if(i<=2){
                    btn[i] = Button(context)
                    btn[i]!!.id = i
                    btn[i]!!.text = prefferarray[i]
                    val buttonLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

                    buttonLayoutParams.width = 150
                    buttonLayoutParams.height = 150
                    buttonLayoutParams.setMargins(5, 0, 5, 0)
                    btn[i]!!.setLayoutParams(buttonLayoutParams)
                    btn[i]!!.width = 25
                    btn[i]!!.height = 50
                    btn[i]!!.setTextColor(Color.parseColor("#FFFFFF"))
                    btn[i]!!.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button))
                    prefretreffres.addView(btn[i])
                    btn[i]!!.setOnClickListener {
                        klapcounte.setText(prefferarray[i].toString())
                        klapzmain.setText("Give:- " + prefferarray[i] + " Klapz")
                        //your desired functionality
                    }
                }
            }

            if(KlapEnvirment =="Production"){
                apiurl = Urls.apiurlPROD
            }else{
                apiurl = Urls.apiurl
            }
            if(token.isNullOrBlank() || token =="test"){
                bottomSheetDialog.show()
            }else{
                if(Mode == "Direct"){
                    KlapzGive()
                }else{
                    bottomSheetDialog.show()
                }
            }

        }catch (e: Exception){
            Log.e("Klapz Sdk Error", e.toString())
            Toast.makeText(context, "Error in initialization klapz sdk ,Check log for more info", Toast.LENGTH_LONG)
                    .show()
        }


    }

    private fun showBottomSheetDialog(title: String, Klapz: Int, Key: String) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottomsheet)
        bottomSheetDialog.show()
    }
    fun Login(){
        Log.e("numbert", Phoneedit.phoneNumber)
        if(Phoneedit.phoneNumber.isNullOrBlank()){
            Toast.makeText(context, "Enter Valid number", Toast.LENGTH_LONG)
                .show()
            return
        }

        var stringphone = Phoneedit.phoneNumber
        val obj = JSONObject()
        val objinner = JSONObject()
        objinner.put("mobile", stringphone)
        obj.put("user", objinner)
        Log.e("login", obj.toString())
        Log.e("url", apiurl + "auth/request_mobile_otp?apiKey=" + key + "&apiFrom=" + Urls.apiFrom + "&buildNumber=" + Urls.buildNumber)
        val jsObjRequest =
            object : JsonObjectRequest(
                    Request.Method.POST,
                    apiurl + "auth/request_mobile_otp?apiFrom=" + Urls.apiFrom + "&buildNumber=" + Urls.buildNumber,
                    obj,
                    Response.Listener<JSONObject?> { response ->
                        Log.e("responce", response.toString())
                        if (response != null) {
                            login!!.visibility = View.GONE
                            otpfinal!!.visibility = View.VISIBLE
                        }

                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(context, "Error in request", Toast.LENGTH_LONG)
                                .show()
                        Log.e("error", error.toString())
                    }
            ) {

                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    return headers
                }
            }
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsObjRequest)
    }

    fun OTP(){
        if(plain_text_input.text.length !=4 ){
            Toast.makeText(context, "Enter Valid OTP", Toast.LENGTH_LONG)
                .show()
            return
        }

        val obj = JSONObject()
        val objinner = JSONObject()
        var stringphone = Phoneedit.phoneNumber
        objinner.put("mobile", stringphone)
        objinner.put("otp", plain_text_input.text)

        obj.put("user", objinner)

        Log.e("numbert", obj.toString())
        Log.e(
                "numbert", apiurl + "auth/verify_mobile_otp.json?apiKey=" + key + "&apiFrom=" + Urls.apiFrom + "&buildNumber=" + Urls.buildNumber
        )
        val jsObjRequest =
            object : JsonObjectRequest(
                    Request.Method.POST,
                    apiurl + "auth/verify_mobile_otp.json?apiFrom=" + Urls.apiFrom + "&buildNumber=" + Urls.buildNumber,
                    obj,
                    Response.Listener<JSONObject?> { response ->
                        Log.e("responce", response.toString())
                        if (response != null) {
                            val pref = context.getSharedPreferences("MyPref", 0)
                            val editor: SharedPreferences.Editor = pref.edit()
                            editor.putString("Klapztoken", token);
                            editor.apply()
                            editor.commit()
                            if (Mode == "Direct") {
                                KlapzGive()
                                bottomSheetDialog.dismiss()
                            } else {
                                otpfinal!!.visibility = View.GONE
                                user_detail_layout!!.visibility = View.VISIBLE
                            }

                        }

                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(context, "Error in request", Toast.LENGTH_LONG)
                                .show()
                        Log.e("error", error.toString())
                    }
            ) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Content-Type", "application/json")
                    return headers
                }
                override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject>? {

                    try{
                        token = response?.headers?.get("auth-token").toString()
                        Log.e("token", token)
                        val jsonString = String(
                                response?.data ?: ByteArray(0),
                                Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
                        return Response.success(
                                JSONObject(jsonString),
                                HttpHeaderParser.parseCacheHeaders(response)
                        )
                    } catch (e: UnsupportedEncodingException) {
                        return Response.error(ParseError(e));
                    } catch (je: JSONException) {
                        return Response.error(ParseError(je));
                    }
                }
            }
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsObjRequest)
    }

    fun KlapzGive(){
        Log.e("numbert", Phoneedit.phoneNumber)
        if(klapz <= 0){
            Toast.makeText(context, "Enter Valid klapz", Toast.LENGTH_LONG)
                    .show()
            return
        }

        val obj = JSONObject()
        val objinner = JSONObject()
        objinner.put("count", klapz)
        objinner.put("contentURL", Url)
        objinner.put("public", true)
        objinner.put("Key", key)
        objinner.put("fromWhere", "App")
        objinner.put("createrID", createrID)
        objinner.put("expression", expression.text)

        obj.put("claps", objinner)
        Log.e("url", apiurl + "claps/expend?apiFrom=" + Urls.apiFrom + "&buildNumber=" + Urls.buildNumber)
        Log.e("main", obj.toString())
        Log.e("main", token)

        val jsObjRequest =
                object : JsonObjectRequest(
                        Request.Method.POST,
                        apiurl + "claps/expend?apiKey=" + key + "&apiFrom=" + Urls.apiFrom + "&buildNumber=" + Urls.buildNumber,
                        obj,
                        Response.Listener<JSONObject?> { response ->
                            Log.e("responce", response.toString())
                            if (response != null) {
                                user_detail_layout!!.visibility = View.GONE
                                thncyou!!.visibility = View.VISIBLE
                            }

                        },
                        Response.ErrorListener { error ->
//                            Toast.makeText(context, "Error in request", Toast.LENGTH_LONG)
//                                    .show()
                            Log.e("error", error.toString())
                            val response = error.networkResponse
                            if (error is ServerError && response != null) {
                                try {
                                    val res = String(
                                            response?.data ?: ByteArray(0),
                                            Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
                                    // Now you can use any deserializer to make sense of data
                                    val obj = JSONObject(res)
                                    Log.e("error", obj.toString())

                                    if (obj.getString("errorCode") == "Z1000") {
                                        user_detail_layout.visibility = View.GONE
                                        errorpopup.visibility = View.VISIBLE
                                        klapzDownloaderror.setText(obj.getString("errorMessage"))

                                        if (Mode == "Direct") {
//                                            Toast.makeText(context, obj.getString("errorMessage"), Toast.LENGTH_LONG)
//                                                    .show()
                                            bottomSheetDialog.show()
                                        }
                                    } else {
                                        Toast.makeText(context, obj.getString("errorMessage"), Toast.LENGTH_LONG)
                                                .show()
                                    }
                                    if (obj.getString("errorCode") == "Z1001") {
                                        var kalpzc = KlapzConfig();
                                        kalpzc.Close(context)
                                        bottomSheetDialog.dismiss()
                                    }
                                } catch (e1: UnsupportedEncodingException) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace()
                                } catch (e2: JSONException) {
                                    // returned data is not JSONObject?
                                    e2.printStackTrace()
                                } catch (e2: java.lang.Exception) {
                                    // returned data is not JSONObject?
                                    e2.printStackTrace()
                                }
                            }
                        }
                ) {
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["auth-token"] = token
                        return headers
                    }
                }
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsObjRequest)
    }

    private fun appInstalledOrNot(uri: String): Boolean {
        val pm: PackageManager = context.getPackageManager()
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return false
    }
}