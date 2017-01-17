package net.coderland.zookeeper.dubbo.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * User: zhangxin
 * Date: 2017-01-17
 * Time: 08:00:00
 */
public class ZookeeperClientAsyncImpl {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperClientAsyncImpl.class);

    private final ZooKeeper client;

    public ZookeeperClientAsyncImpl(String url, int port) throws IOException {
        client = new ZooKeeper(url, port, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    logger.info("#ZookeeperClientAsyncImpl: zookeeper client connected!");
                }
            }
        });
    }

    public void create(String path, String data, boolean ephemeral) {
        CreateMode mode = CreateMode.PERSISTENT;
        if(ephemeral) {
            mode = CreateMode.EPHEMERAL;
        }

        client.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, mode,
                new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        switch(KeeperException.Code.get(rc)) {
                            case OK:
                                logger.info("#ZookeeperClientAsyncImpl: create {} success.", path);
                                break;
                            case CONNECTIONLOSS:
                                logger.error("#ZookeeperClientAsyncImpl: connection loss");
                                break;
                            default:
                                break;
                        }
                    }
                }, null);
    }

    public void getData(String path) {
        client.getData(path, false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                switch (KeeperException.Code.get(rc)) {
                    case OK:
                        logger.info("#ZookeeperClientAsyncImpl: path={}, data={}", path, new String(data));
                        break;
                    case CONNECTIONLOSS:
                        logger.error("#ZookeeperClientAsyncImpl: connection loss");
                        break;
                    default:
                        break;
                }
            }
        }, null);
    }

    public void getChildren(String path) {
        client.getChildren(path, false, new AsyncCallback.ChildrenCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, List<String> children) {
                switch (KeeperException.Code.get(rc)) {
                    case OK:
                        logger.info("#ZookeeperClientAsyncImpl: children={}", children);
                        break;
                    case CONNECTIONLOSS:
                        logger.error("#ZookeeperClientAsyncImpl: connection loss");
                        break;
                    default:
                        break;
                }
            }
        }, null);
    }

    public static void main(String[] args) throws Exception {
        ZookeeperClientAsyncImpl asyncClient = new ZookeeperClientAsyncImpl("127.0.0.1", 2181);

        String path = "/async-test";
        asyncClient.create(path, "async test", false);
        asyncClient.getData(path);
        asyncClient.getChildren(path);

        String childPath = path + "/child-1";
        asyncClient.create(childPath, "aysn test child-1", true);
        asyncClient.getChildren(path);

        String child_2Path = path + "/child-2";
        asyncClient.create(child_2Path, "aysn test child-2", true);
        asyncClient.getChildren(path);

        Thread.sleep(60000);
    }
}
