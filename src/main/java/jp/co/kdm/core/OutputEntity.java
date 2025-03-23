package jp.co.kdm.core;

import jp.co.kdm.core.BatchStatus;
import java.util.List;

public interface OutputEntity {
    BatchStatus getStatus();
    List<String> getMessages();
}