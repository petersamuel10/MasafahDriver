package com.vavisa.masafahdriver.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BottomSpaceItemDecoration extends RecyclerView.ItemDecoration {

  private int space;

  public BottomSpaceItemDecoration(int space) {
    this.space = space;
  }

  @Override
  public void getItemOffsets(
      @NonNull Rect outRect,
      @NonNull View view,
      @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);

    outRect.bottom = space;
  }
}
