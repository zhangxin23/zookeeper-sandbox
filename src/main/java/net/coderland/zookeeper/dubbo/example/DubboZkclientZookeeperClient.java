package net.coderland.zookeeper.dubbo.example;

import net.coderland.zookeeper.dubbo.common.utils.URL;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zhangxin
 * Date: 2017-01-12
 * Time: 10:03:00
 */
public class DubboZkclientZookeeperClient extends AbstractZookeeperClient<IZkChildListener> {

    private ZkClient client = null;

    private volatile Watcher.Event.KeeperState state = Watcher.Event.KeeperState.SyncConnected;

    public DubboZkclientZookeeperClient(URL url) {
        super(url);
        client = new ZkClient(url.getBackupAddress());

        client.subscribeStateChanges(new IZkStateListener() {
            public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
                DubboZkclientZookeeperClient.this.state = state;
                if(state == Watcher.Event.KeeperState.Disconnected) {
                    stateChanged(StateListener.DISCONNECTED);
                } else if(state == Watcher.Event.KeeperState.SyncConnected) {
                    stateChanged(StateListener.CONNECTED);
                }
            }

            public void handleNewSession() throws Exception {
                stateChanged(StateListener.RECONNECTED);
            }
        });
    }

    public void createPersistent(String path) {
        //TODO
        client.createPersistent(path);
    }

    public void createEphemeral(String path) {
        //TODO
    }

    public void delete(String path) {
        //TODO
    }

    public List<String> getChildren(String path) {
        //TODO
        return new ArrayList<String>();
    }

    public boolean isConnected() {
        //TODO
        return false;
    }

    public void doClose() {
        //TODO
    }

    public IZkChildListener createTargetChildListener(String path, final ChildListener listener) {
        //TODO
        return null;
    }

    public List<String> addTargetChildListener(String path, final IZkChildListener listener) {
        //TODO
        return new ArrayList<String>();
    }

    public void removeTargetChildListener(String path, IZkChildListener listener) {
        //TODO
    }
}
