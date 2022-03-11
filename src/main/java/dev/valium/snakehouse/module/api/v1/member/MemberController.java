package dev.valium.snakehouse.module.api.v1.member;


import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ListResult;
import dev.valium.snakehouse.module.api.model.response.SingleResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.member.dto.MemberDTO;
import dev.valium.snakehouse.module.member.Member;
import dev.valium.snakehouse.module.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"1. Member"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    /********************************** 조회 ***************************************/
    @ApiOperation(value = "회원 조회", notes = "회원 Id에 해당하는 정보를 조회한다.")
    @GetMapping(value = "/members/{id}")
    public SingleResult<MemberDTO> findMember(
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "id") Long id) {
        Member member = memberService.findMember(id);
        MemberDTO memberDTO = MemberDTO.createMemberDTO(member);

        return responseService.getSingleResult(memberDTO);
    }

    @ApiOperation(value = "모든 회원 조회", notes = "모든 회원을 조회한다.")
    @GetMapping(value = "/members")
    public ListResult<MemberDTO> findAllMembers() {

        // TODO stream + foreach + new 비용이 비싸다.
        // TODO 그렇다고 하나를 위해 재사용성 떨어지는 DTO repo를 만들기도 이상하다.
        // TODO 일단 이렇게 짜고 나중에 jpql DAO로 받든 해야한다.

        List<Member> allMembers = memberService.findAllMembers();
        List<MemberDTO> MemberDTOs = allMembers.stream()
                .map(MemberDTO::createMemberDTO)
                .collect(Collectors.toList());

        return responseService.getListResult(MemberDTOs);
    }


    /********************************** 입력 ***************************************/
    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping(value = "/members")
    public SingleResult<MemberDTO> saveMember(
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        Member member = memberService.saveMember(Member.createMember(name));

        return responseService.getSingleResult(MemberDTO.createMemberDTO(member));
    }

    /********************************** 수정 ***************************************/
    @ApiOperation(value = "회원 수정", notes = "ID에 해당하는 회원정보를 수정한다.")
    @PutMapping(value = "/members/{id}")
    public SingleResult<MemberDTO> modifyMember(
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "id") Long id,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        Member member = memberService.findMember(id);
        Member modifiedMember = memberService.modifyMember(member, Member.createMember(name));

        return responseService.getSingleResult(MemberDTO.createMemberDTO(modifiedMember));
    }

    /********************************** 삭제 ***************************************/
    @ApiOperation(value = "회원 삭제", notes = "ID에 해당하는 회원을 삭제한다.")
    @DeleteMapping(value = "/members/{id}")
    public CommonResult deleteMember(
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "id") Long id) {
        Member member = memberService.findMember(id);
        memberService.deleteMember(member);

        return responseService.getSuccessResult();
    }
}
