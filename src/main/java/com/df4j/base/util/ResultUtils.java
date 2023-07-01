package com.df4j.base.util;

import com.df4j.base.pojo.PageDto;
import com.df4j.base.pojo.ResultDto;

import java.util.Arrays;
import java.util.List;

public class ResultUtils {

    public static <T> ResultDto<T> error(int errorNo, String errorInfo) {
        ResultDto resultDto = new ResultDto();
        resultDto.setErrorInfo(errorInfo);
        resultDto.setErrorNo(errorNo);
        return resultDto;
    }


    public static <T> ResultDto<T> success(PageDto page, List<T> data) {
        ResultDto resultDto = new ResultDto();
        resultDto.setData(data);
        resultDto.setPage(page);
        return resultDto;
    }

    public static <T> ResultDto<T> success(List<T> data) {
        ResultDto resultDto = new ResultDto();
        resultDto.setData(data);
        return resultDto;
    }

    public static <T> ResultDto<T> successObj(T data) {
        return success(Arrays.asList(data));
    }
}
