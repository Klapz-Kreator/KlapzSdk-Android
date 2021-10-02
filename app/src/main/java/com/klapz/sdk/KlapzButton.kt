package com.klapz.sdk

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.klapz.mylibrary.api.Urls
import com.klapz.sdk.api.KlapzConfig
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class KlapzButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {
    lateinit var Phoneedit: EditText;

    lateinit var klapznext :TextView
    lateinit  var klapzlogin :TextView
    lateinit var klapzmain:TextView
    lateinit var user_detail_layout:LinearLayout
    lateinit var otpfinal  :LinearLayout
    lateinit var login  :LinearLayout
    lateinit var thncyou  :LinearLayout
    lateinit var plain_text_input : EditText
    lateinit var ccp :CountryCodePicker
     var klapzimnage: ImageView? = null
              var titlecontect :TextView
              var klapcounte :EditText
              var expression:EditText
              var klapzDownload:TextView
              var klapzDownloaderror:TextView
              lateinit var errorpopup :LinearLayout
              lateinit var titlemain : TextView
                lateinit var prefretreffres:LinearLayout
    lateinit var klapzDownload2:TextView
    lateinit var klapzDownloaderror2:TextView
            lateinit var errorpopup2 :LinearLayout
    lateinit var resendotp :TextView
    lateinit var errorphone:TextView
    lateinit var errorotp:TextView
    lateinit var thanxtext:TextView


    lateinit var wp:ImageView
    lateinit var fb:ImageView
    lateinit var tw:ImageView
    lateinit var share:ImageView



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
    var ContentType = "content"
    var shareResponse = JSONObject()

    lateinit var bottomSheetDialog: BottomSheetDialog
    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var contentView   = inflater.inflate(R.layout.activity_main, this, true)

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
         Phoneedit = bottomSheetDialog.findViewById<EditText>(R.id.phoneinput)!!
         plain_text_input = bottomSheetDialog.findViewById<EditText>(R.id.plain_text_input)!!
         titlecontect  = bottomSheetDialog.findViewById<EditText>(R.id.title)!!
         klapcounte = bottomSheetDialog.findViewById<EditText>(R.id.klapcount)!!
         expression = bottomSheetDialog.findViewById<EditText>(R.id.exprestion)!!
         klapzDownload = bottomSheetDialog.findViewById<EditText>(R.id.klapzDownload)!!
        klapzDownloaderror = bottomSheetDialog.findViewById<EditText>(R.id.klapzDownloaderror)!!
        ccp = bottomSheetDialog.findViewById<CountryCodePicker>(R.id.ccp)!!
        errorpopup = bottomSheetDialog.findViewById<LinearLayout>(R.id.errorpopup)!!

        klapzDownload2 = bottomSheetDialog.findViewById<EditText>(R.id.klapzDownload2)!!
        klapzDownloaderror2 = bottomSheetDialog.findViewById<EditText>(R.id.klapzDownloaderror2)!!
        errorpopup2 = bottomSheetDialog.findViewById<LinearLayout>(R.id.errorpopup2)!!

        wp = bottomSheetDialog.findViewById<ImageView>(R.id.wa)!!
        fb = bottomSheetDialog.findViewById<ImageView>(R.id.fb)!!
        tw = bottomSheetDialog.findViewById<ImageView>(R.id.tw)!!
        share = bottomSheetDialog.findViewById<ImageView>(R.id.share)!!

        titlemain = bottomSheetDialog.findViewById<TextView>(R.id.titlemain)!!
        errorotp = bottomSheetDialog.findViewById<TextView>(R.id.errorotp)!!
        errorphone = bottomSheetDialog.findViewById<TextView>(R.id.errorphone)!!
        prefretreffres = bottomSheetDialog.findViewById<LinearLayout>(R.id.prefretreffres)!!
        resendotp = bottomSheetDialog.findViewById<TextView>(R.id.resendotp)!!
        thanxtext = bottomSheetDialog.findViewById<TextView>(R.id.thanxtext)!!


//        Phoneedit.ont



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

        klapzDownload2.setOnClickListener {
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

        resendotp.setOnClickListener {
            Login()
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
                    klapzmain.setText("Give this ${ContentType}: " + klapcounte.text.toString() + " Klapz")
                    klapzmain.invalidate()
                } catch (e: Exception) {
                    Log.e("Klapz Sdk Error", e.toString())
                    Toast.makeText(context, "Please enter Valid Klapz", Toast.LENGTH_LONG)
                            .show()

                    var DataForKlapz = JSONObject(pref.getString("KlapConfig", "{}"))
                    klapz = DataForKlapz.getInt("klapz")
                    klapzmain.setText("Give this ${ContentType}: " + DataForKlapz.getInt("klapz").toString() + " Klapz")
                    klapzmain.invalidate()
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
        wp.setOnClickListener {
            Log.e("Share", shareResponse.toString())
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareResponse.getString("whatsapp"))
            try {
                context.startActivity(whatsappIntent)
            } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(context, "Whatsapp have not been installed.", Toast.LENGTH_LONG)
                .show()
            }
        }

        fb.setOnClickListener {
            Log.e("Share", shareResponse.toString())
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.facebook.katana")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareResponse.getString("facebook"))
            try {
                context.startActivity(whatsappIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(context, "Whatsapp have not been installed.", Toast.LENGTH_LONG)
                        .show()
            }
        }
        tw.setOnClickListener {
            Log.e("Share", shareResponse.toString())
            val url = "http://www.twitter.com/intent/tweet?url=YOURURL&text="+ shareResponse.getString("twitter")
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        }

        share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody = shareResponse.getString("twitter")
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT,shareResponse.getString("twitter"))
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            context.startActivity(Intent.createChooser(intent, "Share via"))
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
            errorpopup2.visibility = View.GONE
            errorotp.setText("")
            errorphone.setText("")
            var DataForKlapz = JSONObject(pref.getString("KlapConfig", "{}"))
            title = DataForKlapz.getString("title")
            titlemain.setText(title)
            titlecontect.setText(title)
            klapz = DataForKlapz.getInt("klapz");
            klapcounte.setText(DataForKlapz.getInt("klapz").toString())
            createrID = DataForKlapz.getString("createrID");

            if( DataForKlapz.has("ThankText")){
                thanxtext.setText(DataForKlapz.getString("ThankText").toString())
            }
            if(DataForKlapz.has("ContentType")){
                ContentType = DataForKlapz.getString("ContentType")
            }
            klapzmain.setText("Give this ${ContentType}: " + klapcounte.text.toString() + " Klapz")
            Url = DataForKlapz.getString("Url");
            key = pref.getString("Klapzkey", "xxx").toString()
            KlapEnvirment = pref.getString("KlapEnvirment", "SendBox").toString()

            if( DataForKlapz.has("expressionPlaceholder")){
                expression.setHint(DataForKlapz.getString("expressionPlaceholder").toString())
            }

            Mode = DataForKlapz.getString("Mode")

            var prefferarray = DataForKlapz.getString("PreferKlapz").split(",");
            prefretreffres.removeAllViews()
//            val face = Typeface.createFromAsset(context.getAssets(),
//                    "fonts/montserratbold.ttf")
            if(!DataForKlapz.getString("PreferKlapz").isNullOrBlank()){
                for (i in 0..prefferarray.size-1) {
                    val btn = arrayOfNulls<Button>(3)
                    if(i<=2){
                        Log.e("kalpz", prefferarray[i])
                        btn[i] = Button(context)
                        btn[i]!!.id = i
                        btn[i]!!.text = prefferarray[i]
                        val buttonLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                        buttonLayoutParams.width = 150
                        buttonLayoutParams.height = 150
                        buttonLayoutParams.setMargins(5, 0, 5, 0)
                        btn[i]!!.setLayoutParams(buttonLayoutParams)
                        btn[i]!!.width = 25
                        btn[i]!!.elevation = 0F
//                        btn[i]!!.setTypeface(face)
                        btn[i]!!.height = 50
                        btn[i]!!.setTextColor(Color.parseColor("#FFFFFF"))
                        btn[i]!!.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.preklapz))
                        prefretreffres.addView(btn[i])
                        btn[i]!!.setOnClickListener {
                            klapcounte.setText(prefferarray[i].toString())
                            klapzmain.setText("Give this ${ContentType}: " + prefferarray[i] + " Klapz")
                            //your desired functionality
                        }
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
        Log.e("numbert", ccp.selectedCountryCode.toString())

        if(Phoneedit.text.toString().isNullOrBlank() || Phoneedit.text.toString() == "1234567890"){
//            Toast.makeText(context, "Please enter a valid mobile number", Toast.LENGTH_LONG)
//                .show()
            errorphone.setText("Please enter a valid mobile number")
            return
        }

        var stringphone = "+"+ccp.selectedCountryCode+Phoneedit.text.toString()
        val obj = JSONObject()
        val objinner = JSONObject()
        objinner.put("mobile", stringphone)
        obj.put("user", objinner)
        Log.e("login", obj.toString())
        Log.e("url", apiurl + "auth/request_mobile_otp?apiKey=" + key + "&apiFrom=" + Urls.apiFrom + "&sdkNumber=" + Urls.sdkNumber + "&buildNumber=" + Urls.buildNumber)
        val jsObjRequest =
            object : JsonObjectRequest(
                    Request.Method.POST,
                    apiurl + "auth/request_mobile_otp?apiFrom=" + Urls.apiFrom + "&sdkNumber=" + Urls.sdkNumber + "&buildNumber=" + Urls.buildNumber,
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
//            Toast.makeText(context, "Please enter a valid Code.", Toast.LENGTH_LONG)
//                .show()
            errorotp.setText("Please enter a valid Code.")
            return
        }

        val obj = JSONObject()
        val objinner = JSONObject()
        var stringphone = "+"+ccp.selectedCountryCode+Phoneedit.text.toString()
        objinner.put("mobile", stringphone)
        objinner.put("otp", plain_text_input.text)

        obj.put("user", objinner)

        Log.e("numbert", obj.toString())
        Log.e(
                "numbert", apiurl + "auth/verify_mobile_otp.json?apiKey=" + key + "&apiFrom=" + Urls.apiFrom + "&sdkNumber=" + Urls.sdkNumber + "&buildNumber=" + Urls.buildNumber
        )
        val jsObjRequest =
            object : JsonObjectRequest(
                    Request.Method.POST,
                    apiurl + "auth/verify_mobile_otp.json?apiFrom=" + Urls.apiFrom + "&sdkNumber=" + Urls.sdkNumber + "&buildNumber=" + Urls.buildNumber,
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
                        Toast.makeText(context, "Error in Verify Mobile otp", Toast.LENGTH_LONG)
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
        Log.e("url", apiurl + "claps/expend?apiFrom=" + Urls.apiFrom + "&sdkNumber=" + Urls.sdkNumber + "&buildNumber=" + Urls.buildNumber)
        Log.e("main", obj.toString())
        Log.e("main", token)

        val jsObjRequest =
                object : JsonObjectRequest(
                        Request.Method.POST,
                        apiurl + "claps/expend?apiKey=" + key + "&apiFrom=" + Urls.apiFrom + "&sdkNumber=" + Urls.sdkNumber + "&buildNumber=" + Urls.buildNumber,
                        obj,
                        Response.Listener<JSONObject?> { response ->
                            Log.e("responce", response.toString())
                            if (response != null) {
                                user_detail_layout!!.visibility = View.GONE
                                thncyou!!.visibility = View.VISIBLE
                                shareResponse = response.getJSONObject("social")
                                if (Mode == "Direct") {
                                    Toast.makeText(context, thanxtext.text, Toast.LENGTH_LONG)
                                                    .show()
                                }
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


                                        klapzDownloaderror.setText(obj.getString("errorMessage"))

                                        if (Mode == "Direct") {
                                            user_detail_layout.visibility = View.GONE
                                            errorpopup.visibility = View.VISIBLE
//                                            Toast.makeText(context, obj.getString("errorMessage"), Toast.LENGTH_LONG)
//                                                    .show()
                                            bottomSheetDialog.show()
                                        } else {
                                            errorpopup2.visibility = View.VISIBLE
                                            klapzDownloaderror2.setText(obj.getString("errorMessage"))
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