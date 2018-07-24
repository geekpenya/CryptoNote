package com.example.bender

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import android.widget.EditText
import android.widget.Toast
import javax.crypto.Cipher

class FingerPrintHelper(var fcontext: Context, var sp: SharedPreferences, var editText: EditText) : FingerprintManagerCompat.AuthenticationCallback() {
    private var fsignal: CancellationSignal = CancellationSignal()


    fun startAuth(crypObj: FingerprintManagerCompat.CryptoObject) {

        var manager = FingerprintManagerCompat.from(fcontext)
        manager.authenticate(crypObj, 0, fsignal,this,null)
    }

    fun cancel() {
        if (fsignal.isCanceled)
            fsignal.cancel()
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
        Toast.makeText(fcontext, errString, Toast.LENGTH_SHORT).show()
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
        Toast.makeText(fcontext, helpString, Toast.LENGTH_SHORT).show()
    }


    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        Toast.makeText(fcontext, "Успешно", Toast.LENGTH_SHORT).show()
        (fcontext as MainActivity).finish()

    }

    override fun onAuthenticationFailed() {
        Toast.makeText(fcontext, "Повторить", Toast.LENGTH_SHORT).show()
    }



}