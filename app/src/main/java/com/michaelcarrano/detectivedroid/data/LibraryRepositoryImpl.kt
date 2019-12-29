package com.michaelcarrano.detectivedroid.data

import android.content.res.Resources
import androidx.annotation.VisibleForTesting
import com.michaelcarrano.detectivedroid.R
import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import io.reactivex.Single
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.UUID
import javax.inject.Inject
import org.json.JSONArray

class LibraryRepositoryImpl @Inject constructor(
    private val resources: Resources
) : LibraryRepository {

    private var librariesCache = listOf<LibraryEntity>()

    private val jsonString: String by lazy {
        convertStreamToString(resources.openRawResource(R.raw.libraries))
    }

    override fun getLibraries(): Single<List<LibraryEntity>> {
        if (librariesCache.isEmpty()) {
            librariesCache = parseLibrariesJson(JSONArray(jsonString))
        }
        return Single.just(librariesCache)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun parseLibrariesJson(json: JSONArray): MutableList<LibraryEntity> {
        val libraries = mutableListOf<LibraryEntity>()

        for (i in 0 until json.length()) {
            val jsonObject = json.getJSONObject(i)
            val id = jsonObject.getString("id")
            val name = jsonObject.getString("name")
            val source = jsonObject.getString("source")
            val classPath = jsonObject.getString("classPath")
            val createdAt = jsonObject.getLong("createdAt")
            val updatedAt = jsonObject.getLong("updatedAt")
            libraries.add(
                LibraryEntity(
                    id = UUID.fromString(id),
                    name = name,
                    source = source,
                    classPath = classPath,
                    createdAt = createdAt,
                    updatedAt = updatedAt
                )
            )
        }
        return libraries
    }

    private fun convertStreamToString(input: InputStream): String {
        val reader = BufferedReader(InputStreamReader(input))
        val sb = StringBuilder()
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line + "\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }
}
