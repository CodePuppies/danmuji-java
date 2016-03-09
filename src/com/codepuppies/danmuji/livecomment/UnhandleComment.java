package com.codepuppies.danmuji.livecomment;

/**
 * 未知消息
 *
 * @author Sumy
 * @version 2016-3-7
 */
public class UnhandleComment extends Comment {

    private final String jsondata;

    public UnhandleComment(String jsondata) {
        this.jsondata = jsondata;
    }

    @Override
    public String toString() {
        return String.format("[系统]%s %s", getType(), jsondata);
    }

    @Override
    public String getType() {
        return "未处理";
    }
}
