package net.coderland.zookeeper;

import org.apache.zookeeper.*;

/**
 * User: zhangxin
 * Date: 2017-01-11
 * Time: 09:20:00
 */
public class SimpleExample {
    public static void simpleExample() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 10000, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件!");
            }
        });

        // 创建一个目录节点
        zooKeeper.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 创建一个子目录节点
        zooKeeper.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zooKeeper.getData("/testRootPath", false, null)));

        // 取出子目录节点列表
        System.out.println(zooKeeper.getChildren("/testRootPath", true));

        // 修改子目录节点数据
        zooKeeper.setData("/testRootPath/testChildPathOne", "modifyChildDataOne".getBytes(), -1);
        System.out.println("目录节点状态: [" + zooKeeper.exists("/testRootPath", true) + "]");

        // 创建另外一个子目录节点
        zooKeeper.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zooKeeper.getData("/testRootPath/testChildPathTwo", true, null)));

        // 删除子目录节点
        zooKeeper.delete("/testRootPath/testChildPathTwo", -1);
        zooKeeper.delete("/testRootPath/testChildPathOne", -1);

        // 删除父目录节点
        zooKeeper.delete("/testRootPath", -1);

        // 关闭连接
        zooKeeper.close();
    }

    public static void main(String[] args) {
        try {
            simpleExample();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
