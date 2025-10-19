package com.vascomm.basekotlin.util.security

import android.content.Context
import okhttp3.CertificatePinner
import java.io.InputStream
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

object SSLPinningHelper {

    /**
     * Get certificate from assets folder
     */
    fun getCertificateFromAssets(context: Context, certFileName: String): String {
        return try {
            val inputStream: InputStream = context.assets.open(certFileName)
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val certificate = certificateFactory.generateCertificate(inputStream) as X509Certificate

            // Get SHA-256 hash of certificate
            val encoded = certificate.publicKey.encoded
            val sha256 = java.security.MessageDigest.getInstance("SHA-256").digest(encoded)
            "sha256/${android.util.Base64.encodeToString(sha256, android.util.Base64.NO_WRAP)}"
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Build Certificate Pinner with domain and certificate hash
     */
    fun buildCertificatePinner(domain: String, certificateHash: String): CertificatePinner {
        return CertificatePinner.Builder()
            .add(domain, certificateHash)
            .build()
    }

    /**
     * Build Certificate Pinner with multiple domains
     */
    fun buildCertificatePinner(certificates: Map<String, List<String>>): CertificatePinner {
        val builder = CertificatePinner.Builder()
        certificates.forEach { (domain, hashes) ->
            hashes.forEach { hash ->
                builder.add(domain, hash)
            }
        }
        return builder.build()
    }

    /**
     * Get certificate hash manually (if you already have the hash)
     * Example: sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=
     */
    fun getCertificateHash(domain: String): String {
        // TODO: Replace with your actual certificate hash
        // You can get this by running: openssl s_client -connect yourdomain.com:443 | openssl x509 -pubkey -noout | openssl pkey -pubin -outform der | openssl dgst -sha256 -binary | openssl enc -base64
        return when (domain) {
            "api.github.com" -> "sha256/example_hash_here"
            "your-api-domain.com" -> "sha256/example_hash_here"
            else -> ""
        }
    }
}

