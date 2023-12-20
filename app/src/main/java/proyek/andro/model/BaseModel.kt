package proyek.andro.model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

abstract class BaseModel(
    val collection: String,
) {
    val db = Firebase.firestore
    val collectionRef = db.collection(collection)

    fun <T> convertToClass(data: Map<String, Any>): T {
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
        filter : Filter? = null,
        limit: Int = 10000,
        offset: DocumentSnapshot? = null,
        order: Array<Array<String>> = arrayOf(arrayOf("id", "asc"))
    ): ArrayList<T> {

        val data = ArrayList<T>()
        var query : Query = collectionRef

        if (filter != null)
            query = query.where(filter)

        query = query.orderBy(
            order[0][0],
            if (order[0][1] == "asc") Query.Direction.ASCENDING else Query.Direction.DESCENDING
        )
        order.forEachIndexed { index, it ->
            if (index > 0) {
                query = query.orderBy(
                    it[0],
                    if (it[1] == "asc") Query.Direction.ASCENDING else Query.Direction.DESCENDING
                )
            }
        }

        query = query.limit(limit.toLong())

        if (offset != null) query = query.startAfter(offset)

        val res = query.get().await()

        res.forEach { result ->
            data.add(convertToClass(result.data))
        }

        return data
    }

    suspend fun <T> find(id: String): T {
//        var data : T = this::class.java.constructors.get(0).newInstance() as T
        val result = collectionRef
            .document(id)
            .get()
            .await()

        return convertToClass(result.data!!)
    }

    suspend fun insertOrUpdate(): Int {
        var status = 0

        val classData: HashMap<String, Any> =
            this::class.java.getDeclaredMethod("convertToMap").invoke(this) as HashMap<String, Any>

        val res = collectionRef
            .document(classData["id"].toString())
            .set(classData)
            .await()

        if (res != null) status = 1

        return status
    }

    suspend fun delete(doc: String): Int {
        var status = 0

        val res = collectionRef
            .document(doc)
            .delete()
            .await()

        if (res != null) status = 1

        return status
    }
}