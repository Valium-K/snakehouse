package dev.valium.snakehouse.module.basichello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello-api")
    public String hello() {
        return "hello";
    }

    @GetMapping("/robots.txt")
    public String robots() {
        return "User-agent: *\n" +
                "Disallow: /";
    }
}
