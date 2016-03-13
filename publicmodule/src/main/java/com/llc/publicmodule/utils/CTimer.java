package com.llc.publicmodule.utils;

import android.os.SystemClock;

/**
 * Created by Administrator on 2015/12/25.
 */
public class CTimer {
    long nowTime, lastTime;
    public boolean isImmediatelyExecute = false;
    public void execute(int loopTime,OnEcecuteAction onEcecuteAction) {
        while (true) {
            lastTime = System.currentTimeMillis();
            if ((lastTime - nowTime)> loopTime || isImmediatelyExecute) {
                isImmediatelyExecute = false;
                nowTime = lastTime;
                if (onEcecuteAction != null) {
                    onEcecuteAction.execute();
                }
                SystemClock.sleep(100);
            }
        }

    }

    public interface OnEcecuteAction {
        public void execute();
    }

}
