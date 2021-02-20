package com.simplify.sample.db.controller;


import com.simplify.sample.db.service.MakingCharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class searchingFriendController {

    @Autowired
    MakingCharacterService makingCharacterService;

    //캐릭터 만들기
    //1. 자기 사진 이모티콘으로 만들기
    //2. 자기 닮은 사진 올리기
    //만들기 버튼을 누르면 통과하는 api
    @GetMapping("/makingcharacter")
    public String makingMindMap() {
        return ("/makingCharacter");
    }

    //프론트에서 사진이 넘어오면 처리하는 단계
    @PostMapping("/uploadCharacter")
    public String uploadCharacter(HttpServletRequest request, HttpServletResponse response) {
        String characterFile = request.getParameter("file");
        makingCharacterService.
        return ("/uploadCharacter");
    }

    //mbti를 모를 경우 테스트 하기
    @GetMapping("/mbtiTest")
    public String mbtiTest() {
        return ("/mbtiTest");
    }

    //라이프 그래프 그리기
    @GetMapping("/makingLifeGraph")
    public String makingLifeGraph() {
        return ("/makingLifeGraph");
    }

}
