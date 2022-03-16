package dev.valium.snakehouse.module.api.v1.member;


import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ListResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.model.response.SingleResult;
import dev.valium.snakehouse.module.member.Member;
import dev.valium.snakehouse.module.member.MemberService;
import dev.valium.snakehouse.module.member.dto.MemberDTO;
import dev.valium.snakehouse.module.security.SecurityContextHolderHelper;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;

    /********************************** 조회 ***************************************/
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 조회", notes = "회원 Id에 해당하는 정보를 조회한다.")
    @GetMapping(value = "/members/{member-id}")
    public SingleResult<MemberDTO> findMember(
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "member-id") String memberId) {
        Member member = memberService.findMember(memberId);
        MemberDTO memberDTO = MemberDTO.createMemberDTO(member);

        return responseService.getSingleResult(memberDTO);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 조회", notes = "토큰에 해당하는 회원 정보를 조회한다.")
    @GetMapping(value = "/member")
    public SingleResult<MemberDTO> findMember() {
        Member member = SecurityContextHolderHelper.getMember();
        MemberDTO memberDTO = MemberDTO.createMemberDTO(member);

        return responseService.getSingleResult(memberDTO);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping(value = "/members")
    public SingleResult<MemberDTO> saveMember(
            @ApiParam(value = "회원 ID", required = true) @RequestParam String memberId,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password) {

        Member member = memberService.saveMember(Member.createMember(memberId, passwordEncoder.encode(password), name));

        return responseService.getSingleResult(MemberDTO.createMemberDTO(member));
    }

    /********************************** 수정 ***************************************/
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "ID에 해당하는 회원정보를 수정한다.")
    @PutMapping(value = "/members/{member-id}")
    public SingleResult<MemberDTO> modifyMember(
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "member-id") String memberId,
            @ApiParam(value = "회원 이름", required = false) @RequestParam String name,
            @ApiParam(value = "회원 비밀번호", required = false) @RequestParam String password) {

        Member member = memberService.findMember(memberId);
        Member modifiedMember = memberService.modifyMember(member, Member.createMember(memberId, passwordEncoder.encode(password), name));

        return responseService.getSingleResult(MemberDTO.createMemberDTO(modifiedMember));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "토큰에 해당하는 회원 정보를 수정한다.")
    @PutMapping(value = "/member")
    public SingleResult<MemberDTO> modifyMember(
            @ApiParam(value = "회원 이름", required = false) @RequestParam String name,
            @ApiParam(value = "회원 비밀번호", required = false) @RequestParam String password) {
        Member member = SecurityContextHolderHelper.getMember();
        Member modifiedMember = memberService.modifyMember(member, Member.createMember(member.getMemberId(), passwordEncoder.encode(password), name));

        return responseService.getSingleResult(MemberDTO.createMemberDTO(modifiedMember));
    }

    /********************************** 삭제 ***************************************/
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "ID에 해당하는 회원을 삭제한다.")
    @DeleteMapping(value = "/members/{member-id}")
    public CommonResult deleteMember(
            @ApiParam(value = "회원 ID", required = true) @PathVariable(name = "member-id") String memberId) {

        Member member = memberService.findMember(memberId);
        memberService.deleteMember(member);

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "토큰에 해당하는 회원을 삭제한다.")
    @DeleteMapping(value = "/member")
    public CommonResult deleteMember() {

        Member member = SecurityContextHolderHelper.getMember();
        memberService.deleteMember(member);

        return responseService.getSuccessResult();
    }
}