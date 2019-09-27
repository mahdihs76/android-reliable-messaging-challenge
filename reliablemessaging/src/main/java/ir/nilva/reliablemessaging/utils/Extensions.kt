package ir.nilva.reliablemessaging.utils

fun mergeToMap(keys: Array<String>, values: Array<String>) = HashMap<String, String>().apply {
    keys.forEachIndexed { index, key -> put(key, values[index]) }
}