package net.coderland.zookeeper.dubbo.example;

import java.util.List;

/**
 * User: zhangxin
 * Date: 2017-01-13
 * Time: 09:30:00
 */
public interface ZookeeperClientSimple {

    void create(String path, String data, boolean ephemeral);

    void delete(String path, int version);

    List<String> getChildren(String path, boolean isWatcher);

    List<String> addChildListener(String path, ChildListener listener);

    void removeChildListener(String path, ChildListener listener);

    void addStateListener(StateListener listener);

    void removeStateListener(StateListener listener);

    boolean isConnected();

    void close();
}
