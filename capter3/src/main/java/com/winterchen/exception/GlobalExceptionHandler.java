package com.winterchen.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 *  统一异常处理
 * Created by Administrator on 2017/11/21.
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 同意处理Exception异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e){
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception",e);
        mv.addObject("url", req.getRequestURI());
        mv.setViewName(DEFAULT_ERROR_VIEW);
        return mv;
    }

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception{
        ErrorInfo<String> rs = new ErrorInfo<>();
        rs.setMessage(e.getMessage());
        rs.setCode(ErrorInfo.ERROR);
        rs.setUrl(req.getRequestURL().toString());
        rs.setData("some data");
        return rs;
    }
}
