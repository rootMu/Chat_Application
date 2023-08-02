package uk.rootmu.chatapplication.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uk.rootmu.chatapplication.data.local.model.Message
import uk.rootmu.chatapplication.databinding.ItemMessageBinding

class MessageAdapter :
    ListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessageViewHolder(
        ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    private fun isCriteriaMet(position: Int): Boolean {
        val currentMessage = getItem(position)
        val previousMessage = getItem((position - 1).coerceAtLeast(0))

        val mostRecent = position == itemCount - 1
        val otherUser = getItem((position - 1).coerceAtLeast(0)).sender != currentMessage.sender
        val timeout = currentMessage.timeStamp - previousMessage.timeStamp >= 20000

        Log.d(
            "MESSAGE",
            "mostRecent: $mostRecent, otherUser: $otherUser, timeout: $timeout, Time difference is ${currentMessage.timeStamp - previousMessage.timeStamp}"
        )

        return mostRecent
                || otherUser
                || timeout
    }

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message, position: Int) {
            binding.message = message
            binding.criteriaMet = isCriteriaMet(position)
        }

    }

    class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id // Assuming the Message class has a unique identifier 'id'
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem // Assuming the Message class has proper equals() implementation
        }
    }

}