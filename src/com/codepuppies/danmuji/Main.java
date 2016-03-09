package com.codepuppies.danmuji;

import com.codepuppies.danmuji.api.BilibiliDanmuAPIImp;
import com.codepuppies.danmuji.api.DanmuAPI;
import com.codepuppies.danmuji.callback.CallBack;
import com.codepuppies.danmuji.callback.ConsoleCallBack;
import com.codepuppies.danmuji.client.Client;
import com.codepuppies.danmuji.ui.CommandLineHandler;
import org.apache.commons.cli.*;

/**
 * 程序入口
 */
public class Main {
    public static void main(String[] args) {
        CommandLineHandler clHandler = new CommandLineHandler();
        clHandler.parse(args);

        if (clHandler.canContinue()) {
            DanmuAPI danmuAPI = new BilibiliDanmuAPIImp(clHandler.getLiveid());
            CallBack callBack = new ConsoleCallBack();
            Client client = new Client(danmuAPI, callBack);
            client.connect();
        }
    }
}
