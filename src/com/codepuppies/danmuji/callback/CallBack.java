package com.codepuppies.danmuji.callback;

import com.codepuppies.danmuji.livecomment.Comment;

/**
 * 回调信息接口
 *
 * @author Sumy
 * @version 2016-3-7
 */
public interface CallBack {
    /**
     * 回调服务器消息
     *
     * @param comment 解析的服务器消息
     */
    void onComment(Comment comment);
}
