package project.app.team7cafe.Interface;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerItemTouchHelperListener
{
    void onSwipped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
