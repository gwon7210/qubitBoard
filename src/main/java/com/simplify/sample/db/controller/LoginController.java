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
       /* model.addAttribute("meaage", id);*/
        return "login/logining.html";
    }

    @PostMapping("/checkUser")
    public String ckeckUser(@RequestParam("id") String id, @RequestParam("pass") String pass, ModelMap model, HttpServletRequest req) throws Exception {
        //Model은 인터페이스라고 하는데 어떻게 model구현체를 사용할 수 있나? (스프링이 만들어 준다고 합니다)
        //@RequestParam에 파라미터를 생략해도 작동하는 이유 ex) RequestParam String id
        //loginging으로 부터 온 데이터는 어떤 형태이어서(json?) RequestBody로 자바화 하지 않아도 되는가? Http 요청의 Body내용을 Java Object로 변환시켜주는 역할을 한다는데 이경우는
        //java로 변환 시키지 않아도 되는가 ?

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
