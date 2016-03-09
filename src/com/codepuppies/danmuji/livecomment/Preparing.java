package com.codepuppies.danmuji.livecomment;

/**
 * 直播间状态，准备中
 *
 * @author Sumy
 * @version 2016-3-7
 */
public class Preparing extends Comment {
    @Override
    public String getType() {
        return "准备中";
    }

    @Override
    public String toString() {
        return String.format("[系统]%s", getType());
    }
}
