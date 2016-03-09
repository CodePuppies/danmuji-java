package com.codepuppies.danmuji.util;

/**
 * 工具类，生成用于Socket通信的字节数据
 *
 * @author Sumy
 * @version 2016-3-3
 */
public class SocketData {

    /**
     * 生成用于Bilibili服务器通信的数据
     *
     * @param totalLen 总长度
     * @param headLen  头部长度
     * @param version  协议版本
     * @param param4   保留字段
     * @param param5   保留字段
     * @param data     数据
     * @return 包含通信数据的字节数组
     */
    public static byte[] getSocketByteData(int totalLen, short headLen, short version, int param4, int param5, byte[] data) {
        byte[] ret = new byte[totalLen];
        ByteNum.intToByteArray(ret, 0, totalLen);
        ByteNum.shortToByteArray(ret, 4, headLen);
        ByteNum.shortToByteArray(ret, 6, version);
        ByteNum.intToByteArray(ret, 8, param4);
        ByteNum.intToByteArray(ret, 12, param5);
        if (data != null) System.arraycopy(data, 0, ret, 16, data.length);
        return ret;
    }
}
