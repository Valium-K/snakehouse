package dev.valium.snakehouse.module.api.v1.common;

import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class UnsupportedController {
    private final ResponseService responseService;

    protected CommonResult unSupportedMethod() {
        return responseService.getFailResult(0, "제공 되지 않습니다.");
    }
}
