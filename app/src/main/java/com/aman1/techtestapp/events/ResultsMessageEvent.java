package com.aman1.techtestapp.events;

import com.aman1.techtestapp.mvp.model.Result;

import java.util.List;

public class ResultsMessageEvent {


    private final List<Result> resultMessage;

    public ResultsMessageEvent(List<Result> resultMessage) {
        this.resultMessage = resultMessage;
    }

    public List<Result> getResultMessage() {
        return resultMessage;
    }

    public int size(){
        return resultMessage.size();
    }
}
