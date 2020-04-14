package com.zhen.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LeetCode1
 * @Description
 * @Author wuhengzhen
 * @Date 2020-01-16 17:11
 * @Version 1.0
 */
public class LeetCode1 {
    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个整数，并返回他们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     * <p>
     * 示例:
     * <p>
     * 给定 nums = [2, 7, 11, 15], target = 9 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]`
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println(Arrays.toString(result));
    }

    /**
     * hashmap的key为某个位置上的值，value为此位置的坐标。
     * 如果target - nums[i]为hashmap的某个key。则找到了两个数的和为target，返回这两个值即可。
     * 如果target - nums[i]不在hashmap的key中。则将其加入hashmap中。
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int value = target - nums[i];
            if (map.containsKey(value)) {
                return new int[]{map.get(value), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }
}
