package jp.co.kdm.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.kdm.domain.entity.TableTwoEntity;

@Mapper
public interface TableTwoMapper {
    void insert(TableTwoEntity entity);

    List<TableTwoEntity> findAll();

    void deleteAll();

    void insertBatch(@Param("list") List<TableTwoEntity> entities);

}