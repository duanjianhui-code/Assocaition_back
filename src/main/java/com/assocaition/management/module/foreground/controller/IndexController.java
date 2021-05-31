package com.assocaition.management.module.foreground.controller;

import cn.hutool.core.lang.Range;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Duanjianhui
 * @date 2021-04-27 9:06 下午
 * @describe
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "view/index";
    }
}
