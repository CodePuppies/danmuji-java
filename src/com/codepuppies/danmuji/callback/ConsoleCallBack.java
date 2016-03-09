package com.codepuppies.danmuji.callback;

import com.codepuppies.danmuji.livecomment.Comment;

/**
 * 回调信息输出至控制台
 *
 * @author Sumy
 * @version 2016-3-7
 */
public class ConsoleCallBack implements CallBack {

    @Override
    public void onComment(Comment comment) {
        System.out.println(comment.toString());
    }
}
