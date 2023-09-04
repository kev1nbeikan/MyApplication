package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FanctionItemBinding

data class ControllerData(val imageId: Int, val nameId: Int, var isTurned: Boolean = false)

class ControllersAdapter(val presenter: MainPresenterImpl) : RecyclerView.Adapter<ControllersAdapter.FunctionVeiwHolder>() {
    var data: List<ControllerData> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionVeiwHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FanctionItemBinding.inflate(inflater, parent, false)

        return FunctionVeiwHolder(binding)
    }

    override fun onBindViewHolder(holder: FunctionVeiwHolder, position: Int) {
        val controller = data[position]

        with(holder.binding) {
            imageOfFunctionViewInRecycleView.setImageResource(controller.imageId)
            nameOfFunctionViewInRecycleView.setText(controller.nameId)
            imageOfFunctionViewInRecycleView.setOnClickListener {
                presenter.onControllerSelected(controller)
            }
        }
    }

    class FunctionVeiwHolder(val binding: FanctionItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

class ControllersService {

    private var controllers = mutableListOf<ControllerData>(
        ControllerData(
            imageId = R.mipmap.flashlight,
            nameId = R.string.flashLight,
            ),
        ControllerData(
            imageId = R.mipmap.bluetooth,
            nameId = R.string.bluetooth,
        )
    )

    fun getControllers(): MutableList<ControllerData> {
        return controllers
    }

}
