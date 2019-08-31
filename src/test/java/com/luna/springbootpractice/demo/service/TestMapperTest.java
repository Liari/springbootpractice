package com.luna.springbootpractice.demo.service;

import com.luna.springbootpractice.SpringbootpracticeApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TestMapperTest extends SpringbootpracticeApplicationTests {

    @Autowired
    private TestMapper testMapper;

    @Test
    public void getStr() {
        System.out.println(testMapper.getStr("test"));
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}