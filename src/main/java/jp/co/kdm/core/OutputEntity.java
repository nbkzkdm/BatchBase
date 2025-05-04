package jp.co.kdm.core;

import java.util.List;

public interface OutputEntity {
    BatchStatus getStatus();
    List<String> getMessages();
}