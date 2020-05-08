package limmy.top.springtest.type;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Limmy
 * @date 2020/5/2 5:58 下午
 */
@Getter
@Setter
public class Observer {
    private String address;
    private int port;

    public Observer(String address, int port) {
        this.address = address;
        this.port = port;
    }
}
