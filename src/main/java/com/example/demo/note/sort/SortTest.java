package com.example.demo.note.sort;

import java.util.Arrays;



/**
 * 排序
 * @author Administrator
 */
public class SortTest {
	
	public static void main(String[] args) {
		int[] data = new int[]{4,2,5,1,6,2,9,8,7,0};
		//冒泡排序
//		bubbleSort(data);
//		printArray(data);
		
		//选择排序
		selectSort(data);
		printArray(data);
		
		//快速排序
//		quickSort(data, 0, data.length-1);
//		printArray(data);
//		System.out.println(Arrays.toString(data));

	}
	
	/**
	 * 冒泡排序
	 */
	public static void bubbleSort(int[] data){
		for(int i = 0;i < data.length-1; i++){
			for(int j = 0;j < data.length-1-i; j++){
				if(data[j] > data[j+1]){
					swap(data, j, j+1);
				}
			}
		}
	}
	
	/**
	 * 选择排序
     * 与冒泡相比比较次数相同，减少了交换次数
	 */
	public static void selectSort(int[] data){
		for(int i = 0;i < data.length-1; i++){
            int k = i;
			for(int j = i+1; j < data.length; j++){
				if(data[k] > data[j]){
				    k = j;
				}
			}
			if(i != k){
                swap(data, i, k);
            }
		}
	}
	
	/**
	 * 快速排序
	 */
	public static void quickSort(int[] data, int low, int high){
		int start = low;
		int end = high;
		int key = data[start];
		while(end > start){
			//先从后往前比
			while(end > start && data[end] >= key){
				end --;
			}
			if(data[end] <= key){
				swap(data, start, end);
			}
			//从前往后比
			while(end > start && data[start] <= key){
				start ++;
			}
			if(data[start] >= key){
				swap(data, start, end);
			}
		}
		
		//一次遍历完后key的位置就确定了
		//递归遍历key的左右两边
		if(start > low){//递归key的左边
			quickSort(data, low, start-1);
		}
		if(end < high){//递归key的右边
			quickSort(data, end+1, high);
		}
	}
	
	//交换数组中指定的位置的值
	private static void swap(int[] data, int i, int j){
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
	private static void printArray(int[] data){
		for(int a : data){
			System.out.print(a + "  ");
		}
		System.out.println();
	}

}
