package limmy.top.springtest.controller;

import limmy.top.springtest.type.JsonResponse;
import limmy.top.springtest.type.Publisher;
import limmy.top.springtest.service.PublisherService;
import limmy.top.springtest.type.Task;
import limmy.top.springtest.vo.PublisherVO;
import limmy.top.springtest.vo.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Limmy
 * @date 2020/5/3 11:04 上午
 */
@RestController
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @PostMapping("/add")
    public JsonResponse addPublisher(PublisherVO publisherVO) {
        Publisher result = publisherService.addPublisher(publisherVO.getName(), publisherVO.getPwd(), publisherVO.isClean());
        return result == null ? JsonResponse.CONFLICT : JsonResponse.success(result);
    }

    @DeleteMapping("/remove")
    public JsonResponse removePublisher(PublisherVO publisherVO) {
        boolean ok = publisherService.removePublisher(publisherVO.getName(), publisherVO.getPwd());
        return ok ? JsonResponse.SUCCESS : JsonResponse.BAD_REQUEST;
    }

    @PostMapping("/task/add")
    public JsonResponse addTask(TaskVO taskVO) {
        return publisherService.addTask(taskVO) ? JsonResponse.SUCCESS : JsonResponse.BAD_REQUEST;
    }

    @PostMapping("/task/update")
    public JsonResponse updateTask(TaskVO taskVO) {
        return publisherService.updateTask(taskVO) ? JsonResponse.SUCCESS : JsonResponse.BAD_REQUEST;
    }

    @PostMapping("/task/finish")
    public JsonResponse finishTask(TaskVO taskVO) {
        return publisherService.finishTask(taskVO) ? JsonResponse.SUCCESS : JsonResponse.BAD_REQUEST;
    }

    @DeleteMapping("/task/remove")
    public JsonResponse removeTask(TaskVO taskVO) {
        return publisherService.removeTask(taskVO) ? JsonResponse.SUCCESS : JsonResponse.BAD_REQUEST;
    }

    @GetMapping("/list")
    public JsonResponse listPublisher() {
        return JsonResponse.success(publisherService.listPublisher());
    }

    @GetMapping("/task/list/{name}")
    public JsonResponse listTasks(@PathVariable("name") String name) {
        return JsonResponse.success(publisherService.listTasks(name));
    }

    @GetMapping("/life")
    public JsonResponse activateTask(PublisherVO publisherVO) {
        return publisherService.updateLifeTime(publisherVO) ? JsonResponse.SUCCESS : JsonResponse.NO_CONTENT;
    }
}
