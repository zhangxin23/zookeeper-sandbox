package net.coderland.zookeeper.dubbo.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: zhangxin
 * Date: 2017-01-12
 * Time: 10:40:00
 */
public class ZookeeperClientSimpleImpl{

    private final Logger logger = LoggerFactory.getLogger(ZookeeperClientSimpleImpl.class);

    private final ZooKeeper client;

    public ZookeeperClientSimpleImpl(String url, int port) throws IOException {
        client = new ZooKeeper(url, port, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                switch(watchedEvent.getState()) {
                    case Disconnected:
                        System.out.println("zookeeper client disconnected");
                        break;
                    case SyncConnected:
                        System.out.println("zookeeper client SyncConnected");
                        break;
                    default:
                        System.out.println("zookeeper client other state");
                        break;
                }

//                switch(watchedEvent.getType()) {
//                    case NodeCreated:
//                        break;
//                    case NodeDeleted:
//                        break;
//                    case NodeDataChanged:
//                        break;
//                    case NodeChildrenChanged:
//                        break;
//                    default:
//                        break;
//                }
            }
        });
    }

    public void create(String path, String data, boolean ephemeral) {
        try {
            if(ephemeral) {
                client.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } else {
                client.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            logger.error("create node failed", e);
        } catch (InterruptedException e) {
            logger.error("create node failed", e);
        } catch (Exception e) {
            logger.error("create node failed", e);
        }
    }

    public void delete(String path, int version) {
        try {
            client.delete(path, version);
        } catch (KeeperException e) {
            logger.error("delete node failed", e);
        } catch (InterruptedException e) {
            logger.error("delete node failed", e);
        } catch (Exception e) {
            logger.error("delete node failed", e);
        }
    }

    public String getData(String path) {
        try {
            Stat stat = new Stat();
            return new String(client.getData(path, false, stat));
        } catch (KeeperException e) {
            logger.error("get data failed", e);
        } catch (InterruptedException e) {
            logger.error("get data failed", e);
        } catch (Exception e) {
            logger.error("get data failed", e);
        }
        return "";
    }

    public String getData(String path, Stat stat) {
        try {
            return new String(client.getData(path, false, stat));
        } catch (KeeperException e) {
            logger.error("get data failed", e);
        } catch (InterruptedException e) {
            logger.error("get data failed", e);
        } catch (Exception e) {
            logger.error("get data failed", e);
        }
        return "";
    }

    public void setData(String path, String data, int version) {
        try {
            client.setData(path, data.getBytes(), version);
        } catch (KeeperException e) {
            logger.error("set data failed", e);
        } catch (InterruptedException e) {
            logger.error("set data failed", e);
        } catch (Exception e) {
            logger.error("set data failed", e);
        }
    }

    public List<String> getChildren(String path, boolean isWatcher) {
        List<String> children = new ArrayList<>();
        try {
            children = client.getChildren(path, isWatcher);
        } catch (KeeperException e) {
            logger.error("create node failed", e);
        } catch (InterruptedException e) {
            logger.error("create node failed", e);
        } catch (Exception e) {
            logger.error("create node failed", e);
        }
        return children;
    }

    public List<String> addChildListener(String path) {
        List<String> children = new ArrayList<>();
        try {
            children = client.getChildren(path, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                        System.out.println(watchedEvent.getPath() + " children is changed");
                    }
                }
            });
        } catch (KeeperException e) {
            logger.error("create node failed", e);
        } catch (InterruptedException e) {
            logger.error("create node failed", e);
        } catch (Exception e) {
            logger.error("create node failed", e);
        }
        return children;
    }

    public void removeChildListener(String path) {
        getChildren(path, false);
    }

    public void addDataListener(String path) {
        try {
            Stat stat = new Stat();
            client.getData(path, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                        System.out.println(watchedEvent.getPath() + " data changed");
                    }
                }
            }, stat);
        } catch (KeeperException e) {
            logger.error("add data listener failed", e);
        } catch (InterruptedException e) {
            logger.error("add data listener failed", e);
        } catch (Exception e) {
            logger.error("add data listener failed", e);
        }
    }

    public void removeStateListener(String path) {
        try {
            Stat stat = new Stat();
            client.getData(path, false, stat);
        } catch (KeeperException e) {
            logger.error("add data listener failed", e);
        } catch (InterruptedException e) {
            logger.error("add data listener failed", e);
        } catch (Exception e) {
            logger.error("add data listener failed", e);
        }
    }

    public boolean isConnected() {
        return client.getState().isConnected();
    }

    public void close() {
        try {
            client.close();
        } catch (InterruptedException e) {
            logger.error("close zookeeper client failed", e);
        } catch (Exception e) {
            logger.error("close zookeeper client failed", e);
        }
    }

    public static void main(String[] args) throws Exception {
        ZookeeperClientSimpleImpl client = new ZookeeperClientSimpleImpl("127.0.0.1", 2181);
        client.create("/syncTest", "sync zookeeper test", false);
        System.out.println(client.getChildren("/", false));
        Stat stat = new Stat();
        System.out.println(client.getData("/syncTest", stat));
        System.out.println("/syncTest node version is " + stat.getVersion());

        client.addChildListener("/syncTest");

        client.create("/syncTest/child-1", "syncTest child 1", true);

        client.addDataListener("/syncTest");

        client.setData("/syncTest", "change data", stat.getVersion());

//        client.delete("/syncTest", stat.getVersion());
        Thread.sleep(60000);
    }
}
