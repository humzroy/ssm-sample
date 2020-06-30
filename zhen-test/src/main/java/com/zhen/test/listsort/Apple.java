package com.zhen.test.listsort;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName Apple
 * @Description
 * @Author wuhengzhen
 * @Date 2020-06-30 10:56
 * @Version 1.0
 */
@Data
@ToString
public class Apple {

    private String color;

    private int weight;

    public Apple() {

    }


    public Apple(String color) {
        this.color = color;
    }


    public Apple(int weight, String color) {
        this.color = color;
        this.weight = weight;
    }


}
