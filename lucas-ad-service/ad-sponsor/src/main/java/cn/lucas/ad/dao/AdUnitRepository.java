package cn.lucas.ad.dao;

import cn.lucas.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    /**
     * 根据 planId 和 unitName 查询 推广单元
     * @param planId
     * @param unitName
     * @return
     */
    AdUnit findByPlanIdaAndUnitName(Long planId, String unitName);


    /**
     * 根据推广单元的状态查询 推广单元的集合
     * @param status
     * @return
     */
    List<AdUnit> findAllByUnitStatus(Integer status);
}
