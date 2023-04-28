package com.scaler.FileMergeSort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSort implements Callable<List<String>> {
    private List<String> linesArrayToSort;
    private ExecutorService executorService;

    public MergeSort(List<String> linesArrayToSort) {
        this.linesArrayToSort = linesArrayToSort;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public List<String> call() throws Exception {
        if(linesArrayToSort.size()==1) return linesArrayToSort;

        int mid = linesArrayToSort.size()/2;

        List<String> leftArray = new ArrayList<>();
        List<String> rightArray = new ArrayList<>();

        for(int i=0;i<mid;i++){
            leftArray.add(linesArrayToSort.get((i)));
        }

        for(int i=mid;i<linesArrayToSort.size();i++){
            rightArray.add(linesArrayToSort.get(i));
        }

        MergeSort leftMergeSort = new MergeSort(leftArray);
        MergeSort rightMergeSort = new MergeSort(rightArray);

        Future<List<String>> futureLeftArray = executorService.submit(leftMergeSort);
        Future<List<String>> futureRightArray = executorService.submit(rightMergeSort);

        ArrayList<String> sortedArray = new ArrayList<>();
        int i=0,j=0;

        List<String> leftSortedArray = futureLeftArray.get();
        List<String> rightSortedArray = futureRightArray.get();

        while(i < leftSortedArray.size() && j < rightSortedArray.size()){
            if(Integer.parseInt(leftSortedArray.get(i)) < Integer.parseInt(rightSortedArray.get(j))){
                sortedArray.add(leftSortedArray.get(i));
                i++;
            }
            else {
                sortedArray.add(rightSortedArray.get(j));
                j++;
            }
        }

        while(i<leftSortedArray.size()){
            sortedArray.add(leftSortedArray.get(i));
            i++;
        }

        while (j<rightSortedArray.size()){
            sortedArray.add(rightSortedArray.get(j));
            j++;
        }

        return sortedArray;
    }
}
