package dev.valium.snakehouse.module.api.v1.member;

import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.v1.common.UnsupportedController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4. Sign"})
@RestController
@RequestMapping(value = "/v1")
public class UnsupportedSignController extends UnsupportedController {

    public UnsupportedSignController(ResponseService responseService) {
        super(responseService);
    }

    /********************************** 입력 ***************************************/
    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @GetMapping(value = "/sign-in")
    public CommonResult getSignIn() {
        return unSupportedMethod();
    }

    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @GetMapping(value = "/sign-up")
    public CommonResult getSignup() {
        return unSupportedMethod();
    }

    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @GetMapping(value = "/sign-in/{provider}")
    public CommonResult getSignInProvider() {
        return unSupportedMethod();
    }

    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @GetMapping(value = "/sign-up/{provider}")
    public CommonResult getSignupProvider() {
        return unSupportedMethod();
    }

    /********************************** 수정 ***************************************/
    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @PutMapping(value = "/sign-in")
    public CommonResult putSignIn() {
        return unSupportedMethod();
    }

    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @PutMapping(value = "/sign-up")
    public CommonResult putSignup() {
        return unSupportedMethod();
    }

    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @PutMapping(value = "/sign-in/{provider}")
    public CommonResult putSignInProvider() {
        return unSupportedMethod();
    }

    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @PutMapping(value = "/sign-up/{provider}")
    public CommonResult putSignupProvider() {
        return unSupportedMethod();
    }

    /********************************** 삭제 ***************************************/
    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @DeleteMapping(value = "/sign-in")
    public CommonResult deleteSignIn() {
        return unSupportedMethod();
    }

    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @DeleteMapping(value = "/sign-up")
    public CommonResult deleteSignup() {
        return unSupportedMethod();
    }

    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @DeleteMapping(value = "/sign-in/{provider}")
    public CommonResult deleteSignInProvider() {
        return unSupportedMethod();
    }

    @ApiOperation(value = "제공 되지 않습니다.", notes = "제공 되지 않습니다.")
    @DeleteMapping(value = "/sign-up/{provider}")
    public CommonResult deleteSignupProvider() {
        return unSupportedMethod();
    }
}
