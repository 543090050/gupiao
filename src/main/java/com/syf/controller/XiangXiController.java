package com.syf.controller;

import com.syf.domain.GuPiao;
import com.syf.domain.XiangXi;
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
public class XiangXiController {
    @Autowired
    IGuPiaoService guPiaoService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("xiangxi/index")
    public String Index() {
        String GPId = request.getParameter("gpid");
        return "xiangxi";
    }

    @RequestMapping("findXX")
    public XiangXi find() {
        XiangXi xiangXi = new XiangXi();
        xiangXi.setTime("123");
        xiangXi.setTougu("asd");
        return xiangXi;
    }

    @RequestMapping(value = "/queryXX", method = RequestMethod.GET)
    @ResponseBody
    public List queryXX() {
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            XiangXi xiangXi = new XiangXi();
            xiangXi.setTime(i + "");
            xiangXi.setTougu("投股 "+i);
            list.add(xiangXi);
        }
        return list;
    }
}
