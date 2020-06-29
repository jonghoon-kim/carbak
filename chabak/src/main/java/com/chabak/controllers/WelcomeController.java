package com.chabak.controllers;


import com.chabak.services.MemberService;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class WelcomeController {
    @Autowired
    MemberService memberService;


    //    @SneakyThrows
//    private Connection getConnection() {
//        String url =
//                "jdbc:oracle:thin:@localhost:1521:xe";
//        String user = "hr2";
//        String password = "1";
//        Class.forName("oracle.jdbc.driver.OracleDriver");
//        return DriverManager.getConnection(url,user,password);
//
//
//    }
    @SneakyThrows
    @RequestMapping(value="/")
    public String upload(HttpSession session){
        System.out.println("Controller");

        //session.setAttribute("id","fakeId");
       session.setAttribute("id","id1");
        System.out.println((String)(session.getAttribute("id")));

        return "index";
    }
}
