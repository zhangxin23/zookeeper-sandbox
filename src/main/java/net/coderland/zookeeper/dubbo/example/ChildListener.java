package net.coderland.zookeeper.dubbo.example;

import java.util.List;

/**
 * User: zhangxin
 * Date: 2017-01-11
 * Time: 09:39:00
 */
public interface ChildListener {

    void childChanged(String path, List<String> children);

}
