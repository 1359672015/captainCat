package com.example.captaincat.Widget;

import static com.example.captaincat.Utils.message.PostBoy.logD;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.captaincat.R;

public class CircleViewByImage extends FrameLayout {

    private static final int BACKGROUND = 0;

    private Context mContext;

    private int center;//大小圆心
    private int mInnerCircleRadius;//小圆半径
    private Paint innerPaint;//画内部小圆的画笔
    private float SmallRockerCircleX;//小圆圆心-动态改变
    private float SmallRockerCircleY;//小圆圆心-动态改变
    private float RockerCircleX;//大圆圆心x坐标-不可改变
    private float RockerCircleY;//大圆圆心y坐标-不可y改变
    private float RockerCircleR;//大圆半径

    private boolean isMoveToCenter;//手指是否抬起，即是否需要移动到中心点
    private OnTouchListener mBackgroundListener = new InnerCircleTouchListener();
    private ActionCallback callback;

    private float firstX;//按下去时x的坐标
    private float firstY;//按下去时y的坐标
    private boolean isShortTime;
    private Drawable bgDrawable;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isShortTime = false;//用于判断是否在短时间内点击了小圆两次
        }
    };
    private final int mBackgroundId;
    private final int foregroundId;

    public CircleViewByImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleViewByImage);
        mBackgroundId = array.getResourceId(R.styleable.CircleViewByImage_background, R.mipmap.icon_handlepressed);
      //提供图形用作半径
        foregroundId = array.getResourceId(R.styleable.CircleViewByImage_foreground, R.mipmap.icon_joystick_lock);
        array.recycle();

        initiation();

        setWillNotDraw(false);//如果onDraw不执行，运行该还代码或者重写dispatchDraw替换onDraw
    }

    private void initiation() {
        //放置大圆，如果自定义大圆，可以参考【绘制小圆】的做法，绘制只需要一个半径，和一只画笔，并在onDraw进行简单处理
        ImageView imageViewB = new ImageView(mContext);
        imageViewB.setImageResource(mBackgroundId);
        imageViewB.setMinimumWidth(100);
        imageViewB.setOnTouchListener(mBackgroundListener);
        addView(imageViewB, BACKGROUND);

        bgDrawable = ContextCompat.getDrawable(getContext(),mBackgroundId);
        center = bgDrawable.getMinimumWidth() / 2;//得到大圆半径

        //【绘制小圆】，通过给定的图片来绘制一模一样的小圆
        Drawable drawable = ContextCompat.getDrawable(getContext(),foregroundId);//小圆png图片
        mInnerCircleRadius = drawable.getMinimumWidth() / 2;//通过png图片获取要画的小圆半径 r

        //准备小圆画笔
        innerPaint = new Paint();
        innerPaint.setColor(Color.parseColor("#F9F9F9"));//设置颜色
        innerPaint.setAlpha(78);
        innerPaint.setAntiAlias(true);//设置抗锯齿
        innerPaint.setStyle(Paint.Style.FILL_AND_STROKE);//设置绘制风格，填充模式

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        center = getWidth() / 2;//在这里得到大圆半径，有可能导致绘图时，小圆在左上角而不是在中间

        //大圆圆心坐标
        RockerCircleX = center;
        RockerCircleY = center;

        //第一次绘制小圆时坐标点
        SmallRockerCircleX = center;
        SmallRockerCircleY = center;

        //调节此处，当手指处于控件外围时，小球位置的最大区域，同时也调节灵敏度
        RockerCircleR = center - mInnerCircleRadius;
        //center - mInnerCircleRadius 小圆最大移动距离是内切大圆

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isMoveToCenter) {
            //手指抬起，小圆返回圆心
            canvas.drawCircle(center, center, mInnerCircleRadius, innerPaint);
            //callback.stop();
        } else {
            //手指一动，小圆随手指一动
            canvas.drawCircle(SmallRockerCircleX, SmallRockerCircleY, mInnerCircleRadius, innerPaint);
        }
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        if (isMoveToCenter) {
            //手指抬起，小圆返回圆心
            canvas.drawCircle(center, center, mInnerCircleRadius, innerPaint);
         //   callback.stop();
        } else {
            //手指一动，小圆随手指一动
            canvas.drawCircle(SmallRockerCircleX, SmallRockerCircleY, mInnerCircleRadius, innerPaint);
        }
    }

    private class InnerCircleTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (isSlideOperating){
                int action = event.getAction();
                float x = event.getX();
                float y = event.getY();

                try {
                    gestures(action, x, y);
                } catch (Exception e) {
                    throw new NullPointerException("must implement : "+ActionCallback.class.getSimpleName());
                }
                return true;
            }else {
                return false;
            }

        }
    }

    private void gestures(int action, float x, float y) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                firstX = x;
                firstY = y;
                isShortTime = true;
                handler.sendEmptyMessageDelayed(0, 500);//500ms后再点击则判断为此次动作为非点击动作
            case MotionEvent.ACTION_MOVE:
                // 当触屏区域不在活动范围内
                if (Math.sqrt(Math.pow((RockerCircleX - (int) x), 2)//Δx²
                        + Math.pow((RockerCircleY - (int) y), 2)) //Δy²
                        >= RockerCircleR) {
                    //得到摇杆与触屏点所形成的角度
                    double tempRad = getRad(RockerCircleX, RockerCircleY, x, y);
                    //保证内部小圆运动的长度限制
                    getXY(RockerCircleX, RockerCircleY, RockerCircleR, tempRad);
                    gestureAction(MyAction.OUTSIDE_MOVE, tempRad);
                } else {//如果小球中心点小于活动区域则随着用户触屏点移动即可
                    SmallRockerCircleX = (int) x;
                    SmallRockerCircleY = (int) y;
                    double tempRad = getRad(RockerCircleX, RockerCircleY, x, y);
                    gestureAction(MyAction.INNER_MOVE, tempRad);
                }
                circleMoveToCenter(false);
                break;
            case MotionEvent.ACTION_UP:
                //当释放按键时摇杆要恢复摇杆的位置为初始位置
                circleMoveToCenter(true);
                gestureAction(MyAction.ACTION_UP, 0);
                if (isInnerClick(firstX, firstY, x, y) && isShortTime) {
                    gestureAction(MyAction.ACTION_CLICK, 0);
                }
                isMoveDown=true;
                isMoveLeft=true;
                isMoveRight=true;
                isMoveUp=true;
                break;
        }
    }

    /**
     * 判断手指按下抬起的坐标是否在小圆内
     * @param param :firstX,firstY,endX,endY
     * @return true：在小圆按下抬起；false：不在小圆按下或抬起
     */
    private boolean isInnerClick(float... param) {
        if (param == null) return false;
        for (float v : param) {
            if ((v <= center - mInnerCircleRadius || v >= center + mInnerCircleRadius)) {
                return false;
            }
        }
        return true;
    }

    /***
     * 得到两点之间的弧度
     */
    public double getRad(float px1, float py1, float px2, float py2) {
        //得到两点X的距离
        float x = px2 - px1;
        //得到两点Y的距离
        float y = py1 - py2;
        //算出斜边长
        float xie = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        //得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）
        float cosAngle = x / xie;
        //通过反余弦定理获取到其角度的弧度
        float rad = (float) Math.acos(cosAngle);
        //注意：当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180
        if (py2 < py1) {
            rad = -rad;
        }
        return rad;
    }

    /**
     * @param R       圆周运动的旋转点
     * @param centerX 旋转点X
     * @param centerY 旋转点Y
     * @param rad     旋转的弧度
     */
    public void getXY(float centerX, float centerY, float R, double rad) {
        //获取圆周运动的X坐标
        SmallRockerCircleX = (float) (R * Math.cos(rad)) + centerX;
        //获取圆周运动的Y坐标
        SmallRockerCircleY = (float) (R * Math.sin(rad)) + centerY;
    }

    private void gestureAction(MyAction myAction, double rad) {
        if(rad!=0){
            double tem = -(rad/1.6)*90.0;
            if(tem<0) tem+=360.0;
            double tem2 =  Math.toRadians(tem);
            String a = (""+tem2).substring(0,5);
            logD("当前角度"+a);
            callback.move(Math.cos(tem2),-Math.sin(tem2));
            logD("余弦值:"+Math.cos(tem2)+",正弦值:"+Math.sin(tem2));
        }
        switch (myAction) {
            case OUTSIDE_MOVE:
                judgeDirection(rad);
                break;
            case INNER_MOVE:
                callback.centerMove();
                break;
            case ACTION_CLICK:
                callback.centerClick();
                break;
            case ACTION_UP:
                callback.actionUp();
                break;
        }
    }

    /**
     * 根据角度判断方向
     */
    private  boolean isMoveLeft=true;
    private  boolean isMoveRight=true;
    private  boolean isMoveDown=true;
    private  boolean isMoveUp=true;
    private void judgeDirection(double tempRad) {
        Log.d("当前角度 --> ",tempRad+"");
        double small = 0.75;
        double big = 2.35;

        if (tempRad >= big || tempRad <= -big) {//左
            if (isMoveLeft){
                isMoveLeft=false;
                isMoveRight=true;
                isMoveDown=true;
                isMoveUp=true;
                callback.leftMove();
            }
        } else if (tempRad > -big && tempRad < -small) {//上
            if (isMoveUp){
                isMoveLeft=true;
                isMoveRight=true;
                isMoveDown=true;
                isMoveUp=false;
                callback.forwardMove();
            }
        } else if (tempRad >= -small && tempRad <= small) {//右
            if (isMoveRight){
                isMoveLeft=true;
                isMoveRight=false;
                isMoveDown=true;
                isMoveUp=true;
                callback.rightMove();
            }
        } else {//下
            if (isMoveDown){
                isMoveLeft=true;
                isMoveRight=true;
                isMoveDown=false;
                isMoveUp=true;
                callback.backMove();
            }
        }
    }

    /**
     * 手指抬起后回归中心或手指的xy上
     */
    private void circleMoveToCenter(boolean isMoveToCenter) {
        this.isMoveToCenter = isMoveToCenter;
        invalidate();
    }

    public void setCallback(@NonNull ActionCallback callback) {
        this.callback = callback;
    }

    /**
     * 该接口回调用户操作，某一个方法可能会被频繁的调用，请自行做处理，比如判断时间差距
     */
    public interface ActionCallback {

        void forwardMove();//向前移动
        void move(double  x ,double y);
       // void stop();
        void backMove();//向后移动

        void leftMove();//左边移动

        void rightMove();//右边移动

        void centerMove();//中心小范围移动

        void centerClick();//中心点击

        void actionUp();//手指抬起
    }

    enum MyAction {
        OUTSIDE_MOVE, INNER_MOVE, ACTION_CLICK, ACTION_UP
    }

    private boolean isSlideOperating=true;
    public void setSlideOperating(boolean slideOperating) {
        isSlideOperating = slideOperating;
    }
}
