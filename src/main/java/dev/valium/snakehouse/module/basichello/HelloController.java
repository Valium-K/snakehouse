package dev.valium.snakehouse.module.basichello;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final RedisTemplate<String, ?> redisTemplate;

    @GetMapping("/hello-api")
    public String hello() {
        return "hello";
    }

    @GetMapping("/robots.txt")
    public String robots() {
        return "User-agent: *\n" +
                "Disallow: /";
    }

    @GetMapping("/redis")
    public String redis() {
        Set<String> keys = redisTemplate.keys("*");

        return keys != null ? keys.toString() : "[]";
    }
}
