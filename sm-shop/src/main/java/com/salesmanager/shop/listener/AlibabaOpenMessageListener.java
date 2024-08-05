package com.salesmanager.shop.listener;

import com.alibaba.tuna.client.api.MessageProcessException;
import com.alibaba.tuna.client.httpcb.HttpCbMessage;
import com.alibaba.tuna.client.httpcb.HttpCbMessageHandler;
import com.alibaba.tuna.client.httpcb.TunaHttpCbClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.salesmanager.core.constants.ApiFor1688Constants.SECRET_KEY;

@Component
public class AlibabaOpenMessageListener {
    TunaHttpCbClient client = new TunaHttpCbClient(8018, false);

    public boolean doSth(HttpCbMessage message) {
        return false;
    }

    @PostConstruct
    public void init() {
        // 2. 创建 消息处理 Handler
        HttpCbMessageHandler messageHandler = new HttpCbMessageHandler<HttpCbMessage, Void>() {

            /**
             * 应用密钥
             */
            public String getSignKey() {
                return SECRET_KEY;
            }

            /**
             * 为了防止消息篡改，开放平台推送的数据包含签名信息。
             * 字段名为 _aop_signature，假设值为 serverSign。
             *
             * 接收到消息后，SDK 首先会使用秘钥对接收到的内容进行签名，
             * 假设值为 clientSign。
             *
             * 1. 若 serverSign 与 clientSign 相同，则直接调用 {@link #onMessage(Object)} 方法。
             * 2. 若 serverSign 与 clientSign 不同，则调用该方法。若该方法返回 true，则继续
             * 	调用 {@link #onMessage(Object)} 方法；否则直接返回状态码 401。
             */
            public boolean continueOnSignatureValidationFailed(String clientSign, String serverSign) {
                return false;
            }

            /**
             * 消费消息。
             * @throws MessageProcessException 消息消费不成功，如未达重试次数上限，开放平台将会择机重发消息
             */
            public Void onMessage(HttpCbMessage message) throws MessageProcessException {
                System.out.println("message: " + message);
                // 真正的业务逻辑在这。如果消费失败或异常，请抛出 MessageProcessException 异常
                try {
                    boolean ret = doSth(message);
                    if(ret) {
                        throw new MessageProcessException("process alibaba open message error");
                    }
                } catch(Exception e) {
                    throw new MessageProcessException("process alibaba open message error");
                }
                return null;
            }
        };
        // 注册 Handler
        client.registerMessageHandler("/pushMessage", messageHandler);

        // 3. 启动 Client
        client.start();
    }
}
