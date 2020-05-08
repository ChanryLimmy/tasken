package limmy.top.springtest.type;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Limmy
 * @date 2020/5/3 9:28 下午
 */
@Getter
@Setter
@Accessors(chain = true)
public class WSSetting {
    private String address;
    private int port;

    public WSSetting(String address, int port) {
        this.address = address;
        this.port = port;
    }
}
