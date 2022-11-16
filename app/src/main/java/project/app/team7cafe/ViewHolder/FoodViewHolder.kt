package project.app.team7cafe.ViewHolder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import project.app.team7cafe.Interface.ItemClickListener
import project.app.team7cafe.R

class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private lateinit var itemClickListener: ItemClickListener
    var food_name: TextView? = null
    var food_image: ImageView? = null
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    init {
        food_name = itemView.findViewById(R.id.food_name)
        food_image=itemView.findViewById(R.id.food_image)
        itemView.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        itemClickListener.onClick(v, adapterPosition, false)
    }
}