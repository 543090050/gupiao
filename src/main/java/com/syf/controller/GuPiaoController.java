package com.syf.controller;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.syf.domain.GuPiao;
import com.syf.service.IGuPiaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
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
        log.info("GuPiaoController applyGP start");

        String code = request.getParameter("code");
        String name = request.getParameter("name");
        if ("".equals(code) || null == code || "".equals(name) || null == name) {
            return "redirect:/index";
        }
        GuPiao guPiao = new GuPiao();
        guPiao.setId(code);
        guPiao.setName(name);
        guPiaoService.apply(guPiao);
        log.info("GuPiaoController applyGP end");
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
        log.info("GuPiaoController queryGP start");
        String queryCode = request.getParameter("queryCode");
        String queryName = request.getParameter("queryName");
        System.out.println(queryCode + " " + queryName);
        GuPiao guPiao = new GuPiao();
        guPiao.setId(queryCode);
        guPiao.setName(queryName);
        List list = guPiaoService.queryForList(guPiao);
        log.info("GuPiaoController queryGP start");
        return list;
    }

    @RequestMapping("index")
    public String Hello() {
        return "index";
    }

    /**
     * 单表分页查询
     *
     * @param pageable
     * @param predicate
     * @return
     */
    @GetMapping("/simplePageQuery")
    public String simplePageQuery(@QuerydslPredicate(root = GuPiao.class) Predicate predicate, Pageable pageable) {
        //分页参数为 page；size
        guPiaoService.simplePageQuery(predicate, pageable);
        return "index";
    }

    /**
     * 多表分页查询
     *
     * @param pageable
     * @param predicate
     * @return
     */
    @GetMapping("/multiPageQuery")
    public String multiPageQuery(@QuerydslPredicate(root = GuPiao.class) Predicate predicate, Pageable pageable) {
        guPiaoService.multiPageQuery(predicate, pageable);
        return "index";
    }
}
