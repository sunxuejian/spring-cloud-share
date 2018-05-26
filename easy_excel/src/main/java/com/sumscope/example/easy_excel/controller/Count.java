package com.sumscope.example.easy_excel.controller;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author xuejian.sun
 * @date 2018/4/12
 */
public class Count extends RecursiveTask<Integer> {

    private int start;

    private int end;

    public Count(int start,int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if((end - start) < 100){
            for(int i = start; i <= end; i++) {
                sum+=i;
            }
        }else {
            int mide = (start + end) /2;
            Count task1 = new Count(start,mide);
            Count task2 = new Count(mide+1,end);
            task1.fork();
            task2.fork();

            Integer join = task1.join();

            Integer join1 = task2.join();

            sum=join+join1;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        Count count = new Count(1,200);
        Integer ss = pool.invoke(count);
        System.out.println(ss);
    }
}
