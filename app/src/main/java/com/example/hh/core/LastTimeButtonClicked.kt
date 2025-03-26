package com.example.hh.core

interface LastTimeButtonClicked {

    fun timePassed() : Boolean

    class Base(private val diffInMillis: Long = 200) : LastTimeButtonClicked {

        @Volatile
        private var lastTime = 0L

        override fun timePassed(): Boolean = synchronized(this) {
            val now = System.currentTimeMillis()
            val result = now - lastTime > diffInMillis
            if (result)
                lastTime = now
            return result
        }
    }
}