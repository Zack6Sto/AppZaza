package com.example.appzaza.util.biometric

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
   คลาสตัวช่วยสำหรับจัดการกระบวนการพิสูจน์ตัวตนด้วยไบโอเมตริกซ์
 */

class BiometricUtil {

    companion object {
        var biometricManagerPrompt: BiometricPrompt? = null

        val instance: BiometricUtil by lazy { Holder.INSTANCE }
    }
    private object Holder {
        val INSTANCE = BiometricUtil()
    }
    /**
     * ตรวจสอบว่าอุปกรณ์รองรับ Biometric หรือไม่
     */
    private fun hasBiometricCapability(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
    }
    /**
     * ตรวจสอบว่า Biometric Authentication (ตัวอย่าง: Fingerprint) ตั้งค่าไว้ในอุปกรณ์หรือไม่
     */
    fun isBiometricReady(context: Context) = hasBiometricCapability(context) == BiometricManager.BIOMETRIC_SUCCESS


    /**
     * ตรวจสอบว่าอุปกรณ์รองรับการสแกนใบหน้าหรือไม่
     */
//    private fun isFaceIdSupported(context: Context): Boolean {
//        val packageManager = context.packageManager
//        return packageManager.hasSystemFeature(PackageManager.FEATURE_FACE)
//    }

    fun hasFaceCapability(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        val result = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        return result == BiometricManager.BIOMETRIC_SUCCESS
    }
    /**
     * ตรวจสอบว่าอุปกรณ์พร้อมใช้งาน FaceID หรือไม่
     */
//    fun isBiometricReadyForFaceId(context: Context): Boolean {
//        return hasBiometricCapability(context) == BiometricManager.BIOMETRIC_SUCCESS && isFaceIdSupported(context)
//    }

    private fun isFaceIdSupported(context: Context): Boolean {
        val packageManager = context.packageManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            packageManager.hasSystemFeature(PackageManager.FEATURE_FACE)
        } else {
            TODO("VERSION.SDK_INT < Q")
        }
    }

    fun isBiometricReadyForFaceId(context: Context): Boolean {
        return isFaceIdSupported(context)
    }


    /**
     * เตรียมกล่องโต้ตอบ PromptInfo พร้อมการกำหนดค่าที่มีให้
     */
//    private fun setBiometricPromptInfo(
//        title: String,
//        subtitle: String,
//        description: String,
//        allowDeviceCredential: Boolean
//    ): BiometricPrompt.PromptInfo {
//        val builder = BiometricPrompt.PromptInfo.Builder()
//            .setTitle(title)
//            .setSubtitle(subtitle)
//            .setDescription(description)
//            .setConfirmationRequired(false)
//            .setNegativeButtonText("Cancel")
//
//
//
//        // Use Device Credentials if allowed, otherwise show Cancel Button
////    builder.apply {
////      if (allowDeviceCredential) setDeviceCredentialAllowed(true)
////      else setNegativeButtonText("Cancel")
////    }
//
//        return builder.build()
//    }

    private fun setBiometricPromptInfo(
        title: String,
        subtitle: String,
        description: String,
        allowDeviceCredential: Boolean
    ): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeButtonText("Cancel") // ตั้งค่าข้อความลบที่ต้องการ เช่น "Cancel", "Dismiss" หรืออื่นๆ
            .setConfirmationRequired(false) // ตั้งค่าเป็น false เพื่อแสดงกล่องโต้ตอบสแกนใบหน้า
            .setDeviceCredentialAllowed(allowDeviceCredential)
            .build()
    }


    /**
     * เริ่มต้น BiometricPrompt กับผู้โทรและตัวจัดการการโทรกลับ
     */
//    private fun initBiometricPrompt(
//        activity: FragmentActivity,
//        fragment: Fragment,
//        listener: BiometricAuthListener
//    ): BiometricPrompt {
//        // แนบกิจกรรมการโทร
//        val executor = ContextCompat.getMainExecutor(activity)
//
//        // แนบตัวจัดการการโทรกลับ
//        val callback = object : BiometricPrompt.AuthenticationCallback() {
//            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                super.onAuthenticationError(errorCode, errString)
//                listener.onBiometricAuthenticationError(errorCode, errString.toString())
//            }
//
//            override fun onAuthenticationFailed() {
//                super.onAuthenticationFailed()
//                Log.w(this.javaClass.simpleName, "Authentication failed for an unknown reason")
//                listener.onBiometricAuthenticationFailed()
//            }
//
//            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                super.onAuthenticationSucceeded(result)
//                listener.onBiometricAuthenticationSuccess(result)
//            }
//        }
//
//        return BiometricPrompt(fragment, executor, callback)
//    }

    private fun initBiometricPrompt(
        activity: AppCompatActivity,
        listener: BiometricAuthListener
    ): BiometricPrompt {
        // แนบกิจกรรมการโทร
        val executor = ContextCompat.getMainExecutor(activity)

        // แนบตัวจัดการการโทรกลับ
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                listener.onBiometricAuthenticationError(errorCode, errString.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.w(this.javaClass.simpleName, "Authentication failed for an unknown reason")
                listener.onBiometricAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onBiometricAuthenticationSuccess(result)
            }
        }

        return BiometricPrompt(activity, executor, callback)
    }

    /**
     * แสดง BiometricPrompt พร้อมการกำหนดค่าที่มีให้
     */
//    fun showBiometricPrompt(
//        title: String = "Biometric Authentication",
//        subtitle: String = "Enter biometric credentials to proceed.",
//        description: String = "Input your Fingerprint or FaceID to ensure it's you!",
//        activity: FragmentActivity,
//        fragment: Fragment,
//        listener: BiometricAuthListener,
//        cryptoObject: BiometricPrompt.CryptoObject? = null,
//        allowDeviceCredential: Boolean = false
//    ) {
//        // เตรียมกล่องโต้ตอบ BiometricPrompt
//        val promptInfo = setBiometricPromptInfo(
//            title,
//            subtitle,
//            description,
//            allowDeviceCredential
//        )
//
//        // แนบกับผู้โทรและตัวจัดการการโทรกลับ
//        biometricManagerPrompt = initBiometricPrompt(activity, fragment, listener)
//
//        // รับรองความถูกต้องด้วย CryptoObject หากมีให้ มิฉะนั้น การรับรองความถูกต้องเป็นค่าเริ่มต้น
//        biometricManagerPrompt?.apply {
//            if (cryptoObject == null) authenticate(promptInfo)
//            else authenticate(promptInfo, cryptoObject)
//        }
//    }

    fun showBiometricPrompt(
        title: String = "Biometric Authentication",
        subtitle: String = "Enter biometric credentials to proceed.",
        description: String = "Input your Fingerprint or FaceID to ensure it's you!",
        activity: AppCompatActivity,
        listener: BiometricAuthListener,
        cryptoObject: BiometricPrompt.CryptoObject? = null,
        allowDeviceCredential: Boolean = false
    ) {
        // เตรียมกล่องโต้ตอบ BiometricPrompt
        val promptInfo = setBiometricPromptInfo(
            title,
            subtitle,
            description,
            allowDeviceCredential
        )

        // แนบกับผู้โทรและตัวจัดการการโทรกลับ
        biometricManagerPrompt = initBiometricPrompt(activity, listener)

        // รับรองความถูกต้องด้วย CryptoObject หากมีให้ มิฉะนั้น การรับรองความถูกต้องเป็นค่าเริ่มต้น
        biometricManagerPrompt?.apply {
            if (cryptoObject == null) authenticate(promptInfo)
            else authenticate(promptInfo, cryptoObject)
        }
    }

    fun showFaceBiometricPrompt(
        activity: AppCompatActivity,
        listener: BiometricAuthListener
    ) {
        val promptInfo = setBiometricPromptInfo(
            title = "Face Biometric Authentication",
            subtitle = "Scan your face to authenticate",
            description = "Please align your face with the camera",
            allowDeviceCredential = false
        )

        val biometricPrompt = initBiometricPrompt(activity, listener)
        biometricPrompt.authenticate(promptInfo)
    }


    fun dimissBiometric() {
        biometricManagerPrompt?.cancelAuthentication()
    }

    /**
     * นำทางไปยังหน้าจอการตั้งค่าของอุปกรณ์ การตั้งค่าไบโอเมตริกซ์
     */
    fun lunchBiometricSettings(context: Context) {
        ActivityCompat.startActivity(
            context,
            Intent(Settings.ACTION_SECURITY_SETTINGS),
            null
        )
    }

}