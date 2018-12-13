package com.example.demo.note.sort;

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
//		selectSort(data);
//		printArray(data);
		
		//快速排序
//		quickSort(data, 0, data.length-1);
//		printArray(data);
//		System.out.println(Arrays.toString(data));

		//归并排序
		mergeSort(data,0, data.length-1);
		printArray(data);

		//插入排序
//		insertSort(data);
//		printArray(data);

		//希尔排序
//		shellSort(data);
//		printArray(data);

		//堆排序
//		heapSort(data);
//		printArray(data);

	}
	
	/**
	 * 冒泡排序 稳定
	 * 时间: O(n^2)
	 * 空间: O(1)
	 */
	public static void bubbleSort(int[] data){
		for(int i = 0; i < data.length-1; i++){
			for(int j = 0; j < data.length-1-i; j++){
				if(data[j] > data[j+1]){
					swap(data, j, j+1);
				}
			}
		}
	}
	
	/**
	 * 选择排序 不稳定
     * 与冒泡相比比较次数相同，减少了交换次数
	 * 时间: O(n^2)
	 * 空间: O(1)
	 */
	public static void selectSort(int[] data){
		for(int i = 0; i < data.length-1; i++){
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
	 * 快速排序 不稳定
	 * 时间: O(n^2)
	 * 	空间: O(nlog2n)
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

	/**
	 * 归并排序 稳定
	 * 分治策略，分到一个元素，再两两合并
	 * 时间: O(nlog2n)
	 * 空间: O(n)
	 */
	public static void mergeSort(int[] data, int left, int right){
		if(left == right){ //只有一个元素时是有序的
			return;
		}

		int middle = left + (right - left)/2;

		//递归使子序列有序
		mergeSort(data, left, middle);
		mergeSort(data, middle+1, right);

		//将两个有序子序列合并
		merge(data, left, right, middle);
	}

	private static void merge(int[] data, int left, int right, int middle){
		int[] temp = new int[right - left +1];
		int i = left,  j = middle + 1, k = 0;
		while (i <= middle && j <= right){
			temp[k++] = data[i] < data[j] ? data[i++] : data[j++];
		}

		//将剩余元素写进temp中
		while(i <= middle){
			temp[k++] = data[i++];
		}

		while(j <= right){
			temp[k++] = data[j++];
		}

		//将临时数组写回原数组
		int index = 0;
		while (left <= right){
			data[left++] = temp[index++];
		}
	}

	/**
	 * 插入排序 稳定
	 * 取出下一个元素，在已经排序的元素序列中从后向前扫描
	 * 时间: O(n^2)
	 * 	空间: O(1)
	 */
	public static void insertSort(int[] data){
		int preIndex , current;
		for(int i = 1; i < data.length; i++){
			preIndex = i - 1;
			current = data[i];
			while (preIndex >= 0 && data[preIndex] > current){
				data[preIndex + 1] = data[preIndex];
				preIndex --;
			}
			if(preIndex + 1 != i){
				data[preIndex + 1] = current;
			}
		}
	}

	/**
	 * 希尔排序 不稳定
	 * 选择一个增量分割成为若干子序列分别进行直接插入排序
	 * 时间: O(n^2)
	 * 	空间: O(1)
	 */
	public static void shellSort(int[] data){
		int gap = 1;
		// 动态定义增量
		while (gap < data.length/3){
			gap = 3*gap + 1;
		}

		for(; gap >0; gap = gap/3){
			for(int i = gap; i < data.length; i++){
				int j = i;
				while(j-gap >= 0 && data[j] < data[j-gap]){
					swap(data, j, j-gap);
					j -= gap;
				}
			}
		}
	}

	/**
	 * 堆排序 不稳定
	 * 构建大(小)顶堆，把堆顶和末尾交换，长度减1后重复
	 * 时间: O(nlog2n)
	 * 	空间: O(1)
	 */
	public static void heapSort(int[] data){
		for(int i =0; i < data.length; i++){

			//每次建堆就可以排除一个元素
			int size = data.length-i;

			// 从数组的尾部开始建堆, 堆顶为最大数
			for(int j = size-1; j >= 0; j--){
				buildMaxHeap(data, j, size);
			}

			swap(data, 0, size-1);
		}
	}

	private static void  buildMaxHeap(int[] data, int curIndex, int size){
		if(curIndex >= size){
			return;
		}

		//把当前父节点位置看成是最大的
		int max = curIndex;

		//数组下标从0开始，所以比完全二叉树公式多+1
		int left = 2 * curIndex + 1;
		int right = 2 * curIndex + 2;

		if(left < size && data[max] < data[left]){
			max = left;
		}

		if(right < size && data[max] < data[right]){
			max = right;
		}

		//如果父节点 < 子节点
		if(max != curIndex){
			swap(data, curIndex, max);
			//递归比较新的子树
			buildMaxHeap(data, max, size);
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
