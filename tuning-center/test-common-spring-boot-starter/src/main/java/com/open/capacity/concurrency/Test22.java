package com.open.capacity.concurrency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

/**
 * Guava file
 */
public class Test22 {
	public static void main(String[] args) throws IOException {

  

		long time = System.currentTimeMillis();
		for (int i = 0; i <= 0; i++) {

			Scanner scanner;
			try {
				scanner = new Scanner(new File("D:" + File.separator + "hello.txt"));
				while (scanner.hasNext()) {
					String lines = scanner.next() + "";
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		

		}
		System.out.println("耗时1：" + (System.currentTimeMillis() - time));
		System.out.println("============================================");
		long time2 = System.currentTimeMillis();
		for (int i = 0; i <= 0; i++) {
			
			CharSource charSource = Files.asCharSource(new File("D:" + File.separator + "hello.txt"), Charsets.UTF_8);
			
			try {
				BufferedReader bufferedReader =   charSource.openBufferedStream() ;
				String line;
				while (!StringUtils.isEmpty(line = bufferedReader.readLine())) {
					String lines = line + "";
				}
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("耗时2：" + (System.currentTimeMillis() - time2));
		
		
		//big file throw exception
		System.out.println("============================================");
		long time3= System.currentTimeMillis();
		for (int i = 0; i <= 0; i++) {

			CharSource charSource = Files.asCharSource(new File("D:" + File.separator + "hello.txt"), Charsets.UTF_8);

			try {
				for (String line : charSource.readLines()) {
					String lines = line + "";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("耗时3：" + (System.currentTimeMillis() - time3));

		
		
	}
}
