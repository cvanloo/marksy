package ch.vanloo.marksy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MarksAdapter(private val context: Context, private var marks: List<Mark>) :
    RecyclerView.Adapter<MarksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var mark: Mark? = null

        private val markName: TextView = view.findViewById(R.id.mark_name)
        private val markValue: TextView = view.findViewById(R.id.mark_value)
        private val markWeighting: TextView = view.findViewById(R.id.mark_weighting)

        fun bindMark(context: Context, mark: Mark) {
            this.mark = mark

            val (value, weighting, name) = mark
            markName.text = name
            markValue.text = value.toString()
            markWeighting.text = context.getString(R.string.mark_weighting, weighting)

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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mark = marks[position]
        holder.bindMark(context, mark)
    }

    override fun getItemCount() = marks.size

    fun submitList(marksList: List<Mark>) {
        marks = marksList
    }
}