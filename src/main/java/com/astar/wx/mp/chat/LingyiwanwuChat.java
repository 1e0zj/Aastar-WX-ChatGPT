package com.astar.wx.mp.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.astar.wx.mp.utils.JsonUtils;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 1e0zj
 * @description LingyiwanwuChat
 * @since 2024/3/22 11:07
 */
@Slf4j
@Service
public class LingyiwanwuChat implements AiChat{


    String chatEndpoint = "https://api.lingyiwanwu.com/v1/chat/completions";

    String apiKey = "fd6d1abe4edb479eb12958ba6e6fb19e";

    @Override
    public String chat(String txt) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", "yi-34b-chat-0205");
        List<Map<String, String>> dataList = new ArrayList<>();
        dataList.add(new HashMap<String, String>(){{
            put("role", "user");
            put("content", txt);
        }});
        paramMap.put("messages", dataList);
        paramMap.put("temperature", 0.7);
        JSONObject message = null;
        try {
            String body = HttpRequest.post(chatEndpoint)
                .header("Authorization", apiKey)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(paramMap))
                .execute()
                .body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
            message = result.getJSONObject("message");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "失败啦";
        }
        return message.getStr("content");
    }

}
