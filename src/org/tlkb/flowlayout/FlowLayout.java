package org.tlkb.flowlayout;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

	private List<Line> mLines = new ArrayList<FlowLayout.Line>();
	private Line mCurrentLine;
	private int horizontalSpace = 10;
	private int verticalSpace = 10;

	public FlowLayout(Context context) {
		this(context, null);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mLines.clear();
		mCurrentLine = null;
		// 容器宽度
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int maxWidth = widthSize - getPaddingLeft() - getPaddingRight();

		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);

			if (child.getVisibility() == View.GONE) {
				continue;
			}

			if (mCurrentLine == null) {
				mCurrentLine = new Line(maxWidth, horizontalSpace);
				// 记录行
				mLines.add(mCurrentLine);
			}
			// 判断是否能添加
			if (mCurrentLine.canAdd(child)) {
				mCurrentLine.addView(child);
			} else {
				mCurrentLine = new Line(maxWidth, horizontalSpace);
				// 记录行
				mLines.add(mCurrentLine);
				// 给当前行加入孩子
				mCurrentLine.addView(child);

			}
		}

		int measuredHeight = getPaddingTop() + getPaddingBottom();
		for (int i = 0; i < mLines.size(); i++) {
			measuredHeight += mLines.get(i).height;
		}
		measuredHeight += (mLines.size() - 1) * verticalSpace;
		setMeasuredDimension(widthSize, measuredHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 给行布局
		int top = getPaddingTop();
		for (int i = 0; i < mLines.size(); i++) {
			Line line = mLines.get(i);
			line.layout(getPaddingLeft(), top);
			top += (verticalSpace + line.height);
		}
	}

	public class Line {

		private List<View> mViews = new ArrayList<View>();

		private int maxWidth;// 最大宽度
		private int height;// 高度
		private int space;// 间距
		private int usedWidth;// 已用宽度

		public Line(int maxWidth, int space) {
			this.maxWidth = maxWidth;
			this.space = space;
		}

		// 给view布局
		public void layout(int left, int top) {
			int avgWidth = (int) ((maxWidth - usedWidth) * 1f / mViews.size() + 0.5f);
			
			for (int i = 0; i < mViews.size(); i++) {
				View view = mViews.get(i);

				int viewWidth = view.getMeasuredWidth();
				int viewHeight = view.getMeasuredHeight();

				if (avgWidth > 0) {
					// 重新期望
					view.measure(
							MeasureSpec.makeMeasureSpec(viewWidth + avgWidth, MeasureSpec.EXACTLY),
							MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY));

					viewWidth = view.getMeasuredWidth();
					viewHeight = view.getMeasuredHeight();
				}

				int avgHeight = (int) ((height - viewHeight) / 2f + 0.5f);

				int vLeft = left;
				int vTop = top + avgHeight;
				int vRight = vLeft + viewWidth;
				int vBottom = vTop + viewHeight;
				view.layout(vLeft, vTop, vRight, vBottom);

				left += viewWidth + horizontalSpace;
			}
		}

		/**
		 * 加入view
		 * 
		 * @param view
		 */
		public void addView(View view) {
			int viewWidth = view.getMeasuredWidth();
			int viewHeight = view.getMeasuredHeight();

			if (mViews.size() == 0) {
				if (viewWidth > maxWidth) {
					usedWidth = maxWidth;
					height = viewHeight;
				} else {
					usedWidth = viewWidth;
					height = viewHeight;
				}
			} else {
				usedWidth += (space + viewWidth);
				height = height > viewHeight ? height : viewHeight;
			}

			mViews.add(view);
		}

		/**
		 * 是否能加入view
		 * 
		 * @param view
		 * @return boolean 是否能加入view
		 */
		public boolean canAdd(View view) {
			if (mViews.size() == 0) {
				return true;
			}
			if (usedWidth + space + view.getMeasuredWidth() <= maxWidth) {
				return true;
			}

			return false;
		}
	}
}
