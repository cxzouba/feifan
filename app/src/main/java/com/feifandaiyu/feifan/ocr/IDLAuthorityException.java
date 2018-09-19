package com.feifandaiyu.feifan.ocr;

/**
 * Created by houdaichang on 2017/10/13.
 */

public class IDLAuthorityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IDLAuthorityException() {
    }

    public IDLAuthorityException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public IDLAuthorityException(String detailMessage) {
        super(detailMessage);
    }

    public IDLAuthorityException(Throwable throwable) {
        super(throwable);
    }
}
