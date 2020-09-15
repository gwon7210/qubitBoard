package com.simplify.sample.db.controller;

import com.simplify.sample.db.dto.*;
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

    //list 넣기
    @GetMapping("/showList")
    public String showList(HttpServletRequest req, Model model) throws Exception{
        HttpSession session = req.getSession();
        String user_id = (String)session.getAttribute("userid");
        List<allcontentVO> boardList = testService.getContent();
        model.addAttribute("boardList", boardList);
        return "board/boardlist";
    }

    //content 넣기
    @PostMapping("/insertContent")
    public String insertcontent(@RequestParam String title, @RequestParam String content, @RequestParam int delpass, HttpServletRequest req, Model model) throws Exception{
            HttpSession session = req.getSession();
            String user_id = (String)session.getAttribute("userid");

            contentVO con = new contentVO(title,delpass,user_id,content);
            testService.insertContent(con);

            List<allcontentVO> boardList = testService.getContent();

             model.addAttribute("boardList", boardList);
             return "board/boardlist";
    }

    //content 수정
    //???수정 api로 바꾸는 법???
    @PostMapping("/changeContent")
    public String changeContent(@RequestParam int id, @RequestParam String title, @RequestParam String content, @RequestParam int delpass, HttpServletRequest req, Model model) throws Exception{
        HttpSession session = req.getSession();
        String user_id = (String)session.getAttribute("userid");




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

        model.addAttribute("contentdetail",resultCon);
        model.addAttribute("contentId",contentId);

        return "board/seeContentToChange";
    }

    @GetMapping("/seeContent")
    public String seeContent(@RequestParam String contentId,@RequestParam String contentMaker, @ModelAttribute titleVO titleVO, Model model, HttpServletRequest req)throws Exception{
        HttpSession session = req.getSession();
        String user_id = (String)session.getAttribute("userid");

        contentVO con = new contentVO(Integer.parseInt(contentId));

        contentVO resultCon =testService.getContentDetail(con);
        resultCon.setId(Integer.parseInt(contentId));
        resultCon.setUser_id(contentMaker);

        //board_id를 통해서 모든 comment 가져오기
        contentVO contwo = new contentVO(Integer.parseInt(contentId));
        List<commentVO> listComment = testService.findCommentsByBoardId(contwo);

        System.out.println(resultCon);

        model.addAttribute("contentdetail",resultCon);
        model.addAttribute("comments",listComment);
        model.addAttribute("contentId",contentId);
        model.addAttribute("currentUserid",user_id);
        model.addAttribute("contentUserId",resultCon.getUser_id());

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

 /*       for(int i=0;i<listComment.size();i++){
                System.out.println(listComment.get(i).getUser_id()); }
*/

        //템플릿으로 데이터 보내기
        model.addAttribute("contentdetail",resultCon);
        model.addAttribute("comments",listComment);
        model.addAttribute("contentId",contentId);

        return "board/seeContent";
    }

    //content, title을 통해 content 검색하기
    @GetMapping("/searchContentByContentWord")
    public String searchContentByContentWord(@RequestParam String word, Model model) throws Exception{

        List<contentVO> conList = testService.searchContentByContentWord(word);
        System.out.println(conList);

            for(int i=0;i<conList.size();i++){
                System.out.println(conList.get(i).getUser_id()); }

        System.out.println("검색한 결과가 잘 나왔나 ?");
        for(int i=0;i<conList.size();i++){
            System.out.println(conList.get(i).getId());
            System.out.println(conList.get(i).getTitle());
            System.out.println(conList.get(i).getContent());
            System.out.println(conList.get(i).getDelpass());
            System.out.println(conList.get(i).getUser_id());
        }

        model.addAttribute("boardList", conList);
        return "board/boardlist";
    }

    //content 삭제하기
    //??? DeleteMapping 으로 받으려 시도했지만 템플렛에서 method가 post 와 get 둘 밖에 없음
    @PostMapping("deleteContent")
    public String deleteContent(@RequestParam int contentId, @RequestParam String contentUserId, HttpServletRequest req, Model model) throws Exception {

        //세션을 통해서 접속중인 user_id 생성
        HttpSession session = req.getSession();
        String user_id = (String)session.getAttribute("userid");

        int id = contentId;
        System.out.println(id);
        String writerId = testService.compareWriterAndSessionUser(id);

        // ==와 equals 정확히 알
        //contentUserId를 받는 것은 해킹의 위험이 있다. contentId를 통해서 조회 하는 방법 사용하기기
        if(writerId==user_id){
            testService.deleteContentById(contentId);
        }else{
            //템플릿에서 1차 검증을 끝낸 후 2차 검증
            System.out.println(contentUserId);
            System.out.println("user_id :" + user_id);
            System.out.println("writerId :" + writerId);
            System.out.println("다른 사용자 입니다.");
        }

        List<allcontentVO> boardList = testService.getContent();
        model.addAttribute("boardList", boardList);
        return "board/boardlist";
    }
}
