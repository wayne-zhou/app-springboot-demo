package com.example.demo.note.thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;


/**
 * 管道进行线程间通讯
 * @author Administrator
 */
public class PipeTest {
	
	public static void main(String[] args) throws Exception {
		PipeTest pipeTest = new PipeTest();
		pipeTest.test(new PipedWriter(), new PipedReader());
	}
	
	public void test(PipedWriter output, PipedReader input) throws InterruptedException, IOException{
//		output.connect(input);
		input.connect(output); //建立通讯链接
		WriterThred writerThred = new WriterThred(output);
		ReadThred reqdThread = new ReadThred(input);
		reqdThread.start();
		Thread.sleep(1000);
		writerThred.start();
		reqdThread.join();
	}
	
	class WriterThred extends Thread{
		private PipedWriter output;
		
		public WriterThred(PipedWriter output) {
			super();
			this.output = output;
		}

		@Override
		public void run() {
			try {
				for(int i=0;i<10;i++){
					output.write(""+(i+1));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class ReadThred extends Thread{
		private PipedReader input;
		
		public ReadThred(PipedReader input) {
			super();
			this.input = input;
		}

		@Override
		public void run() {
			char[] bytes = new char[20];
			int readLength;
			try {
				StringBuffer data = new StringBuffer(); 
				while((readLength = input.read(bytes)) != -1){
					data.append(new String(bytes,0,readLength));
				}
				System.out.println(data.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
