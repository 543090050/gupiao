package com.syf.controller;

import com.syf.domain.GuPiao;
import com.syf.service.IGuPiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuPiaoController {
    @Autowired
    IGuPiaoService guPiaoService;

    @RequestMapping("createGP")
    public String createGP() {
        guPiaoService.create(new GuPiao());
        return "index";
    }

    @RequestMapping("index")
    public String Hello() {
        return "index";
    }
}
