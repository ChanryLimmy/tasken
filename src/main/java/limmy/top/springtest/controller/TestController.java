package limmy.top.springtest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Limmy
 * @date 2020/5/2 4:09 下午
 */
@RestController
public class TestController {
    @RequestMapping("/")
    public String index() {
        return "Welcome to my system!!!";
    }
}
