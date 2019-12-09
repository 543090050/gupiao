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


    @RequestMapping("findGP")
    public String findGP() {
        GuPiao guPiao = new GuPiao();
        guPiaoService.find(guPiao);
        return "index";
    }

    @RequestMapping("createGP")
    public String createGP() {
        GuPiao guPiao = new GuPiao();
        guPiao.setId("a1");
        guPiao.setName("a2");
        guPiaoService.create(guPiao);
        return "index";
    }

    @RequestMapping("updateGP")
    public String updateGP() {
        GuPiao guPiao = new GuPiao();
        guPiao.setId("3");
        guPiao.setName("bbb");
        guPiaoService.update(guPiao);
        return "index";
    }

    @RequestMapping("deleteGP")
    public String deleteGP() {
        GuPiao guPiao = new GuPiao();
        guPiao.setId("a1");
        guPiaoService.delete(guPiao);
        return "index";
    }

    @RequestMapping("queryGP")
    public String queryGP() {
        GuPiao guPiao = new GuPiao();
        guPiao.setId("1");
        guPiaoService.queryForList(guPiao);
        return "index";
    }

    @RequestMapping("index")
    public String Hello() {
        return "index";
    }
}
