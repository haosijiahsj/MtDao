package com.hhssjj.mt.exceptions;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public class AnnotationNotFoundException extends MtDaoException {

    public AnnotationNotFoundException() {
        super();
    }

    public AnnotationNotFoundException(String message) {
        super(message);
    }

    public AnnotationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
