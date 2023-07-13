package com.neil.pay.exception;

import lombok.Data;

/**
 * @author nihao
 * @date 2023/7/12
 */
@Data
public class PayException extends Exception {

    private static final long serialVersionUID = 4586740183314898999L;

    private String errorMsg;
    private String code;

    public PayException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public PayException(String customErrorMsg, Throwable tr) {
        super(customErrorMsg, tr);
        this.errorMsg = customErrorMsg;
    }
}
