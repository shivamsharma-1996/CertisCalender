package com.shivam.certis_complex;

import java.util.HashMap;
import java.util.Map;

public enum CertiseItemType
{
    FIRST_ROW(1), OTHER_ROW(2);

    private final int code;
    private static final Map<Integer, CertiseItemType> valuesByCode;

    static
    {
        valuesByCode = new HashMap<>();
        for (CertiseItemType checkpoint : CertiseItemType.values()) {
            valuesByCode.put(checkpoint.code, checkpoint);
        }
    }

    CertiseItemType(Integer code) {
        this.code = code;
    }

    public static CertiseItemType lookupByCode(int code)
    {
        return valuesByCode.get(code);
    }

    public Integer getCode() {
        return code;
    }
}