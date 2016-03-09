package com.codepuppies.danmuji.client;

import com.codepuppies.danmuji.api.DanmuAPI;
import com.codepuppies.danmuji.callback.CallBack;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 用户连接服务器的客户端类，客户端类会调用API中的接口数据与服务器通信，将返回的数据通过解析返回给CallBack中
 *
 * @author Sumy
 * @version 2016-3-3
 */
public class Client {
    private static final int SOCKET_READ_TIMOUT = 200;
    private Selector selector;
    private SocketChannel socketChannel;

    private String hostAddress;
    private int hostPort;

    private SocketHeartThread heartThread = null;
    private SocketInputThread inputThread = null;

    protected boolean isInitialized = false;
    protected boolean isDisconnect = true;

    private DanmuAPI api;
    private CallBack callBack;

    public Client(DanmuAPI danmuAPI, CallBack callBack) {
        this.hostAddress = danmuAPI.getHost();
        this.hostPort = danmuAPI.getPort();
        this.api = danmuAPI;
        this.callBack = callBack;

        heartThread = new SocketHeartThread(this, danmuAPI.getheartMessage());
        inputThread = new SocketInputThread(this, danmuAPI, callBack);
    }

    public void connect() {
        try {
            initialize();
            this.isInitialized = true;
        } catch (IOException e) {
            this.isInitialized = false;
            e.printStackTrace();
        } catch (Exception e) {
            this.isInitialized = false;
            e.printStackTrace();
        }
        if (this.isInitialized) {
            inputThread.start();
            isDisconnect = false;
        }
        heartThread.start();
    }

    public Selector getSelector() {
        return selector;
    }

    public void initialize() throws IOException {
        if (this.isInitialized) return;
        boolean done = false;
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(hostAddress, hostPort));
            if (socketChannel != null) {
                socketChannel.socket().setTcpNoDelay(false);
                socketChannel.socket().setKeepAlive(true);
                socketChannel.socket().setSoTimeout(SOCKET_READ_TIMOUT);
                socketChannel.configureBlocking(false);

                selector = Selector.open();
                if (selector != null) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    done = true;
                }
            }
            sendMsg(api.gethandMessage()); //发送握手认证数据
        } finally {
            if (!done && selector != null) {
                selector.close();
            }
            if (!done && socketChannel != null) {
                socketChannel.close();
            }
        }

    }

    public void sendMsg(String message) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes("utf-8"));

        if (socketChannel == null) {
            throw new IOException();
        }
        socketChannel.write(writeBuffer);
    }

    public void sendMsg(byte[] bytes) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.wrap(bytes);

        if (socketChannel == null) {
            throw new IOException();
        }
        socketChannel.write(writeBuffer);
    }

    public boolean isConnect() {
        boolean isConnect = false;
        if (this.isInitialized && !this.isDisconnect) {
            isConnect = this.socketChannel.isConnected();
        }
        return isConnect;
    }

    public boolean reConnect() {
        isInitialized = false;
        closeTCPSocket();
        try {
            initialize();
            isInitialized = true;
            isDisconnect = false;
            inputThread.stopThread();
            inputThread = new SocketInputThread(this, api, callBack); //无法重新启动同一个线程，还是new一个新的
            inputThread.start(); // 重连*应该*成功了，启动读取线程
        } catch (IOException e) {
            isInitialized = false;
            e.printStackTrace();
        } catch (Exception e) {
            isInitialized = false;
            e.printStackTrace();
        }
        return isInitialized;
    }

    /**
     * 检测是否连接到服务器
     *
     * @deprecated 方法已弃用，会抛出<code>java.nio.channels.IllegalBlockingModeException</code>异常
     */
    @Deprecated
    public boolean canConnectToServer() {
        try {
            if (socketChannel != null) {
                socketChannel.socket().sendUrgentData(0xff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void closeTCPSocket() {
        try {
            if (socketChannel != null) {
                socketChannel.close();
            }
        } catch (IOException e) {

        }
        try {
            if (selector != null) {
                selector.close();
            }
        } catch (IOException e) {
        }
    }

    public synchronized void repareRead() {
        if (socketChannel != null) {
            try {
                selector = Selector.open();
                socketChannel.register(selector, SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
