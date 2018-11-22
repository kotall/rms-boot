package com.kotall.rms.api.controller;

import com.kotall.rms.api.annotation.LoginUser;
import com.kotall.rms.common.entity.litemall.LiteMallAddressEntity;
import com.kotall.rms.common.utils.RegexUtil;
import com.kotall.rms.common.utils.Result;
import com.kotall.rms.core.service.litemall.LiteMallAddressService;
import com.kotall.rms.core.service.sys.SysAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/address")
@Validated
@Slf4j
public class WxAddressController {

    @Autowired
    private LiteMallAddressService addressService;
    @Autowired
    private SysAreaService regionService;

    /**
     * 用户收货地址列表
     *
     * @param userId 用户ID
     * @return 收货地址列表
     *   成功则
     *  {
     *      code: 0,
     *      msg: '成功',
     *      data: xxx
     *  }
     *   失败则 { code: XXX, msg: XXX }
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId) {
        if(userId == null){
            return Result.unlogin();
        }
        List<LiteMallAddressEntity> addressList = addressService.queryByUid(userId);
        List<Map<String, Object>> addressVoList = new ArrayList<>(addressList.size());
        for(LiteMallAddressEntity address : addressList){
            Map<String, Object> addressVo = new HashMap<>();
            addressVo.put("id", address.getId());
            addressVo.put("name", address.getName());
            addressVo.put("mobile", address.getMobile());
            addressVo.put("isDefault", address.getIsDefault());
            String province = regionService.getAreaById(new Long(address.getProvinceId())).getName();
            String city = regionService.getAreaById(new Long(address.getCityId())).getName();
            String area = regionService.getAreaById(new Long(address.getAreaId())).getName();
            String addr = address.getAddress();
            String detailedAddress = province + city + area + " " + addr;
            addressVo.put("detailedAddress", detailedAddress);

            addressVoList.add(addressVo);
        }
        return Result.ok().put("data", addressVoList);
    }

    /**
     * 收货地址详情
     *
     * @param userId 用户ID
     * @param id 收获地址ID
     * @return 收货地址详情
     *   成功则
     *  {
     *      code: 0,
     *      msg: '成功',
     *      data:
     *        {
     *           id: xxx,
     *           name: xxx,
     *           provinceId: xxx,
     *           cityId: xxx,
     *           areaId: xxx,
     *           mobile: xxx,
     *           address: xxx,
     *           isDefault: xxx,
     *           version: xxx
     *           provinceName: xxx,
     *           cityName: xxx,
     *           areaName: xxx
     *        }
     *  }
     *   失败则 { code: XXX, msg: XXX }
     */
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, @NotNull Integer id) {

        if(userId == null){
            return Result.unlogin();
        }

        LiteMallAddressEntity address = addressService.getLiteMallAddressById(new Long(id));
        if(address == null){
            return Result.badArgumentValue();
        }

        Map<Object, Object> data = new HashMap<>();
        data.put("id", address.getId());
        data.put("name", address.getName());
        data.put("provinceId", address.getProvinceId());
        data.put("cityId", address.getCityId());
        data.put("areaId", address.getAreaId());
        data.put("mobile", address.getMobile());
        data.put("address", address.getAddress());
        data.put("isDefault", address.getIsDefault());
        String pname = regionService.getAreaById(new Long(address.getProvinceId())).getName();
        data.put("provinceName", pname);
        String cname = regionService.getAreaById(new Long(address.getCityId())).getName();
        data.put("cityName", cname);
        String dname = regionService.getAreaById(new Long(address.getAreaId())).getName();
        data.put("areaName", dname);
        return Result.ok().put("data", data);
    }

    private Object validate(LiteMallAddressEntity address) {
        String name = address.getName();
        if(StringUtils.isEmpty(name)){
            return Result.badArgument();
        }

        // 测试收货手机号码是否正确
        String mobile = address.getMobile();
        if(StringUtils.isEmpty(mobile)){
            return Result.badArgument();
        }
        if(!RegexUtil.isMobileExact(mobile)){
            return Result.badArgument();
        }

        Integer pid = address.getProvinceId();
        if(pid == null){
            return Result.badArgument();
        }
        if(regionService.getAreaById(new Long(pid)) == null){
            return Result.badArgumentValue();
        }

        Integer cid = address.getCityId();
        if(cid == null){
            return Result.badArgument();
        }
        if(regionService.getAreaById(new Long(cid)) == null){
            return Result.badArgumentValue();
        }

        Integer aid = address.getAreaId();
        if(aid == null){
            return Result.badArgument();
        }
        if(regionService.getAreaById(new Long(aid)) == null){
            return Result.badArgumentValue();
        }

        String detailedAddress = address.getAddress();
        if(StringUtils.isEmpty(detailedAddress)){
            return Result.badArgument();
        }

        Integer isDefault = address.getIsDefault();
        if(isDefault == null){
            return Result.badArgument();
        }
        return null;
    }

    /**
     * 添加或更新收货地址
     *
     * @param userId 用户ID
     * @param address 用户收货地址
     * @return 添加或更新操作结果
     *   成功则 { code: 0, msg: '成功' }
     *   失败则 { code: XXX, msg: XXX }
     */
    @PostMapping("save")
    public Object save(@LoginUser Integer userId, @RequestBody LiteMallAddressEntity address) {
        if(userId == null){
            return Result.unlogin();
        }
        Object error = validate(address);
        if(error != null){
            return error;
        }

        if(address.getIsDefault() == 1){
            // 重置其他收获地址的默认选项
            addressService.resetDefault(userId);
        }

        if (address.getId() == null || address.getId().equals(0)) {
            address.setId(null);
            address.setUserId(userId);
            addressService.saveLiteMallAddress(address);
        } else {
            address.setUserId(userId);
            if(addressService.updateLiteMallAddress(address) == 0){
                return Result.updatedDataFailed();
            }
        }
        return Result.ok().put("data", address.getId());
    }

    /**
     * 删除收货地址
     *
     * @param userId 用户ID
     * @param address 用户收货地址
     * @return 删除结果
     *   成功则 { code: 0, msg: '成功' }
     *   失败则 { code: XXX, msg: XXX }
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody LiteMallAddressEntity address) {
        if(userId == null){
            return Result.unlogin();
        }
        Integer id = address.getId();
        if(id == null){
            return Result.badArgument();
        }

        addressService.batchRemove(new Long[]{new Long(id)});
        return Result.ok();
    }
}