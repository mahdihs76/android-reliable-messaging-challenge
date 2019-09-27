package ir.nilva.pushechallenge.utils

import android.graphics.Color
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:text")
fun TextView.setText(long: Long) {
    text = long.toString()
}

@BindingAdapter("android:text")
fun TextView.setText(int: Int) {
    text = int.toString()
}

@BindingAdapter("android:background")
fun RelativeLayout.setBackground(boolean: Boolean) {
    this.setBackgroundColor(if (boolean) Color.GREEN else Color.RED)
}

@BindingAdapter("android:textColor")
fun TextView.setColor(boolean: Boolean) {
    this.setTextColor(if (boolean) Color.BLACK else Color.WHITE)
}
