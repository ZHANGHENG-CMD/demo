package com.example.demo.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * ThreadLocal
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/8/26 14:08
 */
public class ThreadLocalTest {

    static ThreadLocal<String> localVar = new ThreadLocal<>();

    static void print(String str) {
        //打印当前线程中本地内存中本地变量的值
        System.out.println(str + " :" + localVar.get());
        //清除本地内存中的本地变量
        localVar.remove();
    }

    public static void main(String[] args) {
        //创建线程池对象
        ExecutorService service = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                5,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNameFormat("2121").build());
        //创建Runnable实例对象
        Runnable r = () -> {
            //设置线程1中本地变量的值
            localVar.set("localVar1");
            //调用打印方法
            print("thread1");
            //打印本地变量
            System.out.println("after remove : " + localVar.get());
        };
        Runnable r1 = () -> {
            //设置线程1中本地变量的值
            localVar.set("localVar2");
            //调用打印方法
            print("thread2");
            //打印本地变量
            System.out.println("after remove : " + localVar.get());
        };

        //自己创建线程对象的方式
        //Thread t = new Thread(r);
        //t.start(); ---> 调用MyRunnable中的run()

        //从线程池中获取线程对象,然后调用MyRunnable中的run()
        service.submit(r);
        //再获取个线程对象，调用MyRunnable中的run()
        service.submit(r1);

      /*  Thread t1  = new Thread(new Runnable() {
            @Override
            public void run() {
                //设置线程1中本地变量的值
                localVar.set("localVar1");
                //调用打印方法
                print("thread1");
                //打印本地变量
                System.out.println("after remove : " + localVar.get());
            }
        });

        Thread t2  = new Thread(new Runnable() {
            @Override
            public void run() {
                //设置线程1中本地变量的值
                localVar.set("localVar2");
                //调用打印方法
                print("thread2");
                //打印本地变量
                System.out.println("after remove : " + localVar.get());
            }
        });

        t1.start();
        t2.start();*/
    }
}
