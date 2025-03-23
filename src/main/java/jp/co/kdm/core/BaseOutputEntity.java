package jp.co.kdm.core;

import jp.co.kdm.core.BatchStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class BaseOutputEntity implements OutputEntity {
    private BatchStatus status;
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        this.messages.add(message);
    }
}
