package br.com.alura.forum.controller.validation;

import br.com.alura.forum.controller.validation.dto.ErrorMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomRestExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorMessageDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorMessageDTO> messageDTOS = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(error -> addMensagemErro(messageDTOS, error));
        return messageDTOS;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFound(EntityNotFoundException ex){

    }

    private void addMensagemErro(List<ErrorMessageDTO> messageDTOS, FieldError error) {
        messageDTOS.add(new ErrorMessageDTO(error.getField(), getMensagem(error)));
    }

    private String getMensagem(FieldError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }

}
