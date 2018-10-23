package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.CommonArea;
import com.jopool.chargingStation.www.base.entity.CommonSystemConfig;
import com.jopool.chargingStation.www.base.entity.OpenArea;
import com.jopool.chargingStation.www.dao.CommonAreaMapper;
import com.jopool.chargingStation.www.dao.CommonSystemConfigMapper;
import com.jopool.chargingStation.www.dao.OpenAreaMapper;
import com.jopool.chargingStation.www.service.SystemConfigService;
import com.jopool.chargingStation.www.vo.OpenAreaVo;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by synn on 2017/8/31.
 */
@Service
public class SystemConfigServiceImpl extends BaseServiceImpl implements SystemConfigService {

    @Resource
    private CommonSystemConfigMapper commonSystemConfigMapper;
    @Resource
    private OpenAreaMapper           openAreaMapper;
    @Resource
    private CommonAreaMapper         commonAreaMapper;


    @Override
    public List<CommonSystemConfig> searchBaseVo(SearchBaseVo searchBaseVo, RowBounds page) {
        return commonSystemConfigMapper.selectSearchBaseVo(searchBaseVo, page);
    }

    @Override
    public CommonSystemConfig getById(String commonSystemConfigId) {
        return commonSystemConfigMapper.selectByPrimaryKey(commonSystemConfigId);
    }

    @Override
    public void addCommonSystemConfig(CommonSystemConfig commonSystemConfig) {
        commonSystemConfigMapper.insert(commonSystemConfig);
    }

    @Override
    public Result modifyCommonSystemConfig(CommonSystemConfig commonSystemConfig) {
        commonSystemConfigMapper.updateByPrimaryKeySelective(commonSystemConfig);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removeSystemConfigId(String commonSystemConfigId) {
        commonSystemConfigMapper.deleteByPrimaryKey(commonSystemConfigId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public CommonSystemConfig getConfigByName(String name) {
        return commonSystemConfigMapper.selectByName(name);
    }

    @Override
    public void addOpenArea(OpenArea openArea) {
        openAreaMapper.insert(openArea);
    }

    @Override
    public Result modifyArea(OpenArea openArea) {
        openAreaMapper.updateByPrimaryKeySelective(openArea);
        return new Result(Code.SUCCESS);
    }

    @Override
    public OpenArea getByOpenAreaId(String openAreaId) {
        return openAreaMapper.selectByPrimaryKey(openAreaId);
    }

    @Override
    public List<OpenAreaVo> searchBaseVoOpenArea(SearchBaseVo searchBaseVo, RowBounds page) {
        List<OpenAreaVo> openAreaVos=new ArrayList<OpenAreaVo>();
        List<OpenArea> openAreas=openAreaMapper.selectBaseVo(searchBaseVo,page);
        for(OpenArea openArea:openAreas){
            //查找 市级
            CommonArea commonAreaCity=commonAreaMapper.selectByPrimaryKey(openArea.getAreaId());
            String name="";
            if(commonAreaCity!=null){
                String[] location=commonAreaCity.getLocation().split(",");
                //查找省级
                //判断Code 是否相等，有存在北京市 天津市，防止 重复
                if(!location[0].equals(commonAreaCity.getCode())){
                    CommonArea commonAreaProvince=commonAreaMapper.selectByCommonAreaCode(location[0]);
                    if(commonAreaProvince!=null){
                        name=commonAreaProvince.getName()+"-"+commonAreaCity.getName();
                    }
                }else {
                    name=commonAreaCity.getName();
                }
            }
            OpenAreaVo openAreaVo=new OpenAreaVo(openArea,name);
            openAreaVos.add(openAreaVo);
        }
        return openAreaVos;
    }

    @Override
    public Result removeOpenArea(String openAreaId) {
        openAreaMapper.deleteByPrimaryKey(openAreaId);
        return new Result(Code.SUCCESS);
    }
}
