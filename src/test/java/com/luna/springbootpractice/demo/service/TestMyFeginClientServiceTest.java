package com.luna.springbootpractice.demo.service;

import com.luna.springbootpractice.SpringbootpracticeApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TestMyFeginClientServiceTest extends SpringbootpracticeApplicationTests {

    @Autowired
    private TestMyFeginClientService testMyFeginClientService;

    @Test
    public void test() {
        String result = testMyFeginClientService.test("test");
        System.out.println(result);
    }
}