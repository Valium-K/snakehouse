package dev.valium.snakehouse.module.api.v1.member;

import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.v1.common.UnsupportedController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Member"})
@RestController
@RequestMapping(value = "/v1")
public class UnsupportedMemberController extends UnsupportedController {

    public UnsupportedMemberController(ResponseService responseService) {
        super(responseService);
    }

    /********************************** 입력 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @PostMapping(value = "/member")
    public CommonResult postMember() {
        return unSupportedMethod();
    }

    @ApiOperation(value = value, notes = notes)
    @PostMapping(value = "/members/{id}")
    public CommonResult postMembersId() {
        return unSupportedMethod();
    }

    /********************************** 수정 ***************************************/
    @ApiOperation(value = value, notes = notes)
    @PutMapping(value = "/members")
    public CommonResult putMembers() {
        return unSupportedMethod();
    }
}
