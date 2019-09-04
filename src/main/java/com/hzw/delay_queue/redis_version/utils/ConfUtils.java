package com.hzw.delay_queue.redis_version.utils;
import	java.util.logging.Logger;
import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.hzw.delay_queue.redis_version.model.QueuesConf;
import org.apache.commons.io.FileUtils;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2019/9/1 18:20
 * Description:
 */
public class ConfUtils {

    private static final String confFileDir = "src/main/java/com/hzw/delay_queue/redis_version/conf/";
    private static final String defaltConfFilePath = "conf.json";
    private static QueuesConf conf = getConf();

    public static QueuesConf getConf(String filePath) {
        return loadConfByJsonFile(filePath);
    }

    public static QueuesConf getConf() {
        if (conf == null) {
            conf = loadConfByJsonFile(null);
        }
        return conf;
    }

    private static QueuesConf loadConfByJsonFile(String filePath) {
        if (filePath == null) {
            filePath = defaltConfFilePath;
        }
        File file = new File(confFileDir + filePath);
        QueuesConf conf = null;

        try {
            String jsonStr = FileUtils.readFileToString(file, "utf-8");
            Gson gson = new Gson();
            conf = gson.fromJson(jsonStr, QueuesConf.class);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return conf;
        }
    }
}
