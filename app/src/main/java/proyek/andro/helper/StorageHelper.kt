package proyek.andro.helper

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class StorageHelper {
    val storageRef = FirebaseStorage.getInstance().reference

    suspend fun getImageURI(image : String, directory: String) : Uri {
        return storageRef.child("${directory}/${image}").downloadUrl.await()
    }

    suspend fun preloadImages(images: List<String>, directory: String): ArrayList<Uri> {
        val imagesURI = ArrayList<Uri>()

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

    suspend fun uploadFile(uri: Uri, path: String): Task<Uri> {
        val storageRef = FirebaseStorage.getInstance().reference
        val fileRef = storageRef.child(path)

        return fileRef.putFile(uri).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            fileRef.downloadUrl
        }
    }

    suspend fun deleteFile(path: String): Task<Void> {
        return storageRef.child(path).delete()
    }
}