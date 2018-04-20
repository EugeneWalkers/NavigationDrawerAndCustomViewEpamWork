package ew.viewsandlayouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Smile extends View {

    private  final String[] mood = {"good", "bad"}; // good/bad
    private int state = 0;
    private Paint main = new Paint();
    private Paint faceFill = new Paint();
    private Paint eyesFill = new Paint();
    private Paint eyeMain = new Paint();
    private Paint mouth = new Paint();
    private Point leftEye;
    private Point rightEye;
    private Point face;
    private int width, height;
    private int eyeOffset;
    private Point[][] stateMouth;
    private Integer[] eyesColor;

    public Smile(Context context) {
        super(context);
//        this.setSaveEnabled(true);
    }

    public Smile(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        this.setSaveEnabled(true);
    }

    public Smile(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        this.setSaveEnabled(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            state = 1 - state;
            eyesFill.setColor(eyesColor[state]);
            this.invalidate();
        }
        return true;
    }

//    private static class SavedState extends BaseSavedState {
//        int value; //this will store the current state from ValueBar
//
//        SavedState(Parcelable superState) {
//            super(superState);
//        }
//
//        private SavedState(Parcel in) {
//            super(in);
//            this.value = in.readInt();
//        }
//
//        @Override
//        public void writeToParcel(Parcel out, int flags) {
//            super.writeToParcel(out, flags);
//            out.writeInt(this.value);
//        }
//
//        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
//            public SavedState createFromParcel(Parcel in) {
//                return new SavedState(in);
//            }
//
//            public SavedState[] newArray(int size) {
//                return new SavedState[size];
//            }
//        };
//    }
//
//    @Nullable
//    @Override
//    protected Parcelable onSaveInstanceState() {
//        Parcelable saveState = super.onSaveInstanceState();
//        SavedState savedState = new SavedState(saveState);
//        savedState.value = state;
//        return savedState;
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        super.onRestoreInstanceState(state);
//        SavedState ss = (SavedState) state;
//        super.onRestoreInstanceState(ss.getSuperState());
//        this.state = ss.value;
//    }



    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState st = new SavedState(super.onSaveInstanceState());
        st.ourState = state;
        return st;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        this.state = ss.ourState;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(face.x, face.y, face.x + width, face.y + height, faceFill);
        canvas.drawRect(face.x, face.y, face.x + width, face.y + height, main);
        canvas.drawRect(leftEye.x - eyeOffset, leftEye.y - eyeOffset, leftEye.x + eyeOffset, leftEye.y + eyeOffset, eyesFill);
        canvas.drawRect(leftEye.x - eyeOffset, leftEye.y - eyeOffset, leftEye.x + eyeOffset, leftEye.y  + eyeOffset, eyeMain);
        canvas.drawRect(rightEye.x - eyeOffset, rightEye.y - eyeOffset, rightEye.x + eyeOffset, rightEye.y + eyeOffset, eyesFill);
        canvas.drawRect(rightEye.x - eyeOffset, rightEye.y - eyeOffset, rightEye.x + eyeOffset, rightEye.y + eyeOffset, eyeMain);
        for (int i=0; i<2; i++){
            canvas.drawLine(stateMouth[state][i].x, stateMouth[state][i].y, stateMouth[state][i+1].x, stateMouth[state][i+1].y, mouth);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        int minW = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth() + 100;
        int w = resolveSizeAndState(minW, widthMeasureSpec, 1);
        setMeasuredDimension(w, w);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        face = new Point(0, 0);
        setStateMouth();
        setEyes();
        setPaintsFace();
    }

    private void setEyes(){
        leftEye = new Point(width / 4, height / 4);
        rightEye = new Point(3 * width / 4, height / 4);
        eyeOffset = width / 10;
        eyesColor = new Integer[2];
        eyesColor[0] = Color.BLUE;
        eyesColor[1] = Color.RED;
    }

    private void setStateMouth(){
        stateMouth = new Point[2][];
        stateMouth[0] = new Point[3];
        stateMouth[1] = new Point[3];
        stateMouth[0][0] = new Point(width/5, 3*height/5);
        stateMouth[0][1] = new Point(width/2, 17*height/20);
        stateMouth[0][2] = new Point(4*width/5, 3*height/5);
        stateMouth[1][0] = new Point(width/5, 17*height/20);
        stateMouth[1][1] = new Point(width/2, 3*height/5);
        stateMouth[1][2] = new Point(4*width/5, 17*height/20);
    }

    private void setPaintsFace(){
        faceFill.setStyle(Paint.Style.FILL);
        faceFill.setColor(Color.YELLOW);
        main.setStyle(Paint.Style.STROKE);
        main.setStrokeWidth(3);
        eyeMain.setStyle(Paint.Style.STROKE);
        eyesFill.setStyle(Paint.Style.FILL);
        eyesFill.setColor(eyesColor[state]);
        mouth.setStrokeWidth(2);
    }
}
