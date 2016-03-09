package com.codepuppies.danmuji.api;

import com.codepuppies.danmuji.livecomment.Comment;

/**
 * 该API用于描述与服务器通信的信息
 *
 * @author Sumy
 * @version 2016-2-3
 */
public interface DanmuAPI {
    /**
     * 获取服务器地址
     *
     * @return 返回一个表示服务器地址的字符串，使用IP或域名表示
     */
    String getHost();

    /**
     * 获取服务器接端口
     *
     * @return 返回端口
     */
    int getPort();

    /**
     * 获取用于握手认证的字节信息
     *
     * @return 返回包含认证信息的字节数组
     */
    byte[] gethandMessage();

    /**
     * 获取用于心跳包的字节信息
     *
     * @return 返回包含心跳信息的字节数据
     */
    byte[] getheartMessage();

    /**
     * 解析服务器返回的字节信息
     *
     * @param data 服务器返回的字节信息
     * @return 解析到的对象
     */
    Comment parseMessage(byte[] data);
}
