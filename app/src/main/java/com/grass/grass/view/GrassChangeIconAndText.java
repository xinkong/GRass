package com.grass.grass.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.grass.grass.R;

/**
 * Created by huchao on 2015/12/28.
 */
public class GrassChangeIconAndText extends View {

    private static final int defaultColor = 0xff45c01a;

    /**
     * 要显示的文字
     */
    private String mShowText;
    /**
     * 要显示的Icon
     */
    private Bitmap mIcon;
    /**
     * 字体大小()默认12sp
     */
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics());
    /**
     * 覆盖在Icon的颜色
     */
    private int mBgColor;
    /**
     * 图标的宽高
     */
    private int mIconWidth,mIconHeigh;

    /**
     * 绘制文字的画笔
     */
    private Paint mTextPain;
    /**
     * 绘制icon的pain
     */
    private Paint mIconPain;
    /**
     * 绘制Icon的区域
     */
    private Rect mIconRect;
    /**
     * 文字的区域
     */
    private Rect mTextBounds;
    /**
     * Icon的画布
     */
    private Canvas mCanvas;

    private Bitmap mBitmap;

    private  float mAlpha = 0;

    public GrassChangeIconAndText(Context context) {
        this(context,null);
    }

    public GrassChangeIconAndText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GrassChangeIconAndText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //得到自定义的属性的值
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.GrassChangeIconAndText);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.GrassChangeIconAndText_grass_color:
                    mBgColor = a.getColor(attr,defaultColor);
                    break;
                case R.styleable.GrassChangeIconAndText_grass_text:
                    mShowText = a.getString(attr);
                    break;
                case R.styleable.GrassChangeIconAndText_grass_text_size:
                    mTextSize = (int) a.getDimension(attr,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.GrassChangeIconAndText_grass_icon:
                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    mIcon = drawable.getBitmap();
                    break;
            }
        }
        a.recycle();

        mTextBounds = new Rect();

        mTextPain = new Paint();
        mTextPain.setAntiAlias(true);
        mTextPain.setTextSize(mTextSize);
        //测量文字的宽高
        mTextPain.getTextBounds(mShowText,0,mShowText.length(),mTextBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //初始化Icon的宽,图片为正方形去最小值
        mIconHeigh = mIconWidth = Math.min(mIcon.getWidth(),mIcon.getHeight());

        int left = getMeasuredWidth()/2 - mIconWidth/2;
        int top = getMeasuredHeight()/2 - (mIconWidth + mTextBounds.height())/2;

        mIconRect = new Rect(left,top,left + mIconWidth,top+mIconHeigh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int alpha = (int) Math.ceil((255 * mAlpha));
//        int alpha = (int) (255 * mAlpha);
        canvas.drawBitmap(mIcon, null, mIconRect, null);
        drawIcon(alpha);

        drawTargetText(canvas, alpha);
        drawSourceText(canvas, alpha);
        canvas.drawBitmap(mBitmap,0,0,null);

    }

    /**
     * 绘制不变色文字
     * @param canvas
     * @param alpha
     */
    private void drawSourceText(Canvas canvas, int alpha) {

        int x = getMeasuredWidth()/2-mTextBounds.width()/2;
        int y = mIconRect.bottom + mTextBounds.height();
        mTextPain.setColor(0x555555);
        mTextPain.setAlpha(255-alpha);
        canvas.drawText(mShowText,x,y,mTextPain);

    }

    /**
     * 绘制文字
     * @param canvas
     * @param alpha
     */
    private void drawTargetText(Canvas canvas, int alpha) {

        int x = getMeasuredWidth()/2-mTextBounds.width()/2;
        int y = mIconRect.bottom + mTextBounds.height();
        mTextPain.setColor(mBgColor);
        mTextPain.setAlpha(alpha);
        canvas.drawText(mShowText,x,y,mTextPain);

    }

    /**
     * 绘制icon
     */
    private void drawIcon(int alpha) {

        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        mIconPain = new Paint();
        mIconPain.setAntiAlias(true);
        mIconPain.setDither(true);
        mIconPain.setColor(mBgColor);
        mIconPain.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mIconPain);
        mIconPain.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mIconPain.setAlpha(255);
        mCanvas.drawBitmap(mIcon, null, mIconRect, mIconPain);
    }

    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    private static final String INSTANCE_STATE = "instance_state";
    private static final String STATE_ALPHA = "state_alpha";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(STATE_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATE_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
        } else {
            super.onRestoreInstanceState(state);
        }

    }

}
