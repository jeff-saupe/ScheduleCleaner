package de.saupe.jeff.schedulecleaner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ResponseHandler {
    public abstract void onDone(String result);
    public abstract void onError(String message);
}