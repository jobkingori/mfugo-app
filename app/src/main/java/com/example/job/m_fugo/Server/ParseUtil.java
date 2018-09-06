package com.example.job.m_fugo.Server;

import org.json.JSONObject;

/**
 * Created by JOB on 5/27/2017.
 */



    public class ParseUtil {
        public static boolean contains(JSONObject jsonObject, String key) {
            return jsonObject != null && !key.matches("false") && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
        }
    }

