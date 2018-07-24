package com.example.bender

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

class FingerPrint {
    enum class SensorState {
        NOT_SUPPORTED,
        NOT_BLOCKED,
        NO_FINGERPRINTS,
        READY
    }



   companion object {

       fun checkFPCom(context: Context): Boolean {
           return FingerprintManagerCompat.from(context).isHardwareDetected
       }

       @SuppressLint("ServiceCast")
       fun checkSensorState(context: Context): SensorState {
           if (checkFPCom(context)) {
               var keyGuard: KeyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
               if (!keyGuard.isKeyguardSecure) {
                   return SensorState.NOT_BLOCKED
               }
               var fpManager: FingerprintManagerCompat = FingerprintManagerCompat.from(context)
               if (!fpManager.hasEnrolledFingerprints()) return SensorState.NO_FINGERPRINTS
               else return SensorState.READY
           } else
               return SensorState.NOT_SUPPORTED
       }

       fun isSensorState(state: SensorState, context: Context): Boolean {
           return checkSensorState(context) == state
       }



   }
}