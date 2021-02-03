package com.jutter.sharerecipes.extensions

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View?) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Long.convertTimeToDDMMYYYY(): String {
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(this)
}

fun Context.showKeyboard(view: View?) {
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Context.clearFocusAfterDone(editText: TextInputEditText) {
    editText.setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE ||
            event != null &&
            event.action == KeyEvent.ACTION_DOWN &&
            event.keyCode == KeyEvent.KEYCODE_ENTER
        ) {
            if (event == null || !event.isShiftPressed) {
                hideKeyboard(v)
                v.clearFocus()
                return@setOnEditorActionListener true
            }
        }
        return@setOnEditorActionListener false
    }
}

fun convertPhotoFilePathToBase64(path: String): String {
    val imageFile = File(path)
    var fis: FileInputStream? = null

    try {
        fis = FileInputStream(imageFile)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }

    val bm = BitmapFactory.decodeStream(fis)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()

    return "data:image/jpeg;base64,${Base64.encodeToString(b, Base64.DEFAULT)}"
}

fun getRealPathFromURI(
    context: Context,
    contentUri: Uri?
): String? {
    var cursor: Cursor? = null
    return try {
        val proj =
            arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
        val colcumn_index: Int = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)!!
        cursor.moveToFirst()
        cursor.getString(colcumn_index)
    } finally {
        cursor?.close()
    }
}