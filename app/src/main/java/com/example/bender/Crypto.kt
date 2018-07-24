package com.example.bender

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.util.Base64
import java.security.*
import java.security.spec.MGF1ParameterSpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource

class Crypto {
    companion object {
        lateinit var keyStore: KeyStore
        lateinit var keyGen: KeyGenerator
        val KEY_ALI = "key_for_pin"
        val KEY_STORE = "AndroidKeyStore"
        lateinit var cipher: Cipher



        fun getKeyStore(): Boolean {
            try {
                keyStore = KeyStore.getInstance(KEY_STORE)
                keyStore.load(null)
                return true
            }
            catch (e: KeyStoreException ) {
                e.printStackTrace()
            }
            return false
        }

        fun getKeyPair(): Boolean {
            try {
                keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_STORE)
                return true
            }
            catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return false
        }

        fun generateKey(): Boolean {
            if (getKeyPair()) {
                try {
                    keyStore?.load(null)
                    val builder = KeyGenParameterSpec.Builder(KEY_ALI,
                            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setUserAuthenticationRequired(true)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)

                    keyGen?.init(builder.build())
                    keyGen?.generateKey()
                }

                catch (e: InvalidAlgorithmParameterException) {
                    e.printStackTrace()
                }
            }
            return false
        }





        fun getcipher(): Boolean {
            try {
                cipher = Cipher.getInstance( KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7)
                return true
            }
            catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return false
        }

        fun initChiper(mode: Int): Boolean {
            try {
                getKeyStore()
                getKeyPair()
                generateKey()
                getcipher()
                keyStore?.load(null)
                val key = keyStore?.getKey(KEY_ALI, null) as SecretKey
                cipher.init(Cipher.ENCRYPT_MODE, key)
                return true
            }
            catch (e: KeyPermanentlyInvalidatedException) {
                e.printStackTrace()
            }

            return false
        }


        fun deleteInvalidKey() {
            if (getKeyStore()) {
                try {
                    keyStore.deleteEntry(KEY_ALI)
                }
                catch (e: KeyStoreException) {
                    e.printStackTrace()
                }
            }
        }



        fun encoder(input: String): String? {
            try {
                val key = keyStore?.getKey(KEY_ALI, null) as SecretKey
                cipher.init(Cipher.ENCRYPT_MODE, key)

                var byts: ByteArray = cipher.doFinal(input.toByteArray())
                return Base64.encodeToString(byts, Base64.NO_WRAP)

            }
            catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            }
            return null
        }

        fun decoder(encodString: String, cipher: Cipher): String? {
            try {

                var byts: ByteArray = Base64.decode(encodString, Base64.NO_WRAP)
                return String(cipher.doFinal(byts))
            }
            catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            }
            return null
        }

        fun getCryptoObj(): FingerprintManagerCompat.CryptoObject? {
            if (initChiper(Cipher.DECRYPT_MODE)) {
                return FingerprintManagerCompat.CryptoObject(cipher)
            }
            return null
        }
    }
}