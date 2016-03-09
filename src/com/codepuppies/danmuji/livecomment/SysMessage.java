package com.codepuppies.danmuji.livecomment;

/**
 * 系统状态描述
 *
 * @author Sumy
 * @version 2016-3-8
 */
public class SysMessage extends Comment {
    private final String message;

    public SysMessage(String message) {
        this.message = message;
    }

    @Override
    public String getType() {
        return "系统信息";
    }

    @Override
    public String toString() {
        return String.format("[系统]%s %s", getType(), message);
    }
}
