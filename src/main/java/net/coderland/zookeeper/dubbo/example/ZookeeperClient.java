package net.coderland.zookeeper.dubbo.example;

import net.coderland.zookeeper.dubbo.common.utils.URL;

import java.util.List;

/**
 * User: zhangxin
 * Date: 2017-01-11
 * Time: 09:42:00
 */
public interface ZookeeperClient {

    void create(String path, boolean ephemeral);

    void delete(String path);

    List<String> getChildren(String path);

    List<String> addChildListener(String path, ChildListener listener);

    void removeChildListener(String path, ChildListener listener);

    void addStateListener(StateListener listener);

    void removeStateListener(StateListener listener);

    boolean isConnected();

    void close();

    URL getUrl();
}
