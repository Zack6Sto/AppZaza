package com.example.appzaza.util.biometric

import androidx.biometric.BiometricPrompt

/**
   อินเทอร์เฟซทั่วไปเพื่อฟังการโทรกลับ Biometric Authentication
 */

interface BiometricAuthListener {
    fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult)
    fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String)
    fun onBiometricAuthenticationFailed()
}