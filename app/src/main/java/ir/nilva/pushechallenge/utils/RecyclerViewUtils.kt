package ir.nilva.pushechallenge.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

const val DEFAULT_ITEM_MARGIN = 20

data class ItemClickEventArgs<VH : RecyclerView.ViewHolder>(val viewHolder: VH) {
    val position: Int = viewHolder.adapterPosition
    val view: View = viewHolder.itemView
}

abstract class ExtendedViewHolder<in TItem>(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    abstract fun bind(item: TItem)
}

abstract class ClickableViewHolder<in TItem>(containerView: View) :
    ExtendedViewHolder<TItem>(containerView) {

    val clicked = EventHandler<ItemClickEventArgs<RecyclerView.ViewHolder>>()

    init {
        containerView.setOnClickListener { clicked(ItemClickEventArgs(this)) }
    }
}

class MarginItemDecoration(private val spaceHeight: Int = DEFAULT_ITEM_MARGIN) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left = spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}
