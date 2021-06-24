package name.xu1.debug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xu
 */
@RestController
@RequestMapping("/xuDebug")
@Slf4j
public class XuDebugController {
    /**
     * 企业角色列表查询
     */
    @GetMapping(value = "")
    public String list() {
        return "Xu Debug Enable";
    }
}
