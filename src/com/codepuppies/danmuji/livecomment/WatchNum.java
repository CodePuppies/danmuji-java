package com.codepuppies.danmuji.livecomment;

/**
 * 直播间人数
 *
 * @author Sumy
 * @version 2016-3-7
 */
public class WatchNum extends Comment {

    public final int num;

    public WatchNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return String.format("[系统]%s:%d", getType(), num);
    }

    @Override
    public String getType() {
        return "房间人数";
    }
}
