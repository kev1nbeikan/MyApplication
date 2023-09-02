package com.example.myapplication

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FanctionItemBinding

data class Function(val imageId: Int, val nameId: Int)

class FunctionAdapter(val mainActivity: MainActivity) : RecyclerView.Adapter<FunctionAdapter.FunctionVeiwHolder>() {
    var data: List<Function> = emptyList()
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
        val function = data[position]


        with(holder.binding) {
            imageOfFunctionViewInRecycleView.setImageResource(function.imageId)
            nameOfFunctionViewInRecycleView.setText(function.nameId)
            imageOfFunctionViewInRecycleView.setOnClickListener {
                mainActivity.setFunction(function)
            }
        }
    }

    class FunctionVeiwHolder(val binding: FanctionItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}


class App : Application() {
    val functionService = FunctionService()
}

class FunctionService {

    private var functions = mutableListOf<Function>(
        Function(
            imageId = R.mipmap.flashlight,
            nameId = R.string.flashLight,
            ),
        Function(
            imageId = R.mipmap.bluetooth,
            nameId = R.string.bluetooth,
        )
    )

    fun getFunctions(): MutableList<Function> {
        return functions
    }

}
