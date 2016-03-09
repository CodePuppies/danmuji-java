package com.codepuppies.danmuji.livecomment;

/**
 * 系统广播消息
 *
 * @author Sumy
 * @version 2016-3-8
 */
public class SysMsg extends Comment {
    private final String msg;
    private final String url;

    public SysMsg(String message, String url) {
        this.msg = message;
        this.url = url;
    }

    @Override
    public String getType() {
        return "系统通告";
    }

    @Override
    public String toString() {
        return String.format("[系统]%s", msg);
    }
}
