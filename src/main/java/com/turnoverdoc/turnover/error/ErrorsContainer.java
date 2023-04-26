package com.turnoverdoc.turnover.error;

public class ErrorsContainer {
    public static ErrorDto TURN1 = ErrorConverter.convertToErrorDto("TURN1");
    public static ErrorDto TURN2 = ErrorConverter.convertToErrorDto("TURN2");
    public static ErrorDto TURN3 = ErrorConverter.convertToErrorDto("TURN3");
    public static ErrorDto TURN4 = ErrorConverter.convertToErrorDto("TURN4");

    public static ErrorDto TLG_1 = ErrorConverter.convertToErrorDto("TLG_1");
    public static ErrorDto TLG_2 = ErrorConverter.convertToErrorDto("TLG_2");
    public static ErrorDto TLG_3 = ErrorConverter.convertToErrorDto("TLG_3");

    //TODO: create exception for other cases(NullPointer....)
}
