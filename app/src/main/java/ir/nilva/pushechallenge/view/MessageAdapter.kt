package ir.nilva.pushechallenge.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ir.nilva.pushechallenge.databinding.ItemMessageBinding
import ir.nilva.pushechallenge.db.data.Message
import ir.nilva.pushechallenge.utils.ClickableViewHolder
import ir.nilva.pushechallenge.utils.EventHandler
import ir.nilva.pushechallenge.utils.ItemClickEventArgs

class MessageAdapter : ListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiff) {

    val itemClicked = EventHandler<ItemClickEventArgs<MessageViewHolder>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessageViewHolder(
        ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ).apply {
        clicked += { itemClicked(ItemClickEventArgs(it.viewHolder as MessageViewHolder)) }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun getItemId(position: Int) = getItem(position).id

    class MessageViewHolder(private val binding: ItemMessageBinding) :
        ClickableViewHolder<Message>(binding.root) {

        override fun bind(item: Message) {
            binding.message = item
        }
    }

    object MessageDiff : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Message, newItem: Message) =
            oldItem.id == newItem.id &&
                    oldItem.data == newItem.data &&
                    oldItem.sendAttempts == newItem.sendAttempts &&
                    oldItem.isSuccessful == newItem.isSuccessful
    }
}