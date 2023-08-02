package uk.rootmu.chatapplication.utils.databinding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.rootmu.chatapplication.data.local.model.Message
import uk.rootmu.chatapplication.ui.adapters.MessageAdapter

//Example way to create Adapter using databinding but currently unused

@BindingAdapter("messages")
fun RecyclerView.createMessageAdapter(messages: StateFlow<List<Message>>) {
    this.adapter = MessageAdapter().apply {
        submitList(messages.value)
    }

    findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
        messages.collect {
            (this@createMessageAdapter.adapter as? MessageAdapter)?.submitList(it)
        }
    }
}