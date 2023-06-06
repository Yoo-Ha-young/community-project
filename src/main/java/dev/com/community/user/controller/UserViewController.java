//package dev.com.community.user.controller;
//
//
//import dev.com.community.user.model.UserRegisterDTO;
//import dev.com.community.user.service.UserService;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/user")
//public class UserViewController {
//
//    private final UserService userService;
//
//    @PostMapping("/signup-view")
//    public String signup(@RequestBody UserRegisterDTO userRegisterDTO) {
//        userService.save(userRegisterDTO);
//        return "redirect:/login";
//    }
//
//    // 뷰 메서드
//    @GetMapping("/logout-view")
//    public String viewLogout(HttpServletRequest request, HttpServletResponse response){
//        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/login";
//    }
//
//    @GetMapping("/login-view")
//    public String login(){
//        return "login";
//    }
//}
