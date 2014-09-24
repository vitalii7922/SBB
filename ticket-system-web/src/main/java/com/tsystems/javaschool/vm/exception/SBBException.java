package com.tsystems.javaschool.vm.exception;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Vlad on 02.09.2014.
 */
public abstract class SBBException extends Exception {
    public SBBException() {
        super();
    }

    public SBBException(String message) {
        super(message);
    }
}
