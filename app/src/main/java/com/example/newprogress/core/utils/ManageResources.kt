package com.example.newprogress.core.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ManageResources {

    fun string(@StringRes id: Int): String

    class Base @Inject constructor(@ApplicationContext private val context: Context) :
        ManageResources {

        override fun string(id: Int): String = context.getString(id)
    }
}