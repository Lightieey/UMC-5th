package com.umc.demo.apiPayload.exception.handler;

import com.umc.demo.apiPayload.code.BaseErrorCode;
import com.umc.demo.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
