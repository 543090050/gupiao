package com.syf.controller;

import com.syf.domain.GuPiao;
import com.syf.domain.XiangXi;
import com.syf.service.IGuPiaoService;
import com.syf.service.IXiangXiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class XiangXiController {
    @Autowired
    IXiangXiService xiangXiService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("xiangxi/index")
    public String Index() {
        String GPId = request.getParameter("gpid");
        request.getSession().setAttribute("parentId",GPId);
        return "xiangxi";
    }

    @ResponseBody
    @RequestMapping("findXX")
    public XiangXi find() {
        String id = request.getParameter("id");
        XiangXi xiangXi = new XiangXi();
        xiangXi.setId(id);
        xiangXi = xiangXiService.find(xiangXi);
        return xiangXi;
    }

    @RequestMapping(value = "/queryXX", method = RequestMethod.GET)
    @ResponseBody
    public List queryXX() {
        XiangXi xiangXi = new XiangXi();
//        String parentId = request.getParameter("parentId1");
        String parentId = (String) request.getSession().getAttribute("parentId");
        xiangXi.setGongsi_id(parentId);
        List list = xiangXiService.queryForList(xiangXi);
        if (list.size() == 0) {
            xiangXi.setTime("-");
            xiangXi.setTougu("-");
            list.add(xiangXi);
        }
        return list;
    }

    @RequestMapping("applyXX")
    public String applyXX() {
        String parentId = (String) request.getSession().getAttribute("parentId");
        String time = request.getParameter("time");
        String tougu = request.getParameter("tougu");
        if ("".equals(parentId) || null == parentId) {
            return "redirect:/xiangxi/index";
        }
        XiangXi xiangXi = new XiangXi();

        xiangXi.setTime(time);
        xiangXi.setTougu(tougu);
        xiangXi.setGongsi_id(parentId);
        xiangXiService.apply(xiangXi);
        return "redirect:/xiangxi/index";
    }

    @RequestMapping("deleteXX")
    public String deleteXX() {
        String id = request.getParameter("id");
        XiangXi xiangXi = new XiangXi();
        xiangXi.setId(id);
        xiangXiService.delete(xiangXi);
        return "redirect:/xiangxi/index";
    }
}
