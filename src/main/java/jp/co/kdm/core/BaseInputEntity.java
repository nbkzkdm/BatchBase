package jp.co.kdm.core;

import lombok.Data;

@Data
public abstract class BaseInputEntity implements InputEntity {
    private String functionId;
}