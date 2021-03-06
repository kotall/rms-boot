package com.kotall.rms.core.service.sys;

import com.kotall.rms.common.entity.sys.SysConfigEntity;
import com.kotall.rms.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息表
 *
 * @author kotall
 * @date 2018年11月12日 下午9:18:45
 * @since 1.0.0
 */
public interface SysConfigService extends BaseService<SysConfigEntity> {

    /**
     * 根据key，获取配置的value值
     *
     * @param key           key
     */
    String getValue(String key);

    List<SysConfigEntity> queryAll(Map<String, Object> params);
}
