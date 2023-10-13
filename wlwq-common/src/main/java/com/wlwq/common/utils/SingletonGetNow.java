package com.wlwq.common.utils;


import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @ClassName: SingletonGetNow
 * @Description: 使用单例获取当前时间，防止时间突然变成第二天
 * @Author Renbowen
 * @DateTime 2019年10月31日 下午6:29:31
 */
public class SingletonGetNow {
	
	private String date = DateUtil.formatDate(new Date());
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	private volatile static SingletonGetNow singleton;  
    private SingletonGetNow(){}
    /**
     * @Title: getSingleton
     * @Description: 单例双重锁模式，保证在多线程模式下获取到的时间的一致性
     * @Author Renbowen
     * @DateTime 2019年10月31日 下午6:31:09
     * @return
     */
    public static SingletonGetNow getSingleton() {  
    if (singleton == null) {  
        synchronized (SingletonGetNow.class) {  
        if (singleton == null) {  
            singleton = new SingletonGetNow();  
        }  
        }  
    }  
    return singleton;  
    }  
	
	
	
	public static void main(String[] args) {
		for (  int i = 1;  i < 10; i++) {
    		new Thread(){
    			public void run() {
    				SingletonGetNow s= SingletonGetNow.getSingleton();
    				System.err.println(s.getDate());
    			};
    			
    		}.start();
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
