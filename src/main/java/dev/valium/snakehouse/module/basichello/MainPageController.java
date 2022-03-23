package dev.valium.snakehouse.module.basichello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainPageController {

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("/robots.txt")
    @ResponseBody
    public String robots() {
        return "User-agent: *\n" +
                "Disallow: /";
    }
}
