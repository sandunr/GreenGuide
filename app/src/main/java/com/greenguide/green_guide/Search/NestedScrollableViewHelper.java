package com.greenguide.green_guide.Search;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.RelativeLayout;

import com.greenguide.green_guide.R;
import com.sothree.slidinguppanel.ScrollableViewHelper;

/**
 * Created by sandunr on 3/18/2018.
 */

public class NestedScrollableViewHelper extends ScrollableViewHelper {
    public int getScrollableViewScrollPosition(View scrollableView, boolean isSlidingUp) {
        if (scrollableView instanceof NestedScrollView) {
            if (isSlidingUp) {
                return scrollableView.getScrollY();
            } else {
                NestedScrollView nsv = ((NestedScrollView) scrollableView);
                View child = nsv.getChildAt(0);
                return (child.getBottom() - (nsv.getHeight() + nsv.getScrollY()));
            }
        } else {
            return 0;
        }
    }
}