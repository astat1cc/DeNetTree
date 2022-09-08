package com.github.astat1cc.denettree.utils

import android.content.Context
import androidx.annotation.StringRes

class AppResourceProvider(private val context: Context) {

    fun getString(@StringRes id: Int, arg: String) = context.getString(id, arg)
}