package dev.valium.snakehouse.module.api.service;

import dev.valium.snakehouse.module.api.model.response.CommonResult;
import dev.valium.snakehouse.module.api.model.response.ListResult;
import dev.valium.snakehouse.module.api.model.response.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    public static enum CommonResponse {
        SUCCESS(0, "성공 했습니다."),
        FAIL(-1, "실패 했습니다.");


        private final int code;
        private final String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() { return code; }
        public String getMsg() { return msg; }
    }

    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> singleResult = new SingleResult<>();
        singleResult.setData(data);

        return singleResult;
    }

    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> results = new ListResult<>();
        results.setList(list);

        return results;
    }

    private <T> void setResult(CommonResult result, boolean isSuccess) {
        if (isSuccess) setSuccessResult(result);
        else setFailResult(result);
    }
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    private void setFailResult(CommonResult result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);

        return result;
    }
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}