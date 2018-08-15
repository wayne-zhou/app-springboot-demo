package com.example.demo.note.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSortOfComparatorTest {

	public static void main(String[] args) {
		List<String> data = new ArrayList<String>();
		data.add("5");
		data.add("2");
		data.add("2");
		data.add("4");
		Collections.sort(data, new MyCompartor());
		for(String s : data){
			System.out.print(s + "  ");
		}
	}
	
	
	static class MyCompartor implements Comparator<String>{
		@Override
		public int compare(String o1, String o2) {
			int par1 = 0;
			int par2 = 0;
			try {
				par1 = Integer.parseInt(o1);
				par2 = Integer.parseInt(o2);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return par1 - par2;
		}
	}
}
