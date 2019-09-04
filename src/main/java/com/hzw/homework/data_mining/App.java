package com.hzw.homework.data_mining;

import java.io.IOException;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2019/1/9 17:05
 * Description:
 */
public class App {

    public static void main(String[] args) throws IOException {

        //以用户为单位合并其观看的所有电影
        String target = "G:\\item.data_1";
        String app = Myfptree2.app(target, 10);
        System.out.println(app);
    }
}
