package com.example.demo.note.sort;

import java.util.ArrayList;
import java.util.List;

public class MathTest {
	
	public static void main(String[] args){
//		System.out.println(Math.sqrt(20));
		int totalCount = 105;
		List<int[]> list = getPages(totalCount);
		for(int[] pages : list){
			System.out.println(pages[0]+"~"+pages[1]);
		}
	}
	
	public static List<int[]> getPages(int totalCount){
		int pageSize = 10;
		int currentPage = 1;
		int totalPage = 1;
		List<int[]> list = new ArrayList<>();
		if(totalCount > 0){
			totalPage = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
		}
		while(currentPage <= totalPage){
			list.add(new int[]{(currentPage-1)*pageSize+1, currentPage * pageSize});
			currentPage ++ ;
		}
		return list;
	}

}
