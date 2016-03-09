package com.codepuppies.danmuji.livecomment;

import java.text.SimpleDateFormat;

/**
 * 表示服务器返回的一条数据
 *
 * @author Sumy
 * @version 2016-3-7
 */
public abstract class Comment {
    /**
     * 时间格式
     */
    public static final SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");

    /**
     * 返回数据表示的类型意义
     *
     * @return 数据的类型名称
     */
    public abstract String getType();
}
