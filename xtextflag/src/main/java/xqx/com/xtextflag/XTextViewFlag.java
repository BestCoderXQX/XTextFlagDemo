package xqx.com.xtextflag;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/*
* @author xqx
* @email djlxqx@163.com
* create 2018/6/26 0026
* description : 标签控件
* 可以设置参数：
* 1、梯形标签 or 三角形标签
* 2、标签背景颜色，标签长度
* 3、标签文字颜色，大小，内容
* 4、标签显示方式
*/

public class XTextViewFlag extends View {

    // 标签显示模式
    public static final int MODE_LEFT_TOP = 0;                                // 左上侧显示
    public static final int MODE_RIGHT_TOP = 1;                               // 右上侧显示
    public static final int MODE_LEFT_BOTTOM = 2;                        // 左下侧显示
    public static final int MODE_RIGHT_BOTTOM = 3;                       // 右下侧显示
    public static final int MODE_LEFT_TOP_TRIANGLE = 4;                      // 左上侧三角填满显示
    public static final int MODE_RIGHT_TOP_TRIANGLE = 5;                     // 右上侧三角填满显示
    public static final int MODE_LEFT_BOTTOM_TRIANGLE = 6;              // 左下侧三角填满显示
    public static final int MODE_RIGHT_BOTTOM_TRIANGLE = 7;             // 右下侧三角填满显示

    public static final int ROTATE_ANGLE = 45;                            // 填充文字旋转角度  ， 默认45度
    private Paint mPaint;
    private TextPaint mTextPaint;
    private float mFlagLength = 40;                                         // 如果是梯形标签的话 ，该值为腰的长度，注意三角标签的话，该值不使用
    private float mTextSize = 16;                                            // 默认文字大小
    private int mSlantedBackgroundColor = Color.TRANSPARENT;             // 默认标签背景颜色
    private int mTextColor = Color.WHITE;                                   // 默认文字颜色
    private String xTextContent = "";                                        // 默认文字
    private int mMode = MODE_LEFT_TOP;                                          // 默认标签显示模式

    public XTextViewFlag(Context context) {
        this(context, null);
    }

    public XTextViewFlag(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public XTextViewFlag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public XTextViewFlag(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制标签
        drawBackground(canvas);
        // 绘制标签文字
        drawText(canvas);
    }



    /**
     * 控件初始化
     * @param attrs
     */
    public void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.xTextViewFlagStyle);

        //设置标签文字大小、文字颜色、标签长度、标签背景色
        mTextSize = array.getDimension(R.styleable.xTextViewFlagStyle_xTextSize, mTextSize);
        mTextColor = array.getColor(R.styleable.xTextViewFlagStyle_xTextColor, mTextColor);
        mFlagLength = array.getDimension(R.styleable.xTextViewFlagStyle_xFlagLength, mFlagLength);
        mSlantedBackgroundColor = array.getColor(R.styleable.xTextViewFlagStyle_xBackgroundColor, mSlantedBackgroundColor);

        // 设置标签文字内容（如果有值话）
        if (array.hasValue(R.styleable.xTextViewFlagStyle_xTextContent)) {
            xTextContent = array.getString(R.styleable.xTextViewFlagStyle_xTextContent);
        }

        // 设置标签显示模式（如果有值的话）
        if (array.hasValue(R.styleable.xTextViewFlagStyle_xFlagMode)) {
            mMode = array.getInt(R.styleable.xTextViewFlagStyle_xFlagMode, 0);
        }
        array.recycle();

        // 设置标签画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mPaint.setAntiAlias(true);
        mPaint.setColor(mSlantedBackgroundColor);

        // 设置文字画笔
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }


    /**
     * 绘制标签背景
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        Path path = new Path();
        int w = getWidth();
        int h = getHeight();

        if (w != h) throw new IllegalStateException("SlantedTextView's width must equal to height");

        switch (mMode) {
            case MODE_LEFT_TOP:
                path = getModeLeftPath(path, w, h);
                break;
            case MODE_RIGHT_TOP:
                path = getModeRightPath(path, w, h);
                break;
            case MODE_LEFT_BOTTOM:
                path = getModeLeftBottomPath(path, w, h);
                break;
            case MODE_RIGHT_BOTTOM:
                path = getModeRightBottomPath(path, w, h);
                break;
            case MODE_LEFT_TOP_TRIANGLE:
                path = getModeLeftTrianglePath(path, w, h);
                break;
            case MODE_RIGHT_TOP_TRIANGLE:
                path = getModeRightTrianglePath(path, w, h);
                break;
            case MODE_LEFT_BOTTOM_TRIANGLE:
                path = getModeLeftBottomTrianglePath(path, w, h);
                break;
            case MODE_RIGHT_BOTTOM_TRIANGLE:
                path = getModeRightBottomTrianglePath(path, w, h);
                break;
        }
        path.close();
        canvas.drawPath(path, mPaint);
        canvas.save();
    }

    // 左上侧显示
    private Path getModeLeftPath(Path path, int w, int h) {
        path.moveTo(w, 0);
        path.lineTo(0, h);
        path.lineTo(0, h - mFlagLength);
        path.lineTo(w - mFlagLength, 0);
        return path;
    }

    private Path getModeRightPath(Path path, int w, int h) {
        path.lineTo(w, h);
        path.lineTo(w, h - mFlagLength);
        path.lineTo(mFlagLength, 0);
        return path;
    }

    private Path getModeLeftBottomPath(Path path, int w, int h) {
        path.lineTo(w, h);
        path.lineTo(w - mFlagLength, h);
        path.lineTo(0, mFlagLength);
        return path;
    }

    private Path getModeRightBottomPath(Path path, int w, int h) {
        path.moveTo(0, h);
        path.lineTo(mFlagLength, h);
        path.lineTo(w, mFlagLength);
        path.lineTo(w, 0);
        return path;
    }

    private Path getModeLeftTrianglePath(Path path, int w, int h) {
        path.lineTo(0,h);
        path.lineTo(w,0);
        return path;
    }

    private Path getModeRightTrianglePath(Path path, int w, int h) {
        path.lineTo(w,0);
        path.lineTo(w,h);
        return path;
    }

    private Path getModeLeftBottomTrianglePath(Path path, int w, int h) {
        path.lineTo(w,h);
        path.lineTo(0,h);
        return path;
    }

    private Path getModeRightBottomTrianglePath(Path path, int w, int h) {
        path.moveTo(0,h);
        path.lineTo(w,h);
        path.lineTo(w,0);
        return path;
    }

    /**
     * 绘制标签文字内容
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        int w = (int) (canvas.getWidth() - mFlagLength / 2);
        int h = (int) (canvas.getHeight() - mFlagLength / 2);

        float[] xy = calculateXY(canvas,w, h);
        float toX = xy[0];
        float toY = xy[1];
        float centerX = xy[2];
        float centerY = xy[3];
        float angle = xy[4];

        // 画布旋转 旋转方向和角度根据mode决定
        canvas.rotate(angle, centerX , centerY );
        // 绘制文字
        canvas.drawText(xTextContent, toX, toY, mTextPaint);
    }

    private float[] calculateXY(Canvas canvas,int w, int h) {
        float[] xy = new float[5];
        Rect rect = null;
        RectF rectF = null;
        int offset = (int) (mFlagLength / 2);
        switch (mMode) {
            case MODE_LEFT_TOP_TRIANGLE:
            case MODE_LEFT_TOP:
                rect = new Rect(0, 0, w, h);
                rectF = new RectF(rect);
                rectF.right = mTextPaint.measureText(xTextContent, 0, xTextContent.length());
                rectF.bottom = mTextPaint.descent() - mTextPaint.ascent();
                rectF.left += (rect.width() - rectF.right) / 2.0f;
                rectF.top += (rect.height() - rectF.bottom) / 2.0f;
                xy[0] = rectF.left;
                xy[1] = rectF.top - mTextPaint.ascent();
                xy[2] = w / 2;
                xy[3] = h / 2;
                xy[4] = -ROTATE_ANGLE;
                break;
            case MODE_RIGHT_TOP_TRIANGLE:
            case MODE_RIGHT_TOP:
                rect = new Rect(offset, 0, w + offset, h);
                rectF = new RectF(rect);
                rectF.right = mTextPaint.measureText(xTextContent, 0, xTextContent.length());
                rectF.bottom = mTextPaint.descent() - mTextPaint.ascent();
                rectF.left += (rect.width() - rectF.right) / 2.0f;
                rectF.top += (rect.height() - rectF.bottom) / 2.0f;
                xy[0] = rectF.left;
                xy[1] = rectF.top - mTextPaint.ascent();
                xy[2] = w / 2 + offset;
                xy[3] = h / 2;
                xy[4] = ROTATE_ANGLE;
                break;
            case MODE_LEFT_BOTTOM_TRIANGLE:
            case MODE_LEFT_BOTTOM:
                rect = new Rect(0, offset, w, h+offset);
                rectF = new RectF(rect);
                rectF.right = mTextPaint.measureText(xTextContent, 0, xTextContent.length());
                rectF.bottom = mTextPaint.descent() - mTextPaint.ascent();
                rectF.left += (rect.width() - rectF.right) / 2.0f;
                rectF.top += (rect.height() - rectF.bottom) / 2.0f;

                xy[0] = rectF.left;
                xy[1] = rectF.top - mTextPaint.ascent();
                xy[2] = w / 2;
                xy[3] = h / 2 + offset;
                xy[4] = ROTATE_ANGLE;
                break;
            case MODE_RIGHT_BOTTOM_TRIANGLE:
            case MODE_RIGHT_BOTTOM:
                rect = new Rect(offset, offset, w+offset, h+offset);
                rectF = new RectF(rect);
                rectF.right = mTextPaint.measureText(xTextContent, 0, xTextContent.length());
                rectF.bottom = mTextPaint.descent() - mTextPaint.ascent();
                rectF.left += (rect.width() - rectF.right) / 2.0f;
                rectF.top += (rect.height() - rectF.bottom) / 2.0f;
                xy[0] = rectF.left;
                xy[1] = rectF.top - mTextPaint.ascent();
                xy[2] = w / 2 + offset;
                xy[3] = h / 2 + offset;
                xy[4] = -ROTATE_ANGLE;
                break;
        }
        return xy;
    }

    public XTextViewFlag setText(String str) {
        xTextContent = str;
        postInvalidate();
        return this;
    }

    public XTextViewFlag setText(int res) {
        String str = getResources().getString(res);
        if (!TextUtils.isEmpty(str)) {
            setText(str);
        }
        return this;
    }

    public String getText() {
        return xTextContent;
    }

    public XTextViewFlag setSlantedBackgroundColor(int color) {
        mSlantedBackgroundColor = color;
        mPaint.setColor(mSlantedBackgroundColor);
        postInvalidate();
        return this;
    }

    public XTextViewFlag setTextColor(int color) {
        mTextColor = color;
        mTextPaint.setColor(mTextColor);
        postInvalidate();
        return this;
    }


    public XTextViewFlag setMode(int mode) {
        if (mMode > MODE_RIGHT_BOTTOM_TRIANGLE || mMode < 0)
            throw new IllegalArgumentException(mode + "is illegal argument ,please use right value");
        this.mMode = mode;
        postInvalidate();
        return this;
    }

    public int getMode() {
        return mMode;
    }

    public XTextViewFlag setTextSize(int size) {
        this.mTextSize = size;
        mTextPaint.setTextSize(mTextSize);
        postInvalidate();
        return this;
    }

    public XTextViewFlag setSlantedLength(int length) {
        mFlagLength = length;
        postInvalidate();
        return this;
    }

}
