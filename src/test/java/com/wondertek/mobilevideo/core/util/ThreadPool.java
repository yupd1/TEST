package com.wondertek.mobilevideo.core.util;

import java.util.LinkedList;

public class ThreadPool extends ThreadGroup{
	/**是否关闭*/
	private boolean isClosed = false;
	/**工作队列*/
	private LinkedList<Runnable> workQueue;
	/**
	 * 构造函数
	 * @param poolSize
	 */
	public ThreadPool(String name , int poolSize){
		//指定ThreadGroup的名称
		super(name);
		//设置是否守护线程池
		setDaemon(true);
		//创建工作队列
		workQueue = new LinkedList<Runnable>();
		//创建线程
		for(int i = 0; i<poolSize; i++){
			new WorkThread(name,i).start();
		}
	}
	/**
	 * 向工作队列中加入一个新任务，由工作线程去执行该任务
	 * @param task
	 */
	public synchronized void execute(Runnable task){
		if(isClosed){
			throw new IllegalStateException();
		}
		if(task != null){
			workQueue.add(task);
			//唤醒一个正在getTask()方法中带任务的工作线程
			notify();
		}
	}
	/**
	 * 获取任务
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized Runnable getTask() throws InterruptedException{
		while(workQueue.size() == 0){
			if(isClosed)return null;
			wait();
		}
		return workQueue.removeFirst();
	}
	/**
	 * 关闭线程池
	 */
	public synchronized void closePool(){
		if(!isClosed){
			//等待所有工作线程关闭
			waitFinish();
			//提示关闭
			isClosed = true;
			//清空工作队列
			workQueue.clear();
			//中断线程池中的所有工作线程
			interrupt();
		}
	}
	/**
	 * 等待所有工作线程关闭
	 */
	public void waitFinish() {
		synchronized(this){
			isClosed = true;
			//唤醒所有还在getTask()方法中等待任务的工作线程
			notifyAll();
		}
		//活动线程值
		Thread[] threads = new Thread[activeCount()];
		//根据活动线程的估计值获取线程组中当前所有活动的工作线程
		int count = enumerate(threads);
		for(int i = 0; i < count; i++){
			try{
				//等待工作线程结束
				threads[i].join();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}
	
	private class WorkThread extends Thread {
		public WorkThread(String name ,int id){
			super(ThreadPool.this,name + "_" + id);
		}
		public void run(){
			while(!isInterrupted()){
				
				Runnable task = null;
				try{
					task = getTask();
				}catch (InterruptedException e){
					e.printStackTrace();
				}
				
				if(task == null)
					return;
				
				try {
					task.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
