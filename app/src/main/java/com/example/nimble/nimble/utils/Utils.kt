package com.example.nimble.nimble.utils

import com.google.gson.Gson
import java.lang.reflect.Type

class Utils {
    fun jsonToMap(json: String): Map<String, Any>? {
        val gson = Gson()
        val type: Type = com.google.gson.reflect.TypeToken.getParameterized(
            Map::class.java, String::class.java, Any::class.java
        ).type
        return gson.fromJson(json, type)
    }
}