package com.simplify.sample.db.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class myInfoController {
//d
    @GetMapping("/makingMindMap")
    public String makingMindMap() {
        return ("/makingMindMap");
    }

    @GetMapping("/searchingFriend")
    public String searchingFriend() {
        return ("/searchingFriend");
    }
}
