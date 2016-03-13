package com.llc.publicmodule.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 获取验证码等倒计时button
 * 
 * @author Administrator
 * 
 */
public class CountDownButton extends Button {
	Timer timer;
	int time = 30;
	String text;
	Activity context;
	CountDownButton button = this;
	boolean canClickFlags=true;

	public CountDownButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		timer = new Timer();
		this.context = (Activity) context;
	}

	public CountDownButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		timer = new Timer();
		this.context = (Activity) context;
	}

	public CountDownButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		timer = new Timer();
		this.context = (Activity) context;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction()== MotionEvent.ACTION_UP&&canClickFlags) {
			startCountDown();
		}
		return super.onTouchEvent(event);
	}
	@Override
	public void setOnClickListener(OnClickListener l) {
		// TODO Auto-generated method stub
		super.setOnClickListener(l);

	}

	public void setTime(int time) {
		this.time = time;
	}

	private void startCountDown() {
		canClickFlags=false;
		if (timer==null) {
			timer=new Timer();
		}
		timer.schedule(new TimerTask() {
			int timeTemp=time;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				timeTemp--;
				context.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						button.setText(timeTemp + "s");
					}
				});
				if (timeTemp <= 0) {
					context.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							button.setClickable(true);
							button.setAlpha(100);
							button.setText(text);
							canClickFlags=true;
						}
					});

					this.cancel();
				}

			}
		}, 0, 1000);
		this.setClickable(false);
		this.setAlpha(50);
		text = this.getText().toString();
	}

}
