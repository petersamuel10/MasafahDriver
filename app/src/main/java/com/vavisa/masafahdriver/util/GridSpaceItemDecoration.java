package com.vavisa.masafahdriver.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

  private int space;
  // private static int count = 0;

  public GridSpaceItemDecoration(int space) {
    this.space = space;
  }

  @Override
  public void getItemOffsets(
      @NonNull Rect outRect,
      @NonNull View view,
      @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    outRect.bottom = space * 2;
    outRect.right = space;
    outRect.left = space;
  }
}
