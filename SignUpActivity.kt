package com.sampsolution.contactlessdining.view.activity

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Html
import android.util.ArrayMap
import android.widget.Toast
import com.google.gson.Gson
import com.sampsolution.contactlessdining.R
import com.sampsolution.contactlessdining.databinding.ActivitySignUpBinding
import com.sampsolution.contactlessdining.utils.Constant
import com.sampsolution.contactlessdining.utils.LocaleManager
import com.sampsolution.contactlessdining.utils.SharedPref
import java.util.Calendar

class SignUpActivity : BaseActivity() {

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(
            layoutInflater
        )
    }
    private var countryCode = ""
    private var mobileNo = ""
    private var date = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        countryCode = intent.getStringExtra("countryCode").toString()
        mobileNo = intent.getStringExtra("MobileNo").toString()

        binding.ivBack.setOnClickListener { onBackPressed() }

        binding.btnSignUp.setOnClickListener {
            val name = binding.edtFirstName.text.toString()
            val lastname = binding.edtLastName.text.toString()
            val email = binding.edtEmail.text.toString()
            val birthDate = binding.tvDateOfBirth.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_SHORT).show()
            } else if (lastname.isEmpty()) {
                Toast.makeText(this, "Please Enter Last Name", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            } else if (birthDate.isEmpty()) {
                Toast.makeText(this, "Please Select Birth Date", Toast.LENGTH_SHORT).show()
            } else {
                conformation(name, lastname, "+$countryCode", mobileNo, email, date)
            }

        }

        binding.tvDateOfBirth.setOnClickListener {
            val c: Calendar = Calendar.getInstance()

            val year: Int = c.get(Calendar.YEAR)
            val month: Int = c.get(Calendar.MONTH)
            val day: Int = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    date = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                    binding.tvDateOfBirth.text =
                        dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                },

                year, month, day
            )
            datePickerDialog.show()
        }


    }

    private fun conformation(
        fName: String,
        lName: String,
        countryCode: String,
        mobileNo: String,
        email: String,
        dateOFBirth: String
    ) {

        val dialog = ProgressDialog(this)
        dialog.show()

        val map: ArrayMap<String?, Any?> = ArrayMap<String?, Any?>()
        map["first_name"] = fName
        map["last_name"] = lName
        map["country_code"] = countryCode
        map["mobile_number"] = mobileNo
        map["email"] = email
        map["date_of_birth"] = dateOFBirth

        contactlessService?.signUpApi(this, map)?.observeForever {
            dialog.dismiss()
            it?.let { it1 ->
                if (it1.success == true) {
                    SharedPref(this).isLogin = true
                    Constant.saveData(this, Constant.USERID, it1.data?.userId.toString())
                    SharedPref(this).userInfo = Gson().toJson(it.data)
                    finish()
                    startActivity(Intent(this, HomeActivity::class.java))
                }
            }
        }

    }

    private var localeManager: LocaleManager? = null

    override fun attachBaseContext(newBase: Context?) {
        localeManager = LocaleManager(newBase)
        super.attachBaseContext(localeManager!!.setLocale(newBase))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager!!.setLocale(this)
    }

}