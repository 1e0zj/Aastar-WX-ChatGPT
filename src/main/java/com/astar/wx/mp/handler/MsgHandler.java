package com.astar.wx.mp.handler;

import com.astar.wx.mp.chat.LingyiwanwuChat;
import com.astar.wx.mp.config.AstarMpProperties;
import com.astar.wx.mp.push.PushTemplateUtil;
import com.astar.wx.mp.utils.OpenAIAPI;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


/**
 * Astar（一颗星）
 */
@Component
@EnableConfigurationProperties(AstarMpProperties.class)
public class MsgHandler extends AbstractHandler {
    public  static  final Map<String, Integer> dataMap = new HashMap<>();
    @Autowired
    private AstarMpProperties astarMpProperties;

    @Autowired
    private LingyiwanwuChat lingyiwanwuChat;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {


        if (astarMpProperties.isTemplateEnable()) {
            PushTemplateUtil.sendMessage(wxMessage.getFromUser(), wxMessage.getContent(), OpenAIAPI.chat(wxMessage.getContent()), weixinService);
            return null;
        }
        String answer = lingyiwanwuChat.chat(wxMessage.getContent());
        logger.info("question:{}, answer:{}", wxMessage.getContent(), answer);
        return WxMpXmlOutMessage.TEXT()
            .content(answer)
            .fromUser(wxMessage.getToUser())
            .toUser(wxMessage.getFromUser())
            .build();

    }
}
