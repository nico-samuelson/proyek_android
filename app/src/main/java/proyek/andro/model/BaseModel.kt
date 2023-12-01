package proyek.andro.model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

abstract class BaseModel(
    val collection : String,
) {
    val db = Firebase.firestore
    val collectionRef = db.collection(collection)

    fun <T> convertToClass(data: Map<String, Any>) : T {
        val clazz = this::class.java
        val constructor = clazz.constructors[1]

        val args = constructor.parameters.map { param ->
            val key = param.name ?: throw IllegalArgumentException("Parameter name not found")
            val value = data[key]
            value
        }.toTypedArray()

        return constructor.newInstance(*args) as T
    }

    suspend fun <T> get(
        limit : Int = 10,
        offset : Int = 0,
        order : Array<String> = arrayOf("id", "asc")
    ) : ArrayList<T> {

        val data = ArrayList<T>()

        val res = collectionRef
            .orderBy(
                order[0],
                if (order[1] === "asc") Query.Direction.ASCENDING else Query.Direction.DESCENDING
            )
            .limit(limit.toLong())
            .startAt(offset.toLong())
            .get()
            .await()

        res.forEach { result ->
            data.add(convertToClass(result.data))
        }

        return data
    }

    suspend fun <T> find(id: String) : T {
//        var data : T = this::class.java.constructors.get(0).newInstance() as T
        val result = collectionRef
            .document(id)
            .get()
            .await()

        return convertToClass(result.data!!)
    }

    suspend fun insertOrUpdate() : Int {
        var status = 0

        val classData : HashMap<String, Any> = this::class.java.getDeclaredMethod("convertToMap").invoke(this) as HashMap<String, Any>

        val res = collectionRef
            .document(classData["id"].toString())
            .set(classData)
            .await()

        if (res != null) status = 1

        return status
    }

    suspend fun delete(doc : String) : Int {
        var status = 0

        val res = collectionRef
            .document(doc)
            .delete()
            .await()

        if (res != null) status = 1

        return status
    }
}