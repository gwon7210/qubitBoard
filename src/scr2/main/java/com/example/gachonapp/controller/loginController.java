package com.example.gachonapp.controller;


import com.example.gachonapp.mode.memberVO;
import com.example.gachonapp.vaildator.MemberValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class loginController {

//    @Autowired
//    LoginService loginService;

    @GetMapping(value = "/loginpage")
    public String loginPage() {
        return "/loginPage";
    }

    @PostMapping(value = "/checkUser")
    public String checkUser(String id, String pass) {

        //유저 정보 확인 쿼리 작성하기
        // 1 -> 내 화면 창으로
        // 2 -> 다시 로그인 창으로
        return "/checkUser";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registAccount(memberVO memberVO, Model model, BindingResult bindingResult) {

        //modelVO를 통해서 데이터가 왔는지 확인
        System.out.println(memberVO.getId());
        System.out.println(memberVO.getPass());

        //데이터 유효성 검사
        MemberValidator mValidator = new MemberValidator();
        mValidator.validate(memberVO, bindingResult);

        //오류가 있으면 다시 newMember로 리턴합니다.
        if (bindingResult.hasErrors()) {
            System.out.println("error occured !!!");
            System.out.println(bindingResult.getErrorCount());
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/newMember";
        }
        return "redirect:/newMember";
    }

//    @PostMapping("/checkUser")
//    public String ckeckUser(@RequestParam("id") String id, @RequestParam("pass") String pass, ModelMap model, HttpServletRequest req){
//
//        memberVO mv = new memberVO(id, pass);
//
//        //예외처리
//        try{
//            //user_id, pass를 담은 memberVO 객체를 이용하여, 해당 값 count 함수로 카운트 -> 값이 존재하면 1, 존재하지 않으면 0 리턴
//            int userinfo = loginService.checkUserInfo(mv);
//
//            //1일 경우 해당 계정으로 접속
//            if (userinfo==1) {
//
//                //session 생성 후 id 저장
//                HttpSession session = req.getSession();
//                String sessionid = id;
//                session.setAttribute("userid",sessionid);
//                return "redirect:/searchContentByContentWord";
//
//            } else {
//                model.addAttribute("result", 0);
//                //throw new Exception("어떤 오류 발생....");
//                return "login/logining.html";
//            }
//        }catch (Exception e){
//            log.error("회원 검색 중 오류 발생");
//            e.printStackTrace(); //오류 출력(방법은 여러가지)
//            return "/forError";
//        }
//    }

}
