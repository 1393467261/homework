package com.hzw.homework.controller;

import com.google.gson.Gson;
import com.hzw.homework.data_mining.Apriori;
import com.hzw.homework.data_mining.Myfptree2;
import com.hzw.homework.data_mining.Utils;
import com.hzw.homework.entity.Book;
import com.hzw.homework.util.JdbcUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.RequestWrapper;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2018/5/9 13:19
 * Description:
 */
@RestController
public class Controller {

    private static String res = "";

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request){

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        ModelAndView mv = new ModelAndView();

        if (name!=null && password!=null && name.equals(password)){
            request.getSession().setAttribute("name", name);
            mv.setViewName("test");
        }else{
            mv.setViewName("index");
        }

        return mv;
    }

    @RequestMapping("/test")
    public ModelAndView test(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("test");

        return mv;
    }

    @RequestMapping("/add_book")
    public String add_book(HttpServletRequest request){

        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String name = request.getParameter("name");
        float price = Float.valueOf(request.getParameter("price"));
        String id = request.getParameter("id");

        JdbcUtil.addBook(name, id, price);

        return "{\"info\":0}";
    }

    @RequestMapping("/list")
    public List<Book> list(){

        return JdbcUtil.getBookList();
    }

    @RequestMapping("/add_cart")
    public String add_cart(HttpServletRequest request){

        JdbcUtil.addCart(request.getParameter("id"));

        return "{\"info\":0}";
    }

    @RequestMapping("/get_cart")
    public List<Book> get_cart(){

        return JdbcUtil.getCart();
    }

    @RequestMapping("/del_cart")
    public List<Book> del_cart(HttpServletRequest request){

        JdbcUtil.delCart(request.getParameter("id"));

        return JdbcUtil.getCart();
    }

    @RequestMapping("/search_book")
    public List<Book> search_book(HttpServletRequest request){

        return JdbcUtil.searchBook(request.getParameter("title"));
    }

    @RequestMapping("/index")
    public ModelAndView index(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        return mv;
    }

    @RequestMapping("/template")
    public ModelAndView template(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("template");

        return mv;
    }

    @RequestMapping("/run")
    public void run(@RequestParam("file")File file, HttpServletRequest request) throws IOException {

        //on if checked
        String isNeed = request.getParameter("c1");
        Integer support = Integer.valueOf(request.getParameter("support"));
        Integer itemNumber = Integer.valueOf(request.getParameter("itemNumber"));
        String separator = request.getParameter("separator");
        String dir = "G:\\";
        int aprTime = 0;
        String fpRes = "";
        if (isNeed != null && isNeed.equals("on")){
            Integer userIndex = Integer.valueOf(request.getParameter("userIndex"));
            Integer itemIndex = Integer.valueOf(request.getParameter("itemIndex"));
            Utils.mergeItem(dir + file.getName(), separator, userIndex, itemIndex, itemNumber);
            aprTime = Apriori.app(dir + file.getName() + "_1", support);
            fpRes = Myfptree2.app(dir + file.getName() + "_1", support);
        }else{
            aprTime = Apriori.app(dir + file.getName(), support);
            fpRes = Myfptree2.app(dir + file.getName(), support);
        }

        res = fpRes + "Apr执行时间：" + aprTime + "ms";
    }

    @RequestMapping("/get_result")
    public String getResult(){

        return res;
    }
}
