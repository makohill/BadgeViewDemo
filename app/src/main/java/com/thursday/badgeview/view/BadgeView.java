package com.thursday.badgeview.view;

/**
 * Created by thursday on 16/5/12.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TabWidget;
import android.widget.TextView;


public class BadgeView extends TextView {

  private boolean mHideOnNull = true;

  public BadgeView(Context context) {
    this(context, null);
  }

  public BadgeView(Context context, AttributeSet attrs) {
    this(context, attrs, android.R.attr.textViewStyle);
  }

  public BadgeView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    init();
  }

  private void init() {
    if (!(getLayoutParams() instanceof LayoutParams)) {
      LayoutParams layoutParams =
          new LayoutParams(
              android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
              android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
              Gravity.RIGHT | Gravity.TOP);
      setLayoutParams(layoutParams);
    }

    //默认颜色
    setTextColor(Color.WHITE);
    setTypeface(Typeface.DEFAULT_BOLD);
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
    setPadding(dip2Px(5), dip2Px(1), dip2Px(5), dip2Px(1));

    // 默认背景
    setBackground(9, Color.parseColor("#d3321b"));

    setGravity(Gravity.CENTER);

    setHideOnNull(true);
    setBadgeCount(0);
  }

  public void setBackground(int dipRadius, int badgeColor) {
    int radius = dip2Px(dipRadius);
    float[] radiusArray = new float[] { radius, radius, radius, radius, radius, radius, radius, radius };

    RoundRectShape roundRect = new RoundRectShape(radiusArray, null, null);
    ShapeDrawable bgDrawable = new ShapeDrawable(roundRect);
    bgDrawable.getPaint().setColor(badgeColor);
    setBackgroundDrawable(bgDrawable);
  }


  public boolean isHideOnNull() {
    return mHideOnNull;
  }


  public void setHideOnNull(boolean hideOnNull) {
    mHideOnNull = hideOnNull;
    setText(getText());
  }


  @Override
  public void setText(CharSequence text, BufferType type) {
    if (isHideOnNull() && (text == null || text.toString().equalsIgnoreCase("0"))) {
      setVisibility(View.GONE);
    } else {
      setVisibility(View.VISIBLE);
    }
    super.setText(text, type);
  }

  public void setBadgeCount(int count) {
    setText(String.valueOf(count));
  }

  public Integer getBadgeCount() {
    if (getText() == null) {
      return null;
    }

    String text = getText().toString();
    try {
      return Integer.parseInt(text);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public void setBadgeGravity(int gravity) {
    FrameLayout.LayoutParams params = (LayoutParams) getLayoutParams();
    params.gravity = gravity;
    setLayoutParams(params);
  }

  public int getBadgeGravity() {
    FrameLayout.LayoutParams params = (LayoutParams) getLayoutParams();
    return params.gravity;
  }

  public void setBadgeMargin(int dipMargin) {
    setBadgeMargin(dipMargin, dipMargin, dipMargin, dipMargin);
  }

  public void setBadgeMargin(int leftDipMargin, int topDipMargin, int rightDipMargin, int bottomDipMargin) {
    FrameLayout.LayoutParams params = (LayoutParams) getLayoutParams();
    params.leftMargin = dip2Px(leftDipMargin);
    params.topMargin = dip2Px(topDipMargin);
    params.rightMargin = dip2Px(rightDipMargin);
    params.bottomMargin = dip2Px(bottomDipMargin);
    setLayoutParams(params);
  }

  public int[] getBadgeMargin() {
    FrameLayout.LayoutParams params = (LayoutParams) getLayoutParams();
    return new int[] { params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin };
  }

  public void incrementBadgeCount(int increment) {
    Integer count = getBadgeCount();
    if (count == null) {
      setBadgeCount(increment);
    } else {
      setBadgeCount(increment + count);
    }
  }

  public void decrementBadgeCount(int decrement) {
    incrementBadgeCount(-decrement);
  }


  public void setTargetView(TabWidget target, int tabIndex) {
    View tabView = target.getChildTabViewAt(tabIndex);
    setTargetView(tabView);
  }


  public void setTargetView(View target) {
    if (getParent() != null) {
      ((ViewGroup) getParent()).removeView(this);
    }

    if (target == null) {
      return;
    }

    if (target.getParent() instanceof FrameLayout) {
      ((FrameLayout) target.getParent()).addView(this);

    } else if (target.getParent() instanceof ViewGroup) {
      ViewGroup parentContainer = (ViewGroup) target.getParent();
      int groupIndex = parentContainer.indexOfChild(target);
      parentContainer.removeView(target);

      FrameLayout badgeContainer = new FrameLayout(getContext());
      ViewGroup.LayoutParams parentLayoutParams = target.getLayoutParams();

      badgeContainer.setLayoutParams(parentLayoutParams);
      target.setLayoutParams(new ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

      parentContainer.addView(badgeContainer, groupIndex, parentLayoutParams);
      badgeContainer.addView(target);

      badgeContainer.addView(this);
    }

  }


  private int dip2Px(float dip) {
    return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5f);
  }
}
