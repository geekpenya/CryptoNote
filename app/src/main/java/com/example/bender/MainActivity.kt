package com.example.bender

import android.Manifest.permission.READ_PHONE_STATE
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.security.*

class MainActivity : AppCompatActivity() {

    var typeLogin = false
    val SET = "setting"
    lateinit var sp: SharedPreferences
    lateinit var toast: Toast
    val PIN = "pin"
    lateinit var fpHelper: FingerPrintHelper
    lateinit var BD: SQLiteDatabase
    val BD_NAME = "NoteBD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = getSharedPreferences(SET, Context.MODE_PRIVATE)
        toast = Toast.makeText(this, "message", Toast.LENGTH_SHORT)

        if(FingerprintManagerCompat.from(this).isHardwareDetected)
            fpHelper = FingerPrintHelper(this, getSharedPreferences(SET, Context.MODE_PRIVATE), editText)

        var permissionCheck: Int = ContextCompat.checkSelfPermission(this, READ_PHONE_STATE )
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(READ_PHONE_STATE), 0)
        }

        var hasVis: Boolean = sp.getBoolean("hasVis", false)
        if(!hasVis) {
            textView.text = "Задать PIN-Код"
            val e: SharedPreferences.Editor = sp.edit()
            e.putBoolean("hasVis", true)
            e.apply()
            BD = openOrCreateDatabase(BD_NAME, Context.MODE_PRIVATE, null)
            BD.execSQL("CREATE TABLE IF NOT EXISTS Note (titel VARCHAR, text VAECHAR, photo BLOB)")
            BD.close()
        }

        else {
            textView.text = "Введите PIN-Код"
            typeLogin = true
            prepearSens()
        }
    }

    override fun onResume() {
        super.onResume()
        if (FingerprintManagerCompat.from(this).isHardwareDetected) {
            if (sp.contains(PIN))
                prepearSens()
        }
    }

    override fun onStop() {
        super.onStop()
        if(FingerprintManagerCompat.from(this).isHardwareDetected)
            fpHelper.cancel()
    }

    private fun prepearLogin() {
        val pin = editText.text.toString()
        if (pin.length > 0) {
            sp.edit().putString(PIN, hash(editText.text.toString())).apply()
        }
        else
            Toast.makeText(this, "Пин-код не введен", Toast.LENGTH_SHORT).show()
    }

    private fun prepearSens() {
        if(FingerprintManagerCompat.from(this).isHardwareDetected) {
            if (FingerPrint.isSensorState(FingerPrint.SensorState.READY, this)) {
                val cryptoObj = Crypto.getCryptoObj()
                if (cryptoObj != null) {
                // Toast.makeText(this, "Сканер отпечатков пальцев готов", Toast.LENGTH_LONG).show()
                 fpHelper.startAuth(cryptoObj)
              } else {
                    sp.edit().remove(PIN).apply()
                 Toast.makeText(this, "Отпечаток пальцев был изменен. Введите ПИН", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    fun okbut (view: View) {
        if (!typeLogin) {
            prepearLogin()
            Toast.makeText(this, "PIN-Код сохранен", Toast.LENGTH_SHORT).show()
            finish()
        }

        else {
            val pinUser = hash(editText.text.toString())
            val pinBase = sp.getString(PIN, "")
            if (pinBase == pinUser)
                finish()
            else
                Toast.makeText(this, "PIN-Код не верен", Toast.LENGTH_SHORT).show()
        }
    }

    fun hash(string: String): String {
        val bytes = string.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }



    @Override
    override fun onBackPressed() {}



    fun but1 (view: View) {
        editText.append("1")
    }

    fun but2 (view: View) {
        editText.append("2")
    }

    fun but3 (view: View) {
        editText.append("3")
    }

    fun but4 (view: View) {
        editText.append("4")
    }

    fun but5 (view: View) {
        editText.append("5")
    }

    fun but6 (view: View) {
        editText.append("6")
    }

    fun but7 (view: View) {
        editText.append("7")
    }

    fun but8 (view: View) {
        editText.append("8")
    }

    fun but9 (view: View) {
        editText.append("9")
    }

    fun but0 (view: View) {
        editText.append("0")
    }

    fun butbs(view: View) {
        editText.text.clear()
    }
}
