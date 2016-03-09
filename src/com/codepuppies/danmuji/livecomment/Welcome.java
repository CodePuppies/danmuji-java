package com.codepuppies.danmuji.livecomment;

/**
 * 特殊用户欢迎信息
 *
 * @author Sumy
 * @version 2016-3-7
 */
public class Welcome extends Comment {
    private final String username;
    private final boolean isadmin;
    private final boolean svip;
    private final boolean vip;

    public Welcome(String username, Integer isadmin, Integer svip, Integer vip) {
        this.username = username;
        this.isadmin = !(isadmin == null || isadmin == 0);
        this.svip = !(svip == null || svip == 0);
        this.vip = !(vip == null || vip == 0);
    }

    private String getUserType() {
        String ret = "";
        if (isadmin) ret += "{房管}";
        if (svip) ret += "{年费老爷}";
        if (vip) ret += "{姥爷}";
        return ret;
    }

    @Override
    public String toString() {
        return String.format("[系统]%s %s %s 进入直播间", getType(), getUserType(), username);
    }

    @Override
    public String getType() {
        return "欢迎";
    }
}
