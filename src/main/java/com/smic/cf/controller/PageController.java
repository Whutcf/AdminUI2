package com.smic.cf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
/**
 *
 * @ClassName PageController
 * @Description 页面跳转Controller
 * @author cai feng
 * @date 2019年6月22日
 *
 */
public class PageController {

    public static final String BACKSLASH = "\\";
    public static final String API1 = "tgls";


    @GetMapping("/frame")
    public String index() {
        log.info("进入主页面");
        return "frame";
    }

    @GetMapping("/")
    public String index1() {
        log.info("进入主页面");
        return "frame";
    }

    @GetMapping("/toFrame")
    public String toIndex() {
        log.info("进入主页面");
        return "frame";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        log.info("跳转登录页面!");
        return "login";
    }

    @GetMapping("/toRegist")
    public String toRegist() {
        log.info("跳转登录页面!");
        return "regist";
    }


    @GetMapping("/toPermisson")
    public String permissonPage() {
        log.info("您无权访问该页面！");
        return "nopermisson";
    }

    @GetMapping("tgls/{category}/{page}")
    public String page(@PathVariable("category") String category,
                       @PathVariable("page") String page) {
        log.info("进入 {} 页面", page);
        return API1 + BACKSLASH + category + BACKSLASH + page;
    }

    @GetMapping("tgls/{page}")
    public String page1(@PathVariable("page") String page) {
        log.info("进入 {} 页面", page);
        return API1 + BACKSLASH + page;
    }

}
