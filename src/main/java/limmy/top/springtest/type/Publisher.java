package limmy.top.springtest.type;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Limmy
 * @date 2020/5/2 10:54 下午
 */
@Getter
@Setter
public class Publisher {
    private Map<String, Task> tasks = new ConcurrentHashMap<>();
    private static ChannelGroup observers = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private String name;
    private String pwd;
    private long lifeTime = System.currentTimeMillis();

    public Publisher(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public boolean addTask(@NonNull Task task) {
        this.tasks.put(task.getName(), task);
        notifyForUpdate();
        return true;
    }

    public void addObserver(Channel observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Channel observer) {
        this.observers.remove(observer);
    }

    public boolean removeTask(String name) {
        if (!this.tasks.containsKey(name)) {
            return false;
        }
        this.tasks.remove(name);
        notifyForUpdate();
        return true;
    }

    public boolean updateTaskState(String name, @NonNull String state) {
        if (state.equals(Task.CREATED) || state.equals(Task.FINISHED)) {
            return false;
        }
        Task task = tasks.get(name);
        if (task == null || task.getState().equals(Task.FINISHED)) {
            return false;
        }
        task.setState(state);
        notifyForUpdate();
        return true;
    }

    public boolean finishTask(String name) {
        Task task = tasks.get(name);
        if (task == null) {
            return false;
        }
        task.setState(Task.FINISHED);
        task.setEndTime(System.currentTimeMillis());
        notifyForFinish(task);
        return true;
    }

    public void notifyForUpdate() {
        // 通知观察者有任务变化
        for (Channel channel : observers) {
            if (channel.isActive()) {
                channel.writeAndFlush(new TextWebSocketFrame("2$limmy$Some tasks updated"));
            }
        }
    }

    public void notifyForFinish(Task task) {
        // 通知观察者某任务完成
        for (Channel channel : observers) {
            if (channel.isActive()) {
                channel.writeAndFlush(new TextWebSocketFrame("3$limmy$" + JSONObject.toJSONString(task)));
            }
        }
    }

    public void notifyForShutdown() {
        // 通知观察者该发布者已下线
        for (Channel channel : observers) {
            if (channel.isActive()) {
                channel.writeAndFlush(new TextWebSocketFrame("4$limmy$" + this.name));
            }
        }
    }

    public List<Task> listTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void cleanTasks() {
        this.tasks.clear();
    }
}
