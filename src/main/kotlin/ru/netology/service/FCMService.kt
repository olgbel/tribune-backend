package ru.netology.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Hex
import org.springframework.security.crypto.encrypt.Encryptors
import java.io.ByteArrayInputStream

class FCMService(private val dbUrl: String, private val password: String, private val salt: String/*, private val path: String*/) {
    init {
        val decryptor = Encryptors.stronger(password, Hex.encodeHexString(salt.toByteArray(Charsets.UTF_8)))
//        val decrypted = decryptor.decrypt(Files.readAllBytes(Paths.get(path)))

        val options = FirebaseOptions.Builder()
//            .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(decrypted)))
            .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(System.getenv("GCS_CREDENTIALS").toByteArray())))
            .setDatabaseUrl(dbUrl)
            .build()

        FirebaseApp.initializeApp(options)
    }

    suspend fun send(recipientId: Long, recipientToken: String, title: String, text: String) = withContext(Dispatchers.IO) {
        try {
            val message = Message.builder()
                .putData("recipientId", recipientId.toString())
                .putData("title", title)
                .putData("text", text)
                .setToken(recipientToken)
                .build()

            FirebaseMessaging.getInstance().send(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}