package proyek.andro.helper

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class StorageHelper {
//    val storageRef = FirebaseStorage.getInstance().reference

    suspend fun preloadImages(images: List<String>, directory: String): ArrayList<Uri> {
        val imagesURI = ArrayList<Uri>()
        val storageRef = FirebaseStorage.getInstance().reference

        images.forEach { image ->
            suspendCoroutine { continuation ->
                storageRef.child("${directory}/${image}")
                    .downloadUrl
                    .addOnSuccessListener { uri ->
                        imagesURI.add(uri)
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener {
                        Log.e("preload image", it.message.toString())
                        continuation.resume(Unit)
                    }
            }
        }

        return imagesURI
    }
}