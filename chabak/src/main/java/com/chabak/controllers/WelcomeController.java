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


    @SneakyThrows
    @RequestMapping(value= {"", "/", "index"})
    public String index(HttpSession session){
        System.out.println("Controller");

        //session.setAttribute("id","fakeId");
        //session.setAttribute("id","id1");
        System.out.println((String)(session.getAttribute("id")));

        return "index";
    }

    @RequestMapping("/header")
    public String header() {
        return "header";
    }

    @RequestMapping("/footer")
    public String footer() {return "footer";}
}
