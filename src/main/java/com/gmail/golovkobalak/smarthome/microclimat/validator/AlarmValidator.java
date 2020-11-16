package com.gmail.golovkobalak.smarthome.microclimat.validator;

public interface AlarmValidator<T> {
    boolean validate(T target);
}
