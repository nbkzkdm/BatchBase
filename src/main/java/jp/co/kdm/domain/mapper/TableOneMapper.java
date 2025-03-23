package jp.co.kdm.domain.mapper;

import jp.co.kdm.domain.entity.TableOneEntity;
import java.util.List;

public interface TableOneMapper {
    List<TableOneEntity> findAll();
    TableOneEntity findById(int userId);
    void insert(TableOneEntity entity);
}
