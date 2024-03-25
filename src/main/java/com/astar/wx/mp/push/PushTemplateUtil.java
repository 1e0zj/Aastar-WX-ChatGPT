package com.astar.wx.mp.push;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * @author wuzhenyong
 * ClassName:PushTemplateUtil.java
 * date:2023-03-27 15:21
 * Description: 模板推送工具类
 */
@UtilityClass
public class PushTemplateUtil {

    @SneakyThrows
    public void sendMessage(String openId, String question, String chat, WxMpService weixinService) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
            .toUser(openId)
            .templateId("")
            .url("")
            .build();

        templateMessage
            .addData(new WxMpTemplateData("first", ""))
            .addData(new WxMpTemplateData("keyword1", question))
            .addData(new WxMpTemplateData("keyword2", chat))
            .addData(new WxMpTemplateData("remark", ""));

        weixinService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    }
}
