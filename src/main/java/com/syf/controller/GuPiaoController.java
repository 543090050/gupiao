package com.syf.controller;

import com.syf.domain.GuPiao;
import com.syf.service.IGuPiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class GuPiaoController {
    @Autowired
    IGuPiaoService guPiaoService;
    @Autowired
    private HttpServletRequest request;


    @RequestMapping("findGP")
    @ResponseBody
    public GuPiao findGP() {
        String id = request.getParameter("id");
        if ("".equals(id) || null == id) {
            System.out.println("findGP-公司code为空，是从详细页面查找的");
            id = (String) request.getSession().getAttribute("parentId");
        }
        GuPiao guPiao = new GuPiao();
        guPiao.setId(id);
        guPiao = guPiaoService.find(guPiao);
        return guPiao;
    }

    @RequestMapping("applyGP")
    public String applyGP() {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        if ("".equals(code) || null == code || "".equals(name) || null == name) {
            return "redirect:/index";
        }
        GuPiao guPiao = new GuPiao();
        guPiao.setId(code);
        guPiao.setName(name);
        guPiaoService.apply(guPiao);
        return "redirect:/index";
    }

    @RequestMapping("deleteGP")
    public String deleteGP() {
        String id = request.getParameter("id");
        GuPiao guPiao = new GuPiao();
        guPiao.setId(id);
        guPiaoService.delete(guPiao);
        return "index";
    }

    @RequestMapping(value = "/queryGP", method = RequestMethod.GET)
    @ResponseBody
    public List queryGP() {
        String queryCode = request.getParameter("queryCode");
        String queryName = request.getParameter("queryName");
        System.out.println(queryCode + " " + queryName);
        GuPiao guPiao = new GuPiao();
        guPiao.setId(queryCode);
        guPiao.setName(queryName);
        List list = guPiaoService.queryForList(guPiao);
        return list;
    }

    @RequestMapping("index")
    public String Hello() {
        return "index";
    }
}
