package com.simplify.sample.db.controller;

import com.simplify.sample.db.dto.allcontentVO;
import com.simplify.sample.db.dto.commentVO;
import com.simplify.sample.db.dto.contentVO;
import com.simplify.sample.db.dto.titleVO;
import com.simplify.sample.db.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    TestService testService;

    @GetMapping("/gotoContent")
    public String gotocontent()throws Exception{
        return "board/makecontent";
    }

    @GetMapping("/showList")
    public String showList(HttpServletRequest req, Model model) throws Exception{
        HttpSession session = req.getSession();
        String user_id = (String)session.getAttribute("userid");
        List<allcontentVO> boardList = testService.getContent();
        model.addAttribute("boardList", boardList);
        return "board/boardlist";
    }

    @PostMapping("/insertContent")
    public String insertcontent(@RequestParam String title, @RequestParam String content, @RequestParam int delpass, HttpServletRequest req, Model model) throws Exception{
            HttpSession session = req.getSession();
            String user_id = (String)session.getAttribute("userid");
/*
            System.out.println("확인용 코드입니다.");
            System.out.println("=======================================================");
            System.out.println("title ="+title);
            System.out.println("title ="+delpass);
            System.out.println("title ="+user_id);
            System.out.println("title ="+content);
            System.out.println("=======================================================");*/
            contentVO con = new contentVO(title,delpass,user_id,content);
            testService.insertContent(con);

            List<allcontentVO> boardList = testService.getContent();

           /* for(int i=0;i<boardList.size();i++){
                System.out.println(boardList.get(i));
            }*/
             model.addAttribute("boardList", boardList);
             return "board/boardlist";
    }

    @PostMapping("/chaneContent")
    public String chaneContent(@RequestParam int id, @RequestParam String title, @RequestParam String content, @RequestParam int delpass, HttpServletRequest req, Model model) throws Exception{
        HttpSession session = req.getSession();
        String user_id = (String)session.getAttribute("userid");

        //게시물 id값 조회로 존재여부 확인 후, 없으면 생성 있으면 수정
        // 1. 모든 id 값 db에서 가져온 후 현재 컨텐츠 id와 비교하기
        // 2. 아니면 where id = id 인것을 셀렉트 한 후 결과 값이 있는지 비교 방법
        // 1. 2 번 해보고 시간비교 해보기

        contentVO con = new contentVO(title, delpass,content,id);
        testService.updateContent(con);
        List<allcontentVO> boardList = testService.getContent();
        model.addAttribute("boardList", boardList);
        return "board/boardlist";
    }

    @GetMapping("/seeDetailContent")
    public String seeDetailContent(@RequestParam String contentId, @ModelAttribute titleVO titleVO, Model model)throws Exception{
        /*modelattribute 개념 확인하기
        System.out.println(titleVO.getid());*/

        contentVO con = new contentVO(Integer.parseInt(contentId));
        contentVO resultCon = new contentVO();

        resultCon =testService.getContentDetail(con);

       /* System.out.println("it is for test");
        System.out.println("============================");
        System.out.println(resultCon.getTitle());
        System.out.println(resultCon.getDelpass());
        System.out.println(resultCon.getContent());
        System.out.println("============================");*/

        model.addAttribute("contentdetail",resultCon);
        model.addAttribute("contentId",contentId);

        return "board/seeContentToChange";
    }

    @GetMapping("/seeContent")
    public String seeContent(@RequestParam String contentId, @ModelAttribute titleVO titleVO, Model model)throws Exception{

        contentVO con = new contentVO(Integer.parseInt(contentId));
        contentVO resultCon = new contentVO();

        resultCon =testService.getContentDetail(con);
        resultCon.setId(Integer.parseInt(contentId));
       /* System.out.println("it is for test");
        System.out.println("============================");
        System.out.println(resultCon.getTitle());
        System.out.println(resultCon.getDelpass());
        System.out.println(resultCon.getContent());
        System.out.println("============================");*/

        model.addAttribute("contentdetail",resultCon);
        model.addAttribute("contentId",contentId);

        return "board/seeContent";
    }

    @PostMapping("/inputComment")
    public String inputComment(@RequestParam String contentId, @RequestParam String commentpaper, @ModelAttribute titleVO titleVO, Model model, HttpServletRequest req)throws Exception{
        HttpSession session = req.getSession();
        String user_id = (String)session.getAttribute("userid");



        //id 값만 넣은 contentVO 객체 생성
        contentVO con = new contentVO(Integer.parseInt(contentId));
        contentVO resultCon = new contentVO();

        //id 값을 통해서 데이터 가져오기
        resultCon =testService.getContentDetail(con);
        resultCon.setId(Integer.parseInt(contentId));

        //전달될 comment 데이터를 db에 넣기
        commentVO commentvo = new commentVO(user_id,Integer.parseInt(contentId),commentpaper);
        testService.insertCommnet(commentvo);

        //board_id를 통해서 모든 comment 가져오기
        contentVO contwo = new contentVO(Integer.parseInt(contentId));
        List<commentVO> listComment = testService.findCommentsByBoardId(contwo);

        System.out.println("댓글 데이터가 잘 전달 되었나?");
        System.out.println(contentId);
        System.out.println(listComment);
        System.out.println("===========================");

        for(int i=0;i<listComment.size();i++){
                System.out.println(listComment.get(i).getUser_id()); }

        //템플릿으로 데이터 보내기
        model.addAttribute("contentdetail",resultCon);
        model.addAttribute("comments",listComment);
        model.addAttribute("contentId",contentId);

        return "board/seeContent";
    }
}
