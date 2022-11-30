package project.app.team7cafe.ViewHolder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.app.team7cafe.Interface.ItemClickListener
import project.app.team7cafe.R

class CouponViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var txtCouponId: TextView
    var txtCouponName: TextView
    var btnCopyCoupon: Button
    var itemClickListener: ItemClickListener? = null

    init {
        txtCouponId = itemView.findViewById(R.id.coupon_item_id)
        txtCouponName = itemView.findViewById(R.id.coupon_item_name)
        btnCopyCoupon = itemView.findViewById(R.id.button_copy)
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }
}