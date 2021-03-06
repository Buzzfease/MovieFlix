package top.hanyue.movieflex.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mRightSpace;
    private int mTopSpace;

    public SpaceItemDecoration(int rightSpace, int topSpace) {
        this.mRightSpace = rightSpace;
        this.mTopSpace = topSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.right = mRightSpace;
        outRect.top = mTopSpace;
    }

}