package com.example.gachonapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testcontroller {


    @GetMapping("test")
    public String test() {
        return "fortest";
    }
}
