package com.example.coffee_pet.aspect;

import com.example.coffee_pet.component.ResponseState;
import com.example.coffee_pet.component.ResponseStateWithAction;
import com.example.coffee_pet.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ResponseEntityWithStateAspect {

    private final ResourceService resourceService;

    @Around("@annotation(com.example.coffee_pet.annotation.ResponseWithState)")
    public  ResponseState aroundWithState(ProceedingJoinPoint pjp) throws Throwable {
        ResponseState res = (ResponseState) pjp.proceed();
        res.setState(resourceService.getResources());
        return res;
    }

    @Around("@annotation(com.example.coffee_pet.annotation.ExceptionResponseEntity)")
    public  ResponseEntity<ResponseState> around(ProceedingJoinPoint pjp) throws Throwable {
        ResponseEntity<?> res = (ResponseEntity<?>) pjp.proceed();
        ResponseState state = (ResponseState) res.getBody();
        state.setState(resourceService.getResources());

        return ResponseEntity.status(res.getStatusCode()).body(state);
    }

    @Around("@annotation(com.example.coffee_pet.annotation.ResponseWithStateAndAction)")
    public ResponseStateWithAction aroundWithStateAndAction(ProceedingJoinPoint pjp) throws Throwable {
        ResponseStateWithAction res = (ResponseStateWithAction) pjp.proceed();
        res.setState(resourceService.getResources());
        return res;
    }
}
