package com.stormer.common.logger;

import org.aspectj.lang.annotation.Pointcut;

public class LoggingPointcut {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restcontroller() {
        // Do nothing
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postmapping() {
        // Do nothing
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getmapping() {
        // Do nothing
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void service() {
        // Do nothing
    }

    @Pointcut("within(@org.springframework.stereotype.Component *)")
    public void component() {
        // Do nothing
    }
}
