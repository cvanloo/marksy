package ch.vanloo.marksy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MarksAdapter(private val context: Context, private var marks: List<Mark>) :
    RecyclerView.Adapter<MarksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var mark: Mark? = null

        private val markName: TextView
        private val markValue: TextView
        private val markWeighting: TextView

        init {
            markName = view.findViewById(R.id.mark_name)
            markValue = view.findViewById(R.id.mark_value)
            markWeighting = view.findViewById(R.id.mark_weighting)
        }

        fun bindMark(context: Context, mark: Mark) {
            this.mark = mark
            val (value, weighting, name) = mark
            markName.text = name
            markValue.text = value.toString()
            markWeighting.text = context.getString(R.string.mark_weighting, weighting)
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