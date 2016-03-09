package com.codepuppies.danmuji.callback;

import com.codepuppies.danmuji.livecomment.Comment;

/**
 * 什么也不做
 *
 * @author Sumy
 * @version 2016-3-9
 */
public class DoNothingCallBack implements CallBack {

    @Override
    public void onComment(Comment comment) {
        //什么也不做
    }
}
