package com.sampsolution.contactlessdining.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.ConnectivityManager
import android.util.TypedValue
import com.payment.paymentsdk.integrationmodels.PaymentSdkLanguageCode
import okhttp3.RequestBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull


object Constant {

    const val FILENAME = "PREFERENCES_FILE"
    const val USERID = "userID"
    const val ADDCARTCOUNt = "addCartCount"
    const val dark = "dark"
    const val QRCODE = "qrCode"
    var CAMERA_REQUEST_CODE = 101
    var GALLERY_REQUEST_CODE = 102
    var REQUEST_ID_MULTIPLE_PERMISSIONS = 103
    var REQUEST_ID_CAMERA_PERMISSIONS = 104
    var REQUEST_ID_GALLERY_PERMISSIONS = 105
    const val connection = "connection"

    //payment option
    const val PAYPAL_ENABLE = false
    const val RAZOR_ENABLE = true
    const val PAY_TABS_ENABLE = true
    const val STRIPE = false

    // otp option
    const val TWILIO_OTP = false
    const val FACTOR2_OTP = false
    const val TELESIGN_OTP = false
    const val FIREBASE_OTP = true

    // 2 factor otp api key
    const val FACTOR2_API_KEY = "a48bf078-e059-11ee-8cbb-0200cd936042"

    // teleSign username ,password
    const val TELESIGN_USERNAME = "ED33AD30-538D-4EB5-8517-5DF1768E3E2A"
    const val TELESIGN_PASSWORD = "rTzu+JnIMAYr7yreE8m86833PVRoaFNqdxp+3rnUeEyS8ArxDFQ86u7sQLd90JHFWlHwIPpGZhUez1tdrUfobQ=="

    // twilio username,password
    const val TWILIO_USERNAME = "ACa31ff1c5ae3f275f97e7d7d90a543733"
    const val TWILIO_PASSWORD = "fb4213d988198e825e0b3ddb24522e54"


    //payPal Payment
    const val PAYPAL_CLIENT_ID =
        "AaWVPaAQgkChLl3H9OBIfYMKUfZneY7VJv8JNfkbFHt0YVM5vCFJlfaZJ-J9QN0_8wAoBfSoIIguPl1y"

    // razorPay Payment
    const val RAZORPAY_API_KEY = "rzp_test_uXQUnjHQZAmnnF"

    //payTabs Payment
    const val PROFILE_ID = "140879"
    const val SERVER_KEY = "SHJ9NZDRHH-JHWLBWMHZZ-TRM9R66WZ2"
    const val CLIENT_KEY = "C7K2M6-N9726H-VB2VNH-GV9DQ6"
    const val CURRENCY = "INR"
    const val ORDER_ID = "123456"
    const val MERCHANT_COUNTRY_CODE = "IN"


    fun saveData(context: Context, key: String?, data: String?) {
        val sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, data)
        editor.apply()
    }

    fun getData(context: Context, key: String?): String? {
        val sharedPreferences = context.getSharedPreferences(
            FILENAME,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(key, null)
    }


    fun intSaveData(context: Context, key: String?, data: Int): Boolean {
        val sharedPreferences = context.getSharedPreferences(FILENAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, data)
        editor.apply()
        return false
    }

    fun intGetData(context: Context, key: String?): Int {
        val sharedPreferences = context.getSharedPreferences(FILENAME, MODE_PRIVATE)
        //        int data =
        return sharedPreferences.getInt(key, 0)
    }

    fun saveBool(context: Context, key: String, data: Boolean) {
        val sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, data)
        editor.apply()
    }

    fun getBool(context: Context, key: String): Boolean {
        val sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    fun isOnline(context: Context): Boolean {
        val conMgr =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        return !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    fun getBodyData(value: String): RequestBody {
        return RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            value
        )
    }


}