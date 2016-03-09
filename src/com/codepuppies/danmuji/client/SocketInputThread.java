package com.codepuppies.danmuji.client;

import com.codepuppies.danmuji.api.DanmuAPI;
import com.codepuppies.danmuji.callback.CallBack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 读取线程，从服务器读取返回的数据，API解析数据后交还CallBack
 *
 * @author Sumy
 * @version 2016-3-3
 */
public class SocketInputThread extends Thread {
    private static final int SOCKET_SLEEP_SECOND = 2;
    private boolean isStop = false;
    private Client client;
    private DanmuAPI api;
    private CallBack callBack;

    public SocketInputThread(Client client, DanmuAPI api, CallBack callBack) {
        this.client = client;
        this.api = api;
        this.callBack = callBack;
    }

    public void stopThread() {
        this.isStop = true;
    }

    @Override
    public void run() {
        isStop = false;
        while (!isStop) {
            while (!client.isConnect()) {
                try {
                    Thread.sleep(SOCKET_SLEEP_SECOND * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            readSocket();
        }
    }

    private void readSocket() {
        Selector selector = client.getSelector();
        if (selector == null) return;
        try {
            while (selector.select() > 0) {
                for (SelectionKey sk : selector.selectedKeys()) {
                    selector.selectedKeys().remove(sk);
                    if (sk.isReadable()) {
                        SocketChannel sc = (SocketChannel) sk.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(2048);
                        try {
                            int read = sc.read(buffer);
//                            System.out.println(read);
                        } catch (IOException e) {
                            e.printStackTrace();
                            client.isDisconnect = true; //读不到数据，可能已经断开连接
                            this.stopThread(); // 停下线程，等待心跳线程重连
                            return;
                        }
                        buffer.flip();

                        try {
                            callBack.onComment(api.parseMessage(buffer.array()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        buffer.clear();

                        try {
                            sk.interestOps(SelectionKey.OP_READ);
                        } catch (CancelledKeyException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
