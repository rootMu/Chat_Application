package uk.rootmu.chatapplication.utils.databinding

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter

@BindingAdapter("criteriaMet")
fun View.setMessageBackground(criteriaMet: Boolean) {
    this.isActivated = criteriaMet
}

@BindingAdapter("sender")
fun View.setLayoutGravity(sender: Boolean) {

    updateLayoutParams<LinearLayout.LayoutParams> {
        gravity = if(sender) Gravity.START else Gravity.END
    }

}
