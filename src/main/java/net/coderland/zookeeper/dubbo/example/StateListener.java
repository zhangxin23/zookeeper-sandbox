package net.coderland.zookeeper.dubbo.example;

/**
 * User: zhangxin
 * Date: 2017-01-11
 * Time: 09:41:00
 */
public interface StateListener {
    int DISCONNECTED = 0;

    int CONNECTED = 1;

    int RECONNECTED = 2;

    void stateChanged(int connected);
}
