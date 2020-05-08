package limmy.top.springtest;

import limmy.top.springtest.service.WSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Limmy
 * @date 2020/5/2 4:03 下午
 */
@SpringBootApplication
public class TaskyServer {
    @Autowired
    private static WSService wsService = new WSService();

    public static void main(String[] args) {
        SpringApplication.run(TaskyServer.class, args);
        wsService.init();
    }
}
