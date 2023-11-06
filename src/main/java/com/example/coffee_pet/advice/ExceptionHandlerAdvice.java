package com.example.coffee_pet.advice;

import com.example.coffee_pet.annotation.ExceptionResponseEntity;
import com.example.coffee_pet.component.ResponseState;
import com.example.coffee_pet.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ActionNotFoundException.class)
    @ExceptionResponseEntity
    public ResponseEntity<ResponseState> handler(ActionNotFoundException e) {
        return buildResponse("Не найдено задача :" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChangeActionException.class)
    @ExceptionResponseEntity
    public ResponseEntity<ResponseState> handler(ChangeActionException e) {
        return buildResponse("Задача " + e.getMessage() + "не может быть изменена", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CoffeeOffException.class)
    @ExceptionResponseEntity
    public ResponseEntity<ResponseState> handler(CoffeeOffException e) {
        return buildResponse("Кофе-машина выключена", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CoffeeOffWhenWorkException.class)
    @ExceptionResponseEntity
    public ResponseEntity<ResponseState> handler(CoffeeOffWhenWorkException e) {
        return buildResponse("Кофе-машина не может быть выключена пока она делает кофе", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ActionException.class)
    @ExceptionResponseEntity
    public ResponseEntity<ResponseState> handler(ActionException e) {
        return buildResponse("Ошибка при выполнении задачи " + e.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ExceptionResponseEntity
    public ResponseEntity<ResponseState> handler(ResourceNotFoundException e) {
        return buildResponse("Не найден ресурс " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ExceptionResponseEntity
    public ResponseEntity<ResponseState> handler(MissingServletRequestParameterException e) {
        return buildResponse("Некорректные параметры запроса", HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<ResponseState> buildResponse(String msg, HttpStatus status) {
        ResponseState state = new ResponseState();
        switch (status) {
            case BAD_REQUEST:
                state.setPayload("BAD_REQUEST : " + msg);
                return new ResponseEntity<>(
                        state,
                        HttpStatus.BAD_REQUEST
                );
            case CONFLICT:
                state.setPayload("CONFLICT : " + msg);
                return new ResponseEntity<>(
                        state,
                        HttpStatus.CONFLICT
                );
            default:
                state.setPayload("BAD_GATEWAY : " + msg);
                return new ResponseEntity<>(
                        state,
                        HttpStatus.BAD_GATEWAY
                );
        }
    }

}
