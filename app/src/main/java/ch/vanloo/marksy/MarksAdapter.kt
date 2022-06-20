package ch.vanloo.marksy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.vanloo.marksy.entity.Mark
import java.text.DateFormat

class MarksAdapter(private val context: Context, private val itemClickListener: ItemClickListener) :
    ListAdapter<Mark, MarksAdapter.MarkViewHolder>(MarksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkViewHolder {
        return MarkViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MarkViewHolder, position: Int) {
        val mark = getItem(position)
        holder.bind(context, mark)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(mark)
        }
    }

    interface ItemClickListener {
        fun onItemClick(mark: Mark)
    }

    class MarkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var mark: Mark? = null

        private val markName: TextView = view.findViewById(R.id.mark_name)
        private val markValue: TextView = view.findViewById(R.id.mark_value)
        private val markWeighting: TextView = view.findViewById(R.id.mark_weighting)
        private val markDate: TextView = view.findViewById(R.id.mark_date)

        fun bind(context: Context, mark: Mark) {
            this.mark = mark

            val (_, value, weighting, name, date) = mark
            markName.text = name
            markValue.text = value.toString()
            markWeighting.text = context.getString(R.string.mark_weighting, weighting)

            val formattedDate = DateFormat.getDateInstance().format(date)
            markDate.text = context.getString(R.string.formatted_date, formattedDate)

            // @DUP: Coloring:1
            if (value == 6.0f) {
                markValue.setTextColor(context.getColor(R.color.mark_6_0))
            } else if (value >= 5.5f) {
                markValue.setTextColor(context.getColor(R.color.mark_5_5))
            } else if (value >= 5.0f) {
                markValue.setTextColor(context.getColor(R.color.mark_5_0))
            } else if (value >= 4.5f) {
                markValue.setTextColor(context.getColor(R.color.mark_4_5))
            } else if (value >= 4.0f) {
                markValue.setTextColor(context.getColor(R.color.mark_4_0))
            } else {
                markValue.setTextColor(context.getColor(R.color.mark_failing))
            }
        }

        companion object {
            fun create(parent: ViewGroup): MarkViewHolder {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
                return MarkViewHolder(view)
            }
        }
    }

    class MarksComparator : DiffUtil.ItemCallback<Mark>() {
        override fun areItemsTheSame(oldItem: Mark, newItem: Mark): Boolean {
            return oldItem.Uid == newItem.Uid
        }

        override fun areContentsTheSame(oldItem: Mark, newItem: Mark): Boolean {
            return oldItem == newItem
        }

    }
}