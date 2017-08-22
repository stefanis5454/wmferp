package westside.wmferp.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ThreadTest implements Runnable{
	
	public static void main(String[] args) throws InterruptedException
	{
		List<Integer> list = new ArrayList<Integer>();
		
		List<Thread> tl = new ArrayList<Thread>();
		
		for(int i =0; i<100; i++)
		{
			ThreadTest tt = new ThreadTest(list);
			Thread thread = new Thread(tt);
			tl.add(thread);
			thread.start();
			
		}
		for(Thread t :tl )
		{
			t.join();
		}
		
		System.out.println(list.size());
		
		
	}

	@Override
	public void run() {
		for(int i =0; i<100; i++)
		{
			synchronized(list){
		           list.add(i);
		       }
			//this.list.add(i);
		}
		
	}
	
	private List<Integer> list;
	
	public ThreadTest(List<Integer> list)
	{
		this.list = list;
	}

}
