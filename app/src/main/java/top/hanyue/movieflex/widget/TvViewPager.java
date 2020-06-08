package top.hanyue.movieflex.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class TvViewPager extends ViewPager {

    public TvViewPager(@NonNull Context context) {
        super(context);
    }

    public TvViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // 禁止翻页,将权限交给leanback来执行.
        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false; // 禁止滑动翻页
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;// 禁止滑动翻页
    }

}