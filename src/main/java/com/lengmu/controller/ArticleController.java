package com.lengmu.controller;

import com.lengmu.dao.ArticleDao;
import com.lengmu.util.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/*
 * @author  lengmu
 * @version 1.0
 */
@RestController
//@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Resource
    ArticleDao articleDao;

    @GetMapping("/")
    public Result queryAll() {
        System.out.println("正在查询文章..");
        List<Map> maps = articleDao.queryAll();
        return Result.success(maps);
    }
}
