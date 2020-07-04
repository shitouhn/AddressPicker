package com.example.myroom.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by zhong on 2019/7/6.
 */
@Dao
public interface AreaDao {
    /**
     * 获取省
     * @return
     */
    @Query("SELECT * FROM areas WHERE parentId = 0")
    Flowable<List<Area>> getProvince();

    /**
     * 根据父节点id获取子节点数组
     * @param parentId 父节点id
     * @return 子节点数组
     */
    @Query("SELECT * FROM areas WHERE parentId = :parentId")
    Flowable<List<Area>> getAreaByParentId(String parentId);

    /**
     * 根据行政编号查找行政区
     * @param areaNo 行政编号
     * @return 行政区
     */
    @Query("SELECT * FROM areas WHERE AreaNo = :areaNo")
    Maybe<Area> getAreaByNo(String areaNo);

    /**
     * 根据一组行政编号，查找对应行政区
     * @param areaNoList 一组行政编号
     * @return 一组行政区
     */
    @Query("SELECT * FROM areas WHERE AreaNo IN (:areaNoList)")
    Flowable<List<Area>> getAreaList(List<String> areaNoList);
}
