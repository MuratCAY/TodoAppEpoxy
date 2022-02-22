package com.muratcay.todoappepoxy.arch

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    // içeriği döndürür ve tekrar kullanılmasını engeller
    fun getContent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
    // Daha önce işlenmiş olsa bile içeriği döndürür
    fun peekContent(): T = content
}