package com.syf.controller;

import com.syf.domain.GuPiao;
import com.syf.service.IGuPiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GuPiaoController {
    @Autowired
    IGuPiaoService guPiaoService;
    @Autowired
    private HttpServletRequest request;


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
        String id = request.getParameter("id");
        GuPiao guPiao = new GuPiao();
        guPiao.setId(id);
        guPiaoService.delete(guPiao);
        return "index";
    }


    @RequestMapping("initQueryGP")
    public String initQueryGP() {

        return "index";
    }

    @RequestMapping(value = "/queryGP", method = RequestMethod.GET)
    @ResponseBody
    public List queryGP() {


        String queryCode = request.getParameter("queryCode");
        String queryName = request.getParameter("queryName");

//        GuPiao guPiao = new GuPiao();
//        guPiao.setId("1");
//        guPiaoService.queryForList(guPiao);
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            GuPiao guPiao = new GuPiao();
            guPiao.setId(i + "");
            guPiao.setName("name " + i);
            list.add(guPiao);
        }
        System.out.println(queryCode + " " + queryName);
        return list;
    }

    @RequestMapping("index")
    public String Hello() {
        return "index";
    }
}
