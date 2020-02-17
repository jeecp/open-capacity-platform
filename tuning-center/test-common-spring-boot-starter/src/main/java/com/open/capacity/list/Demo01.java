package com.open.capacity.list;

import java.util.Arrays;
/**
 * 数组copy
 */
public class Demo01 {
	public static void main(String[] args) {
		
		Object[] srcObj = new Object[]{1,2,3};
		System.out.println(srcObj.length );
		//Arrays.copyOf 功能实现数组的复制，返回复制后的数组 
		Object[] targetObj = Arrays.copyOf(srcObj, 10); 
		System.out.println(targetObj.length);
		
		
		
		int[] fun = { 1,2,3,4,5,6,7} ;
		System.arraycopy(fun, 3, fun, 0, 4);
		Arrays.stream(fun).forEach( (i) -> System.out.print(i));
	}
}
