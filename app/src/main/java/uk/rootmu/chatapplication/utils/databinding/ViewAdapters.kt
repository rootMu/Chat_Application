package uk.rootmu.chatapplication.utils.databinding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("criteriaMet")
fun View.setMessageBackground(criteriaMet: Boolean) {
    this.isActivated = criteriaMet
}