package com.codepuppies.danmuji.livecomment;

import java.util.Date;

/**
 * 弹幕
 *
 * @author Sumy
 * @version 2016-3-7
 */
public class DanmuMsg extends Comment {

    private final Date date;
    private final String msg;
    private final String username;

    public DanmuMsg(long time, String msg, String username) {
        this.date = new Date(time * 1000);
        this.msg = msg;
        this.username = username;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s]%s:%s", getType(), dateformat.format(date), username, msg);
    }

    @Override
    public String getType() {
        return "弹幕";
    }
}
