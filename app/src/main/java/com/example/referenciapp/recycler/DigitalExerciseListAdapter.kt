package com.example.referenciapp.recycler

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.referenciapp.R
import com.example.referenciapp.ReferenceMenuViewModel
import com.example.referenciapp.database.DigitalExercises
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.list_selection_view_holder.view.*

class DigitalExerciseListAdapter(
    context: Context,
    viewModel: ReferenceMenuViewModel
) : RecyclerView.Adapter<DigitalExerciseListAdapter.ExerciseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var exercises = emptyList<DigitalExercises>() // cached copy of print exercises
    private var context = context
    val vm = viewModel

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseLabel = itemView.exerciseLabel as TextView
        val exerciseTitle = itemView.exerciseTitle as TextView
        val completionBar = itemView.completionBar as ImageView
        val exerciseCard = itemView.exerciseCard as MaterialCardView

        init {
            itemView.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    R.id.action_global_exerciseFragment
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = inflater.inflate(R.layout.list_selection_view_holder, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val current = exercises[position]
        val title = "Ejercicio ${position + 1}"
        holder.exerciseLabel.text = title
        holder.exerciseTitle.text = current.description
        if (current.completed) {
            holder.completionBar.setBackgroundColor(context.resources.getColor(R.color.complete))
        }
        else {
            holder.completionBar.setBackgroundColor(Color.LTGRAY)
        }

        holder.itemView.setOnClickListener { view ->
            vm.setSelectedId(position)
            vm.setResourceType(1)
            val bundle = Bundle()
            bundle.putString("title", title)
            when (current.exerciseType) {
                1 -> {
                    view.findNavController().navigate(R.id.action_global_exerciseFragment, bundle)
                }
                2 -> {
                    view.findNavController().navigate(R.id.action_global_exerciseFragment2, bundle)
                }
                else -> {
                    Toast.makeText(
                        holder.itemView.context,
                        "The current exercise does not hold an exercise type yet",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    internal fun setExercises(exercises: List<DigitalExercises>) {
        this.exercises = exercises
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = exercises.size
}
