package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FanctionItemBinding
import com.example.myapplication.presentation.MainPresenter

class ControllersAdapter(val presenter: MainPresenter) : RecyclerView.Adapter<ControllersAdapter.ControllerVeiwHolder>() {
    var data: List<ControllerData> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControllerVeiwHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FanctionItemBinding.inflate(inflater, parent, false)

        return ControllerVeiwHolder(binding)
    }

    override fun onBindViewHolder(holder: ControllerVeiwHolder, position: Int) {
        val controller = data[position]

        with(holder.binding) {
            imageOfFunctionViewInRecycleView.setImageResource(controller.imageId)
            nameOfFunctionViewInRecycleView.setText(controller.nameId)
            imageOfFunctionViewInRecycleView.setOnClickListener {
                presenter.onControllerSelected(controller)
            }
        }
    }

    class ControllerVeiwHolder(val binding: FanctionItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}


