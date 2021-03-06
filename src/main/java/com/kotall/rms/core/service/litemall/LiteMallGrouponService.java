package com.kotall.rms.core.service.litemall;

import com.kotall.rms.common.entity.litemall.LiteMallGrouponEntity;
import com.kotall.rms.common.utils.Page;
import com.kotall.rms.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author kotall
 * @date 2018年11月13日 下午6:32:18
 * @since 1.0.0
 */
public interface LiteMallGrouponService extends BaseService<LiteMallGrouponEntity> {

    Page<LiteMallGrouponEntity> queryGroupOnByPage(Map<String, Object> params);

    List<LiteMallGrouponEntity> queryJoinRecord(Map<String, Object> params);

    List<LiteMallGrouponEntity> queryMyGroupon(Map<String, Object> params);

    List<LiteMallGrouponEntity> queryMyJoinGroupon(Map<String, Object> params);

    int countGroupOn(Map<String, Object> params);

    /**
     * 根据订单ID查询该笔订单对应的团购记录
     * @param orderId
     * @return
     */
    LiteMallGrouponEntity queryByOrderId(Integer orderId);
}
