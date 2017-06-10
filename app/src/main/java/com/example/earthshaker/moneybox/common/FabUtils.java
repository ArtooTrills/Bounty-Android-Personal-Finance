package com.example.earthshaker.moneybox.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by earthshaker on 14/5/17.
 */

public class FabUtils {

    private static final long DISPLACEMENT = 200;
    private static final long DURATION_TRANSITION = 600;

    /**
     * Applies animation to Fabs
     *
     * @param fab           applies animation to this
     * @param recyclerView  on scrolling this.
     */
    public static void setRollingFab(final View fab, RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    FabUtils.hideFAB(fab, false);
                } else
                    FabUtils.showFAB(fab);
            }
        });
    }

    public static void hideFAB(View fab, boolean offsetEnabled) {
        long translation = fab.getHeight();
        if (offsetEnabled)
            translation += DISPLACEMENT;
        fab.animate().translationY(translation).setDuration(DURATION_TRANSITION);
    }

    public static void showFAB(View fab) {
        fab.animate().translationY(0).setDuration(DURATION_TRANSITION);
    }

}
