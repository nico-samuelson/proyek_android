package proyek.andro.model

import com.google.firebase.Firebase
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

abstract class BaseModel(
    val collection : String,
) {
    val db = Firebase.firestore
    @Exclude
    fun all() : ArrayList<Any> {
        val data = ArrayList<Any>()

        db.collection(collection).get().addOnSuccessListener { result ->
            for (document in result) {
                data.add(document)
            }
        }

        return data
    }
    @Exclude
    fun find(id: String) : Any {
        lateinit var data : Any

        db.collection(collection).whereEqualTo("id", id).get().addOnSuccessListener { result ->
            for (document in result) {
                data = document
            }
        }

        return data
    }

    @Exclude
    fun insert(data: Any) : Int {
        var status = 0

        db.collection(collection).add(data).addOnSuccessListener {
            status = 1
        }

        return status
    }

    @Exclude
    fun update(data: Any, id : String) : Int {
        var status = 0

        db.collection(collection).document(id).set(data).addOnSuccessListener {
            status = 1
        }

        return status
    }

    @Exclude
    fun delete(doc: String) : Int {
        var status = 0

        db.collection(collection).document(doc).delete().addOnSuccessListener {
            status = 1
        }

        return status
    }
}