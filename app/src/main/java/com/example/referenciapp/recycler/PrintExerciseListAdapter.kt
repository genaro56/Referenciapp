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
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.referenciapp.R
import com.example.referenciapp.ReferenceMenuViewModel
import com.example.referenciapp.database.PrintExercises
import kotlinx.android.synthetic.main.list_selection_view_holder.view.*

class PrintExerciseListAdapter(
    context: Context,
    viewModel: ReferenceMenuViewModel
) : RecyclerView.Adapter<PrintExerciseListAdapter.ExerciseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var exercises = emptyList<PrintExercises>() // cached copy of print exercises
    private val vm = viewModel

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseLabel = itemView.exerciseLabel as TextView
        val exerciseTitle = itemView.exerciseTitle as TextView
        val completionBar = itemView.completionBar as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = inflater.inflate(R.layout.list_selection_view_holder, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val current = exercises[position]
        holder.exerciseLabel.text = "Ejercicio ${position + 1}"
        val title = "Ejercicio ${position + 1}"
        holder.exerciseLabel.text = title
        holder.exerciseTitle.text = current.description
        if (current.completed)
            holder.completionBar.setBackgroundColor(Color.GREEN)

        holder.itemView.setOnClickListener { view ->
            vm.setSelectedId(position)
            vm.setResourceType(0)
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

    internal fun setExercises(exercises: List<PrintExercises>) {
        this.exercises = exercises
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = exercises.size
}