package com.kocci.disastertracker.util.extension

import android.view.View

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}