package com.kia.career.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void onlyAlphaAndNumber() {
        assertEquals("abc123", StringUtils.OnlyAlphaAndNumber("abc123<>@$,-"));
    }
    @Test
    void deduplication() {
        List<String> list = Arrays.asList("aaaaabbbb111".split(""));
        LinkedHashSet<String> hashSet = new LinkedHashSet<>();
        hashSet.add("a");
        hashSet.add("b");
        hashSet.add("1");
        assertEquals(hashSet, StringUtils.deduplication(list));
    }
    @Test
    void AlphaNumericSort() {
        List<String> list = Arrays.asList("Aab1".split(""));
        LinkedHashSet<String> hashSet = new LinkedHashSet<>();
        hashSet.add("a");
        hashSet.add("b");
        hashSet.add("A");
        hashSet.add("1");
        assertEquals(list, StringUtils.AlphaNumericSort(hashSet));
    }
    @Test
    void SeperateNumArray(){
        List<String> numberArray = Arrays.asList("123".split(""));
        List<String> sortedlist = Arrays.asList("Aab123".split(""));

        assertEquals(numberArray, StringUtils.SeperateNumArray(sortedlist));
    }

    @Test
    void finalSort(){
        List<String> numberArray = Arrays.asList("123".split(""));
        List<String> atomicList = Arrays.asList("Aab123".split(""));
        List<String> finalArray = Arrays.asList("Aa1b23".split(""));

        assertEquals(finalArray, StringUtils.finalSort(atomicList,numberArray));
    }
}