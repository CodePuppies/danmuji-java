package com.codepuppies.danmuji.livecomment;

import java.util.Date;

/**
 * 礼物描述
 *
 * @author Sumy
 * @version 2016-3-7
 */
public class SendGift extends Comment {
    private final Date date;
    private final String username;
    private final String action;
    private final String giftname;
    private final int giftnum;

    public SendGift(long giftdate, String giftusername, String giftaction, String giftname, int giftnum) {
        this.date = new Date(giftdate * 1000);
        this.username = giftusername;
        this.action = giftaction;
        this.giftname = giftname;
        this.giftnum = giftnum;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s]%s %s %s*%d", getType(), dateformat.format(date), username, action, giftname, giftnum);
    }

    @Override
    public String getType() {
        return "礼物";
    }
}
