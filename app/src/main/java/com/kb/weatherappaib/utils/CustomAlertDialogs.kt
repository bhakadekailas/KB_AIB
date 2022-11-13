package com.kb.weatherappaib.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.kb.weatherappaib.R

class CustomAlertDialogs(private val context: Context) {

    private var loadingDialog: AlertDialog? = null

    fun startLoadingDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.loading, null)
        builder.setView(view)
        builder.setCancelable(true)
        loadingDialog = builder.create()
        loadingDialog!!.show()
    }

    fun stopLoadingDialog() {
        loadingDialog?.dismiss()
    }

    fun showInformationDialog(message: String?) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK") { arg0, arg1 ->
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}