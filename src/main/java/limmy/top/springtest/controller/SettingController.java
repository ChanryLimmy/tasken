package limmy.top.springtest.controller;

import limmy.top.springtest.service.SettingService;
import limmy.top.springtest.type.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Limmy
 * @date 2020/5/3 9:26 下午
 */
@RestController
@RequestMapping("/settings")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping("/ws")
    public JsonResponse getWsSetting() {
        return JsonResponse.success(settingService.getWsSetting());
    }
}
