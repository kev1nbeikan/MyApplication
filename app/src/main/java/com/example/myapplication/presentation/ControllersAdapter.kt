package com.example.myapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.controllers.ControllerData
import com.example.myapplication.databinding.FanctionItemBinding

class ControllersAdapter(private val presenter: MainPresenter) : RecyclerView.Adapter<ControllersAdapter.ControllerViewHolder>() {
    var data: List<ControllerData> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = presenter.itemsCount

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControllerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FanctionItemBinding.inflate(inflater, parent, false)

        return ControllerViewHolder(binding, presenter)
    }

    override fun onBindViewHolder(holder: ControllerViewHolder, position: Int) {
        presenter.onBindItemView(holder, position)
    }

    class ControllerViewHolder(private val binding: FanctionItemBinding, private val presenter: MainPresenter) : RecyclerView.ViewHolder(binding.root), ItemView {
        override fun bindItem(controllerData: ControllerData) {
            with(binding) {
                imageOfFunctionViewInRecycleView.setImageResource(controllerData.imageId)
                nameOfFunctionViewInRecycleView.setText(controllerData.nameId)
                imageOfFunctionViewInRecycleView.setOnClickListener {
                    presenter.onControllerSelected(controllerData)
                }
            }
        }
    }

}


