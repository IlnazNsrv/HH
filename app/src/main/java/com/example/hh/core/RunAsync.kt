package com.example.hh.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RunAsync {

    fun <T: Any> runAsync(coroutineScope: CoroutineScope,
                          background: suspend () -> T,
                          ui: (T) -> Unit)

    class Base : RunAsync {
        override fun <T : Any> runAsync(
            coroutineScope: CoroutineScope,
            background: suspend () -> T,
            ui: (T) -> Unit
        ) {
            coroutineScope.launch(Dispatchers.IO) {
                val result = background.invoke()
                withContext(Dispatchers.Main) {
                    ui.invoke(result)
                }
            }
        }

    }
}