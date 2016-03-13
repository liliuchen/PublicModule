package com.llc.publicmodule.views;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

/**
 * Created by Administrator on 2015/9/8.
 */
public class ShakeEditText extends EditText {

    public ShakeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param counts 1秒钟颤抖的次数
     */
    public void execSaking(Activity activity,int counts){

        Animation translateAnimation=  new TranslateAnimation(0,10,0,0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        this.startAnimation(translateAnimation);
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(1000);
    }


}
