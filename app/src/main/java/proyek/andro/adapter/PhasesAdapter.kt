package proyek.andro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.model.TournamentPhase

class PhasesAdapter(
    private val phases: ArrayList<TournamentPhase>,
) : RecyclerView.Adapter<PhasesAdapter.PhasesViewHolder>() {
    inner class PhasesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tlPhase: TableLayout = itemView.findViewById(R.id.tlPhase)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhasesAdapter.PhasesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_phases_item, parent, false)
        return PhasesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhasesAdapter.PhasesViewHolder, position: Int) {
        val currentItem = phases[position]
        holder.tvName.text = currentItem.name
        holder.tvDate.text = currentItem.start_date + " until " + currentItem.end_date

        holder.tlPhase.removeAllViews()
        val firstRow = TableRow(holder.tlPhase.context)
        val emptySpace = TextView(holder.tlPhase.context)
        val detail = TextView(holder.tlPhase.context)

        emptySpace.text = " "
        emptySpace.background = ContextCompat.getDrawable(holder.tlPhase.context, R.color.header)
        detail.text = "Detail"
        detail.setTextColor(ContextCompat.getColor(holder.tlPhase.context, R.color.white))
        detail.background = ContextCompat.getDrawable(holder.tlPhase.context, R.color.header)

        firstRow.addView(emptySpace)
        firstRow.addView(detail)
        holder.tlPhase.addView(firstRow)

        for (data in currentItem.detail) {
            val row = TableRow(holder.tlPhase.context)

            val arrowRight = ImageView(holder.tlPhase.context)
            arrowRight.setImageResource(R.drawable.ic_keyboard_arrow_right_24)
            arrowRight.layoutParams = TableRow.LayoutParams(
                50,
                50
            )
            arrowRight.setColorFilter(ContextCompat.getColor(holder.tlPhase.context, R.color.white))

            row.addView(arrowRight)

            val cell = TextView(holder.tlPhase.context)
            cell.maxLines = 2
            cell.text = data
            cell.textSize = 13f
            cell.setTextColor(ContextCompat.getColor(holder.tlPhase.context, R.color.white))

            row.addView(cell)

            holder.tlPhase.addView(row)
        }
    }


    override fun getItemCount(): Int {
        return phases.size
    }
}