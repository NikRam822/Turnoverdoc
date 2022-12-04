package com.turnoverdoc.turnover.error;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.turnoverdoc.turnover.TurnoverApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorConverter {
    private static List<ErrorDto> errors;

    public ErrorConverter() {
        String errorsPath = (String) TurnoverApplication.class.getClassLoader().getResource("errors.json").getFile();
        Type errorListType = new TypeToken<ArrayList<ErrorDto>>(){}.getType();
        try {
            errors = new Gson().fromJson(new FileReader(errorsPath), errorListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public static ErrorDto convertToErrorDto(String errorName) {
        return errors.stream().filter(error -> error.getName().equals(errorName)).findFirst().orElse(null);
    }
}
