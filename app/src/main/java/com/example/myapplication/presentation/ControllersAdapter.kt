package com.example.myapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.domain.ControllerData
import com.example.myapplication.databinding.ContorllerItemBinding

class ControllersAdapter(private val presenter: MainPresenter) : RecyclerView.Adapter<ControllersAdapter.ControllerViewHolder>() {
    override fun getItemCount(): Int = presenter.itemsCount

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControllerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContorllerItemBinding.inflate(inflater, parent, false)

        return ControllerViewHolder(binding, presenter)
    }

    override fun onBindViewHolder(holder: ControllerViewHolder, position: Int) {
        presenter.onBindItemView(holder, position)
    }

    class ControllerViewHolder(private val binding: ContorllerItemBinding, private val presenter: MainPresenter) : RecyclerView.ViewHolder(binding.root), ItemView {
        override fun bindItem(controllerData: ControllerData) {
            with(binding) {
                imageOfControllersItem.setImageResource(controllerData.imageId)
                nameOfControllersItem.setText(controllerData.nameId)
                imageOfControllersItem.setOnClickListener {
                    presenter.onControllerSelected(controllerData)

                }
            }
        }
    }

}


