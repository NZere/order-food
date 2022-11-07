package project.app.team7cafe.Interface

import android.view.View
import java.text.FieldPosition

interface ItemClickListener {
    fun onClick(view: View, position: Int, isLongClick:Boolean ){}
}