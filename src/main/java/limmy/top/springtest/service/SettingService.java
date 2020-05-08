package limmy.top.springtest.service;

import limmy.top.springtest.type.WSSetting;
import org.springframework.stereotype.Service;

/**
 * @author Limmy
 * @date 2020/5/3 9:31 下午
 */
@Service("SettingService")
public class SettingService {
    private static final WSSetting wsSetting = new WSSetting("127.0.0.1", 8088);

    public WSSetting getWsSetting() {
        return wsSetting;
    }
}
