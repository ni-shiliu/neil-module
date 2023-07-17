package com.neil.pay.exception;

/**
 * @author nihao
 * @date 2023/7/17
 */
public class PayRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -7912832347267906023L;

    public PayRuntimeException(Throwable e) {
        super(e);
    }

    public PayRuntimeException(String msg) {
        super(msg);
    }

    public PayRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }
}
