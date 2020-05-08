package limmy.top.springtest.service;

import limmy.top.springtest.type.Publisher;
import limmy.top.springtest.type.Task;
import limmy.top.springtest.vo.PublisherVO;
import limmy.top.springtest.vo.TaskVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Limmy
 * @date 2020/5/3 11:12 上午
 */
@Service("PublisherService")
public class PublisherService {
    public static final String KEYWORD = "$limmy$";
    private static Map<String, Publisher> publisherMap = new ConcurrentHashMap<>();

    public Publisher addPublisher(String name, String pwd, boolean clean) {
        Publisher remain = publisherMap.get(name);
        if ((remain != null && !remain.getPwd().equals(pwd)) || name.equals(KEYWORD)) {
            return null;
        } else if (remain == null) {
            Publisher publisher = new Publisher(name, pwd);
            publisherMap.put(name, publisher);
            return publisher;
        }
        if (clean) {
            remain.cleanTasks();
        }
        return remain;
    }

    public boolean removePublisher(String name, String pwd) {
        if (checkPublisher(name, pwd)) {
            Publisher publisher = publisherMap.get(name);
            publisher.notifyForShutdown();
            publisherMap.remove(name);
            return true;
        }
        return false;
    }

    public boolean addTask(TaskVO taskVO) {
        Publisher publisher = publisherMap.get(taskVO.getPname());
        if (!checkPublisher(taskVO.getPname(), taskVO.getPpwd()))
            return false;
        updateLifeTime(publisher);
        return publisher.addTask(new Task(taskVO.getName(), taskVO.getMsg()));
    }

    public boolean updateTask(TaskVO taskVO) {
        Publisher publisher = publisherMap.get(taskVO.getPname());
        if (!checkPublisher(taskVO.getPname(), taskVO.getPpwd())) {
            return false;
        }
        updateLifeTime(publisher);
        return publisher.updateTaskState(taskVO.getName(), taskVO.getState());
    }

    public boolean finishTask(TaskVO taskVO) {
        Publisher publisher = publisherMap.get(taskVO.getPname());
        if (!checkPublisher(taskVO.getPname(), taskVO.getPpwd())) {
            return false;
        }
        updateLifeTime(publisher);
        return publisher.finishTask(taskVO.getName());
    }

    public void cleanPublisher(String name) {
        Publisher publisher = publisherMap.get(name);
        if (publisher != null) {
            publisher.notifyForShutdown();
            publisherMap.remove(name);
        }
    }

    private boolean checkPublisher(String name, String pwd) {
        Publisher publisher = publisherMap.get(name);
        return publisher != null && publisher.getPwd().equals(pwd);
    }

    public boolean removeTask(TaskVO taskVO) {
        Publisher publisher = publisherMap.get(taskVO.getPname());
        if (!checkPublisher(taskVO.getPname(), taskVO.getPpwd())) {
            return false;
        }
        updateLifeTime(publisher);
        return publisher.removeTask(taskVO.getName());
    }

    public List<String> listPublisher() {
        return new ArrayList<>(publisherMap.keySet());
    }

    public List<Task> listTasks(String name) {
        Publisher publisher = publisherMap.get(name);
        if (publisher == null) {
            return Collections.emptyList();
        }
        return publisher.listTasks();
    }

    public boolean updateLifeTime(PublisherVO publisherVO) {
        Publisher publisher = publisherMap.get(publisherVO.getName());
        if (!checkPublisher(publisherVO.getName(), publisherVO.getPwd())) {
            return false;
        }
        publisher.setLifeTime(System.currentTimeMillis());
        return true;
    }

    private void updateLifeTime(Publisher publisher) {
        publisher.setLifeTime(System.currentTimeMillis());
    }

    public Publisher getPublisher(String name) {
        return publisherMap.get(name);
    }
}
