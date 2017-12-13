package com.omaoyu.timer;

import android.os.Handler;
import android.os.Message;

import java.util.TimerTask;

/**
 * @author 李梦卿
 */
public class Timer {
	private java.util.Timer mTimer = null;
	private TimerTask mTimerTask = null;
	private Handler mHandler = null;
	private int count = 0;
	private long intervalTime = 1000;
	private boolean isIncreasing = true;

	private boolean isPause = false;

	private int count_ = 0;

	/**
	 * 设置初始值 （默认递增）
	 * */
	public void setCount(int count){
		this.count = count;
	}
	/**
	 * 设置初始值 及递增/递减
	 * */
	public void setCount(int count,boolean isIncreasing){
		this.count = count;
		this.count_ = count;
		this.isIncreasing = isIncreasing;
	}

	public Timer(Handler handler){
		this.mHandler = handler;
	}

	/**
	 * handler what == 0
	 */
	public void startTimer(){
		startTimer(0);
	}

	public Timer(Handler handler, long intervalTime){
		this(handler);
		this.intervalTime = intervalTime;
	}

	/**
	 * @param id Message.what = id
	 */
	public void startTimer(final int id){
		if (mTimer == null) {
			mTimer = new java.util.Timer();
		}

		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					sendMessage(id, count);
					do {
						try {
							Thread.sleep(intervalTime);
						} catch (InterruptedException e) {
						}
					} while (isPause);
					if(isIncreasing){
						count ++;
					} else {
						count --;
					}
				}
			};
		}

		if(mTimer != null && mTimerTask != null )
			mTimer.schedule(mTimerTask, intervalTime, intervalTime);
	}

	public void stopTimer(){
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
		count = count_;
	}

	/**
	 * 暂停/重新启动
	 * */
	public void pause(){
		isPause = !isPause;
	}

	private void sendMessage(int id, Object obj){
		if (mHandler != null) {
			Message message = Message.obtain(mHandler, id, obj);
			mHandler.sendMessage(message);
		}
	}
}