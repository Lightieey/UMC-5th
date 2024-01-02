package com.umc.demo.apiPayload.exception.handler;

import com.umc.demo.apiPayload.code.BaseErrorCode;
import com.umc.demo.apiPayload.exception.GeneralException;

public class FoodCategoryHandler extends GeneralException {

    public FoodCategoryHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
