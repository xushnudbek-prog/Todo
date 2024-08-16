package project.spring.jdbc.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import project.spring.jdbc.exceptions.NotFoundException;

@ControllerAdvice("project.spring.jdbc")
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ModelAndView error_404(HttpServletRequest request, NotFoundException e) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/404");
        mav.addObject("message", e.getMessage());
        mav.addObject("path", e.getPath());
        return mav;
    }
}
