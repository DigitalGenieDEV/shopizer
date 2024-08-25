/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.salesmanager.shop.listener.alibaba.tuna.netty.handler.codec.http.websocketx;

import com.salesmanager.shop.listener.alibaba.tuna.netty.channel.Channel;
import com.salesmanager.shop.listener.alibaba.tuna.netty.channel.ChannelFuture;
import com.salesmanager.shop.listener.alibaba.tuna.netty.channel.ChannelPromise;
import com.salesmanager.shop.listener.alibaba.tuna.netty.handler.codec.http.DefaultHttpResponse;
import com.salesmanager.shop.listener.alibaba.tuna.netty.handler.codec.http.HttpRequest;
import com.salesmanager.shop.listener.alibaba.tuna.netty.handler.codec.http.HttpResponse;
import com.salesmanager.shop.listener.alibaba.tuna.netty.handler.codec.http.HttpResponseStatus;
import com.salesmanager.shop.listener.alibaba.tuna.netty.handler.codec.http.HttpVersion;
import com.salesmanager.shop.listener.alibaba.tuna.netty.handler.codec.http.HttpHeaders.Names;

/**
 * Auto-detects the version of the Web Socket protocol in use and creates a new proper
 * {@link WebSocketServerHandshaker}.
 */
public class WebSocketServerHandshakerFactory {

    private final String webSocketURL;

    private final String subprotocols;

    private final boolean allowExtensions;

    private final int maxFramePayloadLength;

    /**
     * Constructor specifying the destination web socket location
     *
     * @param webSocketURL
     *            URL for web socket communications. e.g "ws://myhost.com/mypath".
     *            Subsequent web socket frames will be sent to this URL.
     * @param subprotocols
     *            CSV of supported protocols. Null if sub protocols not supported.
     * @param allowExtensions
     *            Allow extensions to be used in the reserved bits of the web socket frame
     */
    public WebSocketServerHandshakerFactory(
            String webSocketURL, String subprotocols, boolean allowExtensions) {
        this(webSocketURL, subprotocols, allowExtensions, 65536);
    }

    /**
     * Constructor specifying the destination web socket location
     *
     * @param webSocketURL
     *            URL for web socket communications. e.g "ws://myhost.com/mypath".
     *            Subsequent web socket frames will be sent to this URL.
     * @param subprotocols
     *            CSV of supported protocols. Null if sub protocols not supported.
     * @param allowExtensions
     *            Allow extensions to be used in the reserved bits of the web socket frame
     * @param maxFramePayloadLength
     *            Maximum allowable frame payload length. Setting this value to your application's
     *            requirement may reduce denial of service attacks using long data frames.
     */
    public WebSocketServerHandshakerFactory(
            String webSocketURL, String subprotocols, boolean allowExtensions,
            int maxFramePayloadLength) {
        this.webSocketURL = webSocketURL;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadLength = maxFramePayloadLength;
    }

    /**
     * Instances a new handshaker
     *
     * @return A new WebSocketServerHandshaker for the requested web socket version. Null if web
     *         socket version is not supported.
     */
    public WebSocketServerHandshaker newHandshaker(HttpRequest req) {

        String version = req.headers().get(Names.SEC_WEBSOCKET_VERSION);
        if (version != null) {
            if (version.equals(WebSocketVersion.V13.toHttpHeaderValue())) {
                // Version 13 of the wire protocol - RFC 6455 (version 17 of the draft hybi specification).
                return new WebSocketServerHandshaker13(
                        webSocketURL, subprotocols, allowExtensions, maxFramePayloadLength);
            } else if (version.equals(WebSocketVersion.V08.toHttpHeaderValue())) {
                // Version 8 of the wire protocol - version 10 of the draft hybi specification.
                return new WebSocketServerHandshaker08(
                        webSocketURL, subprotocols, allowExtensions, maxFramePayloadLength);
            } else if (version.equals(WebSocketVersion.V07.toHttpHeaderValue())) {
                // Version 8 of the wire protocol - version 07 of the draft hybi specification.
                return new WebSocketServerHandshaker07(
                        webSocketURL, subprotocols, allowExtensions, maxFramePayloadLength);
            } else {
                return null;
            }
        } else {
            // Assume version 00 where version header was not specified
            return new WebSocketServerHandshaker00(webSocketURL, subprotocols, maxFramePayloadLength);
        }
    }

    /**
     * @deprecated use {@link #sendUnsupportedVersionResponse(Channel)}
     */
    @Deprecated
    public static void sendUnsupportedWebSocketVersionResponse(Channel channel) {
        sendUnsupportedVersionResponse(channel);
    }

    /**
     * Return that we need cannot not support the web socket version
     */
    public static ChannelFuture sendUnsupportedVersionResponse(Channel channel) {
        return sendUnsupportedVersionResponse(channel, channel.newPromise());
    }

    /**
     * Return that we need cannot not support the web socket version
     */
    public static ChannelFuture sendUnsupportedVersionResponse(Channel channel, ChannelPromise promise) {
        HttpResponse res = new DefaultHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.UPGRADE_REQUIRED);
        res.headers().set(Names.SEC_WEBSOCKET_VERSION, WebSocketVersion.V13.toHttpHeaderValue());
        return channel.write(res, promise);
    }
}