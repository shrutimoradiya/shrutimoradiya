package com.sampsolution.contactlessdining.app

import android.app.Application
import android.content.Context
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import com.sampsolution.contactlessdining.BuildConfig
import com.sampsolution.contactlessdining.utils.Constant.PAYPAL_CLIENT_ID

class ServiceApp : Application() {

    companion object {

        var context: Context? = null
        var mInstance: ServiceApp? = null
    }


    override fun onCreate() {
        super.onCreate()
        context = this
        mInstance = this

        PayPalCheckout.setConfig(
            checkoutConfig = CheckoutConfig(
                application = this,
                clientId = PAYPAL_CLIENT_ID,
                environment = Environment.SANDBOX,
                currencyCode = CurrencyCode.USD,
                userAction = UserAction.PAY_NOW,
                settingsConfig = SettingsConfig(
                    loggingEnabled = true,
                    showWebCheckout = false
                ),
                returnUrl  = "${BuildConfig.APPLICATION_ID}://paypalpay"
            )
        )

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    fun setConnectivityListener(listener: ConnectivityReceiver.ConnectivityReceiverListener?) {
        ConnectivityReceiver.connectivityReceiverListener = listener
    }


}