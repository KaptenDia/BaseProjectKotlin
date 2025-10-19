package com.vascomm.basekotlin.util.security

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionHelper {

    private const val AES_ALGORITHM = "AES"
    private const val AES_TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private const val RSA_ALGORITHM = "RSA"
    private const val RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding"

    /**
     * Encrypt data using AES
     */
    fun encryptAES(data: String, secretKey: String): String {
        return try {
            val keySpec = SecretKeySpec(secretKey.toByteArray(), AES_ALGORITHM)
            val cipher = Cipher.getInstance(AES_TRANSFORMATION)

            // Generate random IV
            val iv = ByteArray(16)
            java.security.SecureRandom().nextBytes(iv)
            val ivSpec = IvParameterSpec(iv)

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(data.toByteArray())

            // Combine IV and encrypted data
            val combined = iv + encrypted
            Base64.encodeToString(combined, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Decrypt data using AES
     */
    fun decryptAES(encryptedData: String, secretKey: String): String {
        return try {
            val combined = Base64.decode(encryptedData, Base64.NO_WRAP)

            // Extract IV and encrypted data
            val iv = combined.sliceArray(0 until 16)
            val encrypted = combined.sliceArray(16 until combined.size)

            val keySpec = SecretKeySpec(secretKey.toByteArray(), AES_ALGORITHM)
            val ivSpec = IvParameterSpec(iv)
            val cipher = Cipher.getInstance(AES_TRANSFORMATION)

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val decrypted = cipher.doFinal(encrypted)
            String(decrypted)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Generate AES Secret Key
     */
    fun generateAESKey(keySize: Int = 256): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM)
        keyGenerator.init(keySize)
        return keyGenerator.generateKey()
    }

    /**
     * Encrypt API Request
     */
    fun encryptRequest(data: String, moduleId: String): String {
        // TODO: Implement your encryption logic based on moduleId
        // This is a placeholder implementation
        val secretKey = getSecretKeyForModule(moduleId)
        return encryptAES(data, secretKey)
    }

    /**
     * Decrypt API Response
     */
    fun decryptResponse(encryptedData: String, moduleId: String): String {
        // TODO: Implement your decryption logic based on moduleId
        val secretKey = getSecretKeyForModule(moduleId)
        return decryptAES(encryptedData, secretKey)
    }

    /**
     * Get secret key based on module
     * TODO: Replace with your actual key management system
     */
    private fun getSecretKeyForModule(moduleId: String): String {
        // This should be securely managed, not hardcoded
        // Consider using Android Keystore System
        return "your-secret-key-here-16bytes" // Must be 16, 24, or 32 bytes for AES
    }
}

