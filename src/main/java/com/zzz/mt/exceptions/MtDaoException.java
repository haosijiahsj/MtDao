package com.zzz.mt.exceptions;

/**
 * Created by hushengjun on 2017/8/8.
 */
public class MtDaoException extends RuntimeException {

    public MtDaoException() {
        super();
    }

    public MtDaoException(String message) {
        super(message);
    }

    public MtDaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
