package limmy.top.springtest.type;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Limmy
 * @date 2020/5/2 10:54 下午
 */
@Getter
@Setter
public class Task {
    public static final String CREATED = "Created";
    public static final String FINISHED = "Finished";


    private long startTime = System.currentTimeMillis();
    private long endTime;
    private String name;
    private String msg;
    private String state;

    public Task(String name, String msg) {
        this.name = name;
        this.msg = msg;
        this.state = CREATED;
    }
}
