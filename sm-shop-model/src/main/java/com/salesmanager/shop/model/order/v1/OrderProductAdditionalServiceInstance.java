package com.salesmanager.shop.model.order.v1;

import com.alibaba.fastjson.JSONObject;
import com.salesmanager.core.enmus.AdditionalServiceInstanceStatusEnums;
import com.salesmanager.shop.model.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderProductAdditionalServiceInstance extends Entity {
    private Long orderProductId;
    private Long additionalServiceId;
    private String additionalFilename;
    private String additionalFileUrl;
    private String status = AdditionalServiceInstanceStatusEnums.INITIAL.name();
    private Date dateCreated;

    /**
     * chat object
     */
    private MessageContent messageContent;

    @Data
    public static class MessageContent {
        private List<MessageDetail> details = new ArrayList<>();

        public int size() {
            return details.size();
        }

        public void addMessage(String content, String from) {
            MessageDetail messageDetail = new MessageDetail();
            messageDetail.setFrom(from);
            messageDetail.setContext(content);
            messageDetail.setTimestamp(new Date());
            messageDetail.setSequence(nextSequence());
            details.add(messageDetail);
        }

        public void replyMessage(String content, String from, Integer replyTo) {
            MessageDetail messageDetail = new MessageDetail();
            messageDetail.setFrom(from);
            messageDetail.setContext(content);
            messageDetail.setTimestamp(new Date());
            messageDetail.setSequence(nextSequence());
            messageDetail.setReplyTo(replyTo);
            details.add(messageDetail);
        }

        private int nextSequence() {
            if (details.isEmpty()) {
                return 1;
            }
            return details.get(details.size() - 1).getSequence() + 1;
        }

        public MessageDetail getSequenceOf(Integer sequence) {
            return details
                    .stream()
                    .filter(detail -> Objects.equals(detail.getSequence(), sequence))
                    .findAny()
                    .orElse(null);
        }

        public void delReply(Integer sequence) {
            details.removeIf(detail -> detail.getReplyTo() != null && Objects.equals(detail.getSequence(), sequence));
        }

        public void updateReply(String content, Integer sequence) {
            details.stream()
                    .filter(detail -> detail.getReplyTo() != null && Objects.equals(detail.getSequence(), sequence))
                    .findFirst()
                    .ifPresent(detail -> detail.setContext(content));
        }
    }

    @Data
    public static class MessageDetail {
        private String context;
        /**
         * indicate who send it
         */
        private String from;
        private Date timestamp;
        private Integer replyTo;
        private Integer sequence;
    }

    public void addMessage(String content, String from) {
        if (messageContent == null) {
            messageContent = new MessageContent();
        }
        messageContent.addMessage(content, from);
    }

    public void replyMessage(String content, String from, Integer replyTo) {
        if (messageContent == null) {
            messageContent = new MessageContent();
        }
        messageContent.replyMessage(content, from, replyTo);
    }

    public String buildContentString() {
        if (messageContent == null) {
            return null;
        }
        return JSONObject.toJSONString(messageContent);
    }
}
