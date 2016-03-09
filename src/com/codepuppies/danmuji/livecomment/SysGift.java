package com.codepuppies.danmuji.livecomment;

/**
 * 系统广播的礼物通告
 *
 * @author Sumy
 * @version 2016-3-7
 */
public class SysGift extends Comment {
    private final String giftmessage;

    public SysGift(String message) {
        this.giftmessage = message;
    }

    @Override
    public String toString() {
        return String.format("[系统]%s %s", getType(), giftmessage);
    }

    @Override
    public String getType() {
        return "系统礼物";
    }
}
