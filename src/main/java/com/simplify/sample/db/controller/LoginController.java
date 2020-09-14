package com.simplify.sample.db.controller;

import com.simplify.sample.db.dto.memberVO;
import com.simplify.sample.db.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.logging.Level;
//Restcontroller로 하면 화면이 글자로 나오는데 그 이유 알기
@Controller
@AllArgsConstructor
public class LoginController {

    @Autowired
    TestService testService;

    @GetMapping("/")
    public String firstview() {
        return "login/logining.html";
    }

    @GetMapping("/newMember")
    public String goToMemberRegister() {
        return "login/registerMember.html";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String test(@RequestParam String id, @RequestParam String pass,Model model) throws Exception {
        return "board/list.html";
    }


    //Requestbody 사용하지 않아도 되는가 ?
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String register(@RequestParam String id, @RequestParam String pass,Model model) throws Exception {

        System.out.println(id);
        System.out.println(pass);
        memberVO mv = new memberVO(id,pass);

        testService.insertMain(mv);
        return "login/logining.html";
    }

    @PostMapping("/checkUser")
    public String ckeckUser(@RequestParam("id") String id, @RequestParam("pass") String pass, ModelMap model, HttpServletRequest req) throws Exception {


        memberVO mv = new memberVO(id, pass);
        memberVO userinfo = testService.checkUserInfo(mv);


        if (userinfo.getId().equals(id) && userinfo.getPass().equals(pass)) {

            //session 생성 후 id 저장
            HttpSession session = req.getSession();
            String sessionid = userinfo.getId();
            session.setAttribute("userid",sessionid);
            return "redirect:/showList";
         } else {
            model.addAttribute("result", 0);
            return "login/logining.html";
        }
    }
}
