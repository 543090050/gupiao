package com.syf.controller;

import com.syf.domain.XiangXi;
import com.syf.service.IXiangXiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        if (!"".equals(GPId) && GPId != null) {
            request.getSession().setAttribute("parentId", GPId);
        }
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
        List list = new ArrayList();
        String parentId = (String) request.getSession().getAttribute("parentId");
        if ("".equals(parentId) || null == parentId) {
            System.err.println("queryXX-parentId=============null");
            request.getSession().removeAttribute("parentId");
            xiangXi.setGongsi_id("-");
            list.add(xiangXi);
            return list;
        }
        xiangXi.setGongsi_id(parentId);
        list = xiangXiService.queryForList(xiangXi);
        if (list.size() == 0) {//为了获取公司ID
            xiangXi.setTime("-");
            xiangXi.setTougu("-");
            list.add(xiangXi);
        }
        return list;
    }

    @RequestMapping("applyXX")
    public String applyXX() {
        String parentId = request.getParameter("parentId");
        if (null == parentId || "".equals(parentId)) {
            parentId = (String) request.getSession().getAttribute("parentId");
        }
        String id = request.getParameter("id");
        String time = request.getParameter("time");
        String tougu = request.getParameter("tougu");
        if ("".equals(parentId) || null == parentId) {
            System.err.println("applyXX-parentId=============null");
            request.getSession().removeAttribute("parentId");
            return "redirect:/xiangxi/index";
        }
        if (null == id || "".equals(id)) {
            id= UUID.randomUUID().toString();
        }
        XiangXi xiangXi = new XiangXi();
        xiangXi.setId(id);
        xiangXi.setTime(time);
        xiangXi.setTougu(tougu);
        xiangXi.setGongsi_id(parentId);
        xiangXiService.apply(xiangXi);
        System.out.println("修改结束后的parentId=" + request.getSession().getAttribute("parentId"));
        request.getSession().setAttribute("parentId", parentId);
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
