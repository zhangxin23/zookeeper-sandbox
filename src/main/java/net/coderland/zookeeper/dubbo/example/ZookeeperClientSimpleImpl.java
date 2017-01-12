package net.coderland.zookeeper.dubbo.example;

import net.coderland.zookeeper.dubbo.common.utils.URL;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: zhangxin
 * Date: 2017-01-12
 * Time: 10:40:00
 */
public class ZookeeperClientSimpleImpl implements ZookeeperClient {

    private final ZooKeeper client;

    public ZookeeperClientSimpleImpl(String url, int port) throws IOException {
        client = new ZooKeeper(url, port, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //ToDo
            }
        });
        
    }

    @Override
    public void create(String path, boolean ephemeral) {
        //ToDo
    }

    @Override
    public void delete(String path) {

    }

    @Override
    public List<String> getChildren(String path) {
        //ToDo
        return new ArrayList<String>();
    }

    @Override
    public List<String> addChildListener(String path, ChildListener listener) {
        //ToDo
        return new ArrayList<String>();
    }

    @Override
    public void removeChildListener(String path, ChildListener listener) {
        //ToDo
    }

    @Override
    public void addStateListener(StateListener listener) {
        //ToDo
    }

    @Override
    public void removeStateListener(StateListener listener) {
        //ToDo
    }

    @Override
    public boolean isConnected() {
        //ToDo
        return false;
    }

    @Override
    public void close() {
        //ToDo
    }

    @Override
    public URL getUrl() {
        //ToDo
        return null;
    }
}
