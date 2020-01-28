package com.syf.controller;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.syf.domain.GuPiao;
import com.syf.domain.QGuPiao;
import com.syf.domain.QueryGuPiao;
import com.syf.domain.XiangXi;
import com.syf.service.IGuPiaoService;
import com.syf.service.IXiangXiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
public class GuPiaoController {
    @Autowired
    IGuPiaoService guPiaoService;
    @Autowired
    IXiangXiService xiangXiService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("findGP")
    @ResponseBody
    public GuPiao findGP() {
        String id = request.getParameter("id");
        if ("".equals(id) || null == id) {
            log.info("findGP-公司code为空，是从详细页面查找的");
            id = (String) request.getSession().getAttribute("parentId");
        }
        GuPiao guPiao = new GuPiao();
        guPiao.setId(id);
        guPiao = guPiaoService.find(guPiao);
        return guPiao;
    }

    @RequestMapping("applyGP")
    public String applyGP() {
        log.debug("GuPiaoController applyGP start");

        String code = request.getParameter("code");
        String name = request.getParameter("name");
        if ("".equals(code) || null == code || "".equals(name) || null == name) {
            return "redirect:/index";
        }
        GuPiao guPiao = new GuPiao();
        guPiao.setId(code);
        guPiao.setName(name);
        guPiaoService.apply(guPiao);
        log.debug("GuPiaoController applyGP end");
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
        log.debug("GuPiaoController queryGP start");
        String queryCode = request.getParameter("queryCode");
        String queryName = request.getParameter("queryName");
        System.out.println(queryCode + " " + queryName);
        GuPiao guPiao = new GuPiao();
        guPiao.setId(queryCode);
        guPiao.setName(queryName);
        List list = guPiaoService.queryForList(guPiao);
        log.debug("GuPiaoController queryGP start");
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
     * @return
     */
    @GetMapping("/pageQueryGP")
    @ResponseBody
    public String pageQueryGP(Pageable pageable) {
        String queryContext = request.getParameter("queryContext");
        Predicate predicate = null;
        String time = "";
        QueryResults<GuPiao> result =null;
        if (StringUtils.isEmpty(queryContext)) {//为空时
            result = guPiaoService.pageQuery(predicate, pageable);
        }else if (isValidDate(queryContext)) {//为时间查询
            time = queryContext;
            result = guPiaoService.pageQueryByTime(null, pageable, time);
        } else if (!StringUtils.isEmpty(queryContext)) {
            predicate = QGuPiao.guPiao.id.like("%" + queryContext + "%").or(QGuPiao.guPiao.name.like("%" + queryContext + "%"));
            result = guPiaoService.pageQuery(predicate, pageable);
        }
        List resultList = new ArrayList();
        for (GuPiao guPiao : result.getResults()) {
            QueryGuPiao queryGuPiao = new QueryGuPiao();
            queryGuPiao.setId(guPiao.getId());
            queryGuPiao.setName(guPiao.getName());
            queryGuPiao.setCount(0);
            XiangXi xiangXi = new XiangXi();
            xiangXi.setGongsi_id(guPiao.getId());
            xiangXi.setTime(time);
            List xxList = xiangXiService.queryForList(xiangXi);
            if (xxList != null) {
                queryGuPiao.setCount(xxList.size());
            }
            resultList.add(queryGuPiao);
        }
        Collections.sort(resultList);
        Map<String, Object> maps = new HashMap<>();
        maps.put("rows", resultList);
        maps.put("total", result.getTotal());
        return JSON.toJSONString(maps);
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

    /**
     * 校验时间格式
     *
     * @param s
     * @return
     */
    public boolean isValidDate(String s) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.parse(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
