package com.zhen.test.listsort;


import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;

import static java.util.Comparator.comparing;

/**
 * @ClassName TestCollectionSort
 * @Description
 * @Author wuhengzhen
 * @Date 2020-06-30 10:59
 * @Version 1.0
 */
public class TestCollectionSort {


    private static ArrayList<Apple> getList() {
        ArrayList<Apple> inventory = Lists.newArrayList(
                new Apple(10, "red"),
                new Apple(5, "red"),
                new Apple(1, "green"),
                new Apple(15, "green"),
                // new Apple(15, "yellow"),
                new Apple(2, "red"));
        return inventory;
    }


    public static void main(String[] args) {
        /**
         * 顺序排序
         */
        ArrayList<Apple> inventory = getList();
        System.out.println("排序前：" + inventory);

        // 1、传递代码，函数式编程
        inventory.sort(new AppleComparator());
        System.out.println("1:" + inventory);

        // 2、匿名内部类
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        System.out.println("2:" + inventory);
        // 3、使用Lambda表达式
        inventory.sort((a, b) -> a.getWeight() - b.getWeight());
        System.out.println("3:" + inventory);
        // 4、使用Comparator的comparing
        Comparator<Apple> comparing = comparing((Apple a) -> a.getWeight());
        inventory.sort(comparing((Apple a) -> a.getWeight()));
        System.out.println("4:" + inventory);
        //或者等价于
        inventory.sort(comparing(Apple::getWeight));
        System.out.println("5:" + inventory);


        /**
         * 逆序排序
         */
        // 1、 根据重量逆序排序
        inventory.sort(comparing(Apple::getWeight).reversed());
        System.out.println("**********逆序排序，根据重量后:" + inventory);
        // 2、如果两个苹果的重量一样重，怎么办？那就再找一个条件进行排序
        inventory.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        System.out.println("**********逆序排序，根据重量、颜色后:" + inventory);

    }


}
