package com.codepuppies.danmuji.livecomment;

/**
 * 直播间状态，直播中
 *
 * @author Sumy
 * @version 2016-3-7
 */
public class Live extends Comment {

    @Override
    public String getType() {
        return "直播中";
    }

    @Override
    public String toString() {
        return String.format("[系统]%s", getType());
    }
}
