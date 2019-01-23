package cn.lucas.ad.dao;

import cn.lucas.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    /**
     * 根据 adplan id 和 userid 查询 广告计划
     * @param id
     * @param userId
     * @return
     */
    AdPlan findByIdAndUserId(Long id,Long userId);

    /**
     * 根据 ids 和 userid 查询广告计划集合
     * @param ids
     * @param userId
     * @return
     */
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);


    /**
     * userId 和 planName 查询 AdPlan
     * @param userId
     * @param planName
     * @return
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 根据状态查询 AdPlan 集合
     * @param status
     * @return
     */
    List<AdPlan> findAllByPlanStatus(Integer status);
}
