package com.zzz.mt.exceptions;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public class NotSupportException extends MtDaoException {

    public NotSupportException() {
        super();
    }

    public NotSupportException(String message) {
        super(message);
    }

    public NotSupportException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
