package com.chandilsachin.personal_finance.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.chandilsachin.personal_finance.R

/**
 * Created by sachin on 28/5/17.
 */
fun <T: ViewModel> Fragment.initViewModel(c:Class<T>):T{
    val model = ViewModelProviders.of(this).get(c)
    return model
}

fun Fragment.loadFragment(containerId:Int, fragment: Fragment){
    loadFragment(containerId, fragment, activity as AppCompatActivity)
}

fun Fragment.loadFragmentSlideUp(containerId:Int, fragment: Fragment){
    activity!!.supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                    R.anim.slide_out_down, R.anim.slide_in_down)
            .replace(containerId, fragment)
            .addToBackStack(null).commit()
}

fun loadFragment(containerId:Int, fragment: Fragment, activity: AppCompatActivity){
    activity.supportFragmentManager.beginTransaction().replace(containerId, fragment)
            .addToBackStack(null).commit()
}



fun Fragment.setSupportActionBar(toolbar: Toolbar){
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
}

fun Fragment.setDisplayHomeAsUpEnabled(value:Boolean){
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(value)
}

fun Fragment.getAppCompactActivity():AppCompatActivity{
    return activity as AppCompatActivity
}

