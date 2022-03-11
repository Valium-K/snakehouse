package dev.valium.snakehouse.module.api.exception.advice;

import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ResponseService;
import dev.valium.snakehouse.module.api.model.response.enums.GameResponse;
import dev.valium.snakehouse.module.api.model.response.enums.MemberResponse;
import dev.valium.snakehouse.module.game.score.exception.NoSuchGameException;
import dev.valium.snakehouse.module.member.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * API를 위한 Exception Advice
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(NoSuchMemberException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult noSuchMemberException(HttpServletRequest request, NoSuchMemberException e) {
        return responseService.getFailResult(MemberResponse.NO_SUCH_MEMBER_ID.getCode(), MemberResponse.NO_SUCH_MEMBER_ID.getMsg());
    }

    @ExceptionHandler(NoSuchGameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult noSuchGameException(HttpServletRequest request, NoSuchGameException e) {
        return responseService.getFailResult(GameResponse.NO_SUCH_GAME.getCode(), GameResponse.NO_SUCH_GAME.getMsg());
    }
}