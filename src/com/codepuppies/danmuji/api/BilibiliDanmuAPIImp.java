package com.codepuppies.danmuji.api;

import com.codepuppies.danmuji.livecomment.*;
import com.codepuppies.danmuji.util.ByteNum;
import com.codepuppies.danmuji.util.SocketData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * 哔哩哔哩弹幕网弹幕服务器的实现
 * 参考https://coding.net/u/payne/p/bili-comment/git/blob/master/lib/v2/comment_socket.js
 *
 * @author Sumy
 * @version 2016-3-3
 */
public class BilibiliDanmuAPIImp implements DanmuAPI {
    private static final String DEFAULT_COMMENT_HOST = "livecmt-1.bilibili.com";
    private static final int DEFAULT_COMMENT_PORT = 788;
    private int liveId;

    public BilibiliDanmuAPIImp(int liveId) {
        this.liveId = liveId;
    }

    @Override
    public String getHost() {
        return DEFAULT_COMMENT_HOST;
    }

    @Override
    public int getPort() {
        return DEFAULT_COMMENT_PORT;
    }

    @Override
    public byte[] gethandMessage() {
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("roomid", liveId);
        long uid = 100000000000000L + (long) (new Random().nextDouble() * 200000000000000L);
        jsonobj.put("uid", uid);
        Logger.getLogger(this.getClass().getName()).info(jsonobj.toString());
        byte[] bytedata = jsonobj.toString().getBytes();
        return SocketData.getSocketByteData(16 + bytedata.length, (short) 16, (short) 1, 7, 1, bytedata);
    }

    @Override
    public byte[] getheartMessage() {
        return SocketData.getSocketByteData(16, (short) 16, (short) 1, 2, 1, null);

    }

    @Override
    public Comment parseMessage(byte[] data) {
        int datalen = ByteNum.intFromByteArray(data, 0);
//        System.out.println(datalen);
        short headlen = ByteNum.shortFromByteArray(data, 4);

        int actionIndex = ByteNum.intFromByteArray(data, 8);

        switch (actionIndex) {
            case 3:
                Logger.getLogger(this.getClass().getName()).info("login_success--num: " + ByteNum.intFromByteArray(data, headlen));
                return new WatchNum(ByteNum.intFromByteArray(data, headlen));
            case 5:
                try {
                    String jsondata = new String(data, headlen, datalen - headlen, "utf-8");
                    Logger.getLogger(this.getClass().getName()).info(jsondata);
                    JSONObject jsonObject = new JSONObject(jsondata);
                    switch (jsonObject.getString("cmd")) {
                        case "DANMU_MSG":
                            JSONArray msginfo = jsonObject.getJSONArray("info");
                            long msgdate = msginfo.getJSONArray(0).getLong(4);
                            String msgmsg = msginfo.getString(1);
                            String msgusername = msginfo.getJSONArray(2).getString(1);
                            return new DanmuMsg(msgdate, msgmsg, msgusername);
                        case "SEND_GIFT":
                            JSONObject giftinfo = jsonObject.getJSONObject("data");
                            long giftdate = giftinfo.getLong("timestamp");
                            String giftusername = giftinfo.getString("uname");
                            String giftaction = giftinfo.getString("action");
                            String giftname = giftinfo.getString("giftName");
                            int giftnum = giftinfo.getInt("num");
                            return new SendGift(giftdate, giftusername, giftaction, giftname, giftnum);
                        case "WELCOME":
                            JSONObject welinfo = jsonObject.getJSONObject("data");
                            String welusername = welinfo.getString("uname");
                            Integer isadmin = welinfo.has("isadmin") ? welinfo.getInt("isadmin") : null;
                            Integer svip = welinfo.has("svip") ? welinfo.getInt("svip") : null;
                            Integer vip = welinfo.has("vip") ? welinfo.getInt("vip") : null;
                            return new Welcome(welusername, isadmin, svip, vip);
                        case "SYS_GIFT":
                            return new SysGift(jsonObject.getString("msg"));
                        case "SYS_MSG":
                            return new SysMsg(jsonObject.getString("msg"), jsonObject.getString("url"));
                        case "LIVE":
                            return new Live();
                        case "PREPARING":
                            return new Preparing();
                        default:
                            return new UnhandleComment(jsondata);
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                Logger.getLogger(this.getClass().getName()).info("connected");
                return new SysMessage("已连接服务器");
            case 17:
                Logger.getLogger(this.getClass().getName()).info("server updated");
                break;
        }
        Logger.getLogger(this.getClass().getName()).info("unhandle" + actionIndex);
        return null;
    }
}
