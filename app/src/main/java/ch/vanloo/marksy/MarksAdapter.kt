package ch.vanloo.marksy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.vanloo.marksy.entity.SubjectWithMarks
import java.text.DateFormat

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class MarksAdapter(private val context: Context, private val itemClickListener: ItemClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(MarksComparator()) {

    fun addList(list: List<SubjectWithMarks>) {
        val f = mutableListOf<DataItem>()
        for (s in list) {
            f.add(DataItem.SubjectHeader(s.toSubject))
            for (m in s.marks) {
                f.add(DataItem.MarkItem(m))
            }
        }
        submitList(f)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> MarkViewHolder.create(parent)
            ITEM_VIEW_TYPE_HEADER -> SubjectHeaderViewHolder.create(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MarkViewHolder -> holder.bind(context, item as DataItem.MarkItem)
            is SubjectHeaderViewHolder -> holder.bind(context, item as DataItem.SubjectHeader)
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.MarkItem -> ITEM_VIEW_TYPE_ITEM
            is DataItem.SubjectHeader -> ITEM_VIEW_TYPE_HEADER
        }
    }

    interface ItemClickListener {
        fun onItemClick(item: DataItem)
    }

    class MarkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var mark: DataItem.MarkItem? = null

        private val markName: TextView = view.findViewById(R.id.mark_name)
        private val markValue: TextView = view.findViewById(R.id.mark_value)
        private val markWeighting: TextView = view.findViewById(R.id.mark_weighting)
        private val markDate: TextView = view.findViewById(R.id.mark_date)

        fun bind(context: Context, mark: DataItem.MarkItem) {
            this.mark = mark

            val (_, value, weighting, name, date) = mark.toMark
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

    class SubjectHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var subject: DataItem.SubjectHeader? = null

        private val subjectName: TextView = view.findViewById(R.id.subject_name)

        fun bind(context: Context, subject: DataItem.SubjectHeader) {
            this.subject = subject

            subjectName.text = subject.toSubject.Name
        }

        companion object {
            fun create(parent: ViewGroup): SubjectHeaderViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.subject_header_view, parent, false)
                return SubjectHeaderViewHolder(view)
            }
        }
    }

    class MarksComparator : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            if (oldItem::class != newItem::class)
                return false
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}