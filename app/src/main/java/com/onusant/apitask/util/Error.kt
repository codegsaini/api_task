package com.onusant.apitask.util

import java.lang.Exception

data class Error(
    val message: String,
    val exception: Exception? = null
)
