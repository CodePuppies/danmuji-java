package com.codepuppies.danmuji.client;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 心跳线程，定时发送心跳数据，无连接时尝试重新连接服务器
 *
 * @author Sumy
 * @version 2016-3-3
 */
public class SocketHeartThread extends Thread {

    private static final int SOCKET_HEART_SECOND = 10;
    private boolean isStop = false;
    private Client client;
    private byte[] heartData;

    public SocketHeartThread(Client client, byte[] heartData) {
        this.client = client;
        this.heartData = heartData;
    }

    public void stopThread() {
        isStop = true;
    }

    @Override
    public void run() {
        isStop = false;
        while (!isStop) {
            if (client.isDisconnect || !client.isInitialized) {
                Logger.getLogger(this.getClass().getName()).info("try reconnected");
                client.reConnect();
            }
            try {
                if (!client.isDisconnect && client.isInitialized) {
                    client.sendMsg(heartData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(SOCKET_HEART_SECOND * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
