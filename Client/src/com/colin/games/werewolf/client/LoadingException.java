package com.colin.games.werewolf.client;

public class LoadingException extends RuntimeException {
    public LoadingException(Exception e) {
        super(e);
    }
}
