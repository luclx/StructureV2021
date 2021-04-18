package com.luclx.structure2021.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object CommonUtils {

    private val TAG = "CommonUtils"

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun isEmailValid(email: String): Boolean {
        val pattern: Pattern
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun getTimeStamp(): String {
        return SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(Date())
    }

    fun getSSLSocketFactory(): SSLSocketFactory {

        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        return sslContext.socketFactory
    }

//	fun getUnsafeOkHttpClient(): OkHttpClient {
//		// Create a trust manager that does not validate certificate chains
//		val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//			override fun getAcceptedIssuers(): Array<X509Certificate> {
//				return arrayOf()
//			}
//
//			override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
//
//			override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
//		})
//
//		// Install the all-trusting trust manager
//		val sslContext = SSLContext.getInstance("SSL")
//		sslContext.init(null, trustAllCerts, SecureRandom())
//
//		// Create an ssl socket factory with our all-trusting manager
//		val builder = OkHttpClient.Builder()
//		builder.sslSocketFactory(getSSLSocketFactory(), trustAllCerts[0] as X509TrustManager)
//		builder.hostnameVerifier { _, _ -> true }
//		return builder.build()
//	}

    fun parseDateTimeIso8601(strDateTime: String?): Long {
        var result: Long = 0
        try {
            val sdf = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val date = sdf.parse(strDateTime)
            if (date != null) {
                result = date.time
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}
