package com.luna.springbootpractice.demo.service;

import com.luna.springbootpractice.demo.myfegin.MyFeginClientRequestMapping;
import com.luna.springbootpractice.demo.myfegin.MyFeginClientScanner;

@MyFeginClientScanner(url = "http://localhost:80")
public interface TestMyFeginClientService {

    @MyFeginClientRequestMapping(value = "/test")
    public String test(String param);

}
