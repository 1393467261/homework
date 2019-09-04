package com.hzw.homework.data_mining;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2019/1/9 16:44
 * Description:
 */
public class Utils {

    public static void mergeItem(String sourceFile, String separator, int user, int item, int rows) throws IOException {
        BufferedReader source = new BufferedReader(new FileReader(sourceFile));
        FileWriter target = new FileWriter(new File(sourceFile + "_1"));
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        String line = "";
        String[] split;
        int userId;
        int itemId;

        while ((line = source.readLine()) != null){
            split = line.split(separator);
            userId = Integer.parseInt(split[user]);
            itemId = Integer.parseInt(split[item]);
            if (map.containsKey(userId) && map.get(userId).size() < 26){
                map.get(userId).add(itemId);
            }else{
                ArrayList<Integer> list = new ArrayList<>();
                list.add(itemId);
                map.put(userId, list);
            }
        }

        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            StringBuilder items = new StringBuilder();
            for (Integer id : entry.getValue()){
                items.append(id);
                items.append("\t");
            }
            if (rows == 0){
                break;
            }
            target.write(items.toString() + "\n");
            rows--;
        }

        target.flush();
    }
}
