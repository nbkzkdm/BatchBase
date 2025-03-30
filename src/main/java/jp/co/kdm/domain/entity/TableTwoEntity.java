package jp.co.kdm.domain.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TableTwoEntity {
    private String id;
    private Timestamp date;
    private int value;
}
