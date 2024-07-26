package dev.patika.VeterinaryManagementSystem.core.utilies;

import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.response.vaccine.VaccineResponse;
import org.springframework.http.HttpStatus;

public class ResultHelper {

    public static <T> ResultData<T> created(T data, String message){
        return new ResultData<>(true, message, "201", data);
    }

    public static <T> ResultData<T> validateError(T data) {
        return new ResultData<>(false, Msg.VALIDATE_ERROR, "400", data);
    }

    public static <T> ResultData<T> success(T data){
        return new ResultData<>(true, Msg.OK, "200", data);
    }

    public static Result error() {
        return new Result(false, Msg.ERROR, "400");
    }

    public static <T> ResultData<T> ok(T data) {
        return new ResultData<>(true, Msg.OK, "200", data);
    }

    public static Result notFoundError(String msg){
        return new Result(false, msg, "404");
    }

    public static Result internalServerError(String message) {
        return new Result(false, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static <T> ResultData<T> internalServerError(T data, String message) {
        return new ResultData<>(false, message, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), data);
    }

    public static Result deleted(String message) {
        return new Result(true, message, "200");

    }

    public static <T> ResultData<T> protectionPeriodNotFinishedError(String message) {
        return new ResultData<>(false, message, "400", null);
    }


}