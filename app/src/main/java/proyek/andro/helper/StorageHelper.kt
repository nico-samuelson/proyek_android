package proyek.andro.helper

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class StorageHelper {
    val storageRef = FirebaseStorage.getInstance().reference

    suspend fun getFile(path: String) : Uri {
        var imageRef = storageRef.child(path)
        return imageRef.downloadUrl.await()
    }

    fun uploadFile(path: String, file: ByteArray) {
        val res = storageRef.child(path).putBytes(file).addOnSuccessListener {
            Log.d("FirebaseFiles", "File uploaded successfully")
        }.addOnFailureListener {
            Log.d("FirebaseFiles", "File upload failed")
        }
    }
}