package id.bikebosque.utils

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.StorageClient
import java.io.InputStream

object FirebaseAdmin {

    private val serviceAccount :InputStream? =
        this::class.java.classLoader.getResourceAsStream("firebase-service.json")

    private val option: FirebaseOptions =
        FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setStorageBucket("vikination-139c0.appspot.com")
            .build()

    fun init() :FirebaseApp = FirebaseApp.initializeApp(option)

    fun getBucket() = StorageClient.getInstance().bucket()
}