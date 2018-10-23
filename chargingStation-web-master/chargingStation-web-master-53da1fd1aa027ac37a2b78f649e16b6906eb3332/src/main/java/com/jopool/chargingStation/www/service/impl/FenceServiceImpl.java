package com.jopool.chargingStation.www.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jopool.chargingStation.www.base.entity.Fence;
import com.jopool.chargingStation.www.dao.FenceMapper;
import com.jopool.chargingStation.www.service.FenceService;
import com.jopool.chargingStation.www.vo.FenceTreeVo;
import com.jopool.jweb.cache.annotation.CacheFlush;
import com.jopool.jweb.cache.annotation.Cacheable;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.UUIDUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gexin on 2017/10/26.
 */
@Service
public class FenceServiceImpl extends BaseServiceImpl implements FenceService, SelfBeanAware<FenceService> {
    private FenceService fenceService;
    @Resource
    private FenceMapper  fenceMapper;

    @Override
    @CacheFlush
    public void addGovernmentFence(Fence fence) {
        fence.setId(UUIDUtils.createId());
        fence.setCreationTime(new Date());
        fence.setIsDeleted(false);
        fenceMapper.insert(fence);
    }

    @Override
    @CacheFlush
    public void modifyGovernmentFence(Fence fence) {
        fenceMapper.updateByPrimaryKeySelective(fence);
    }

    @Override
    @CacheFlush
    public void removeGovernmentFence(String fenceId) {
        fenceMapper.deleteByPrimaryKey(fenceId);
    }

    @Override
    public List<Fence> getByGovernmentId(String fenceId, String name, String governmentId) {
        return fenceMapper.selectFenceList(fenceId, name, governmentId);
    }

    @Override
    public List<FenceTreeVo> getByGovernmentIdTree(Fence fence) {
        List<FenceTreeVo> fenceTreeVos = new ArrayList<>();
        List<Fence> fences = fenceMapper.selectFenceList("", fence.getName(), fence.getGovernmentId());
        for (Fence fenceNew : fences) {
            FenceTreeVo fenceTreeVo = new FenceTreeVo(fenceNew);
            fenceTreeVos.add(fenceTreeVo);
        }
        return fenceTreeVos;
    }

    @Override
    public Fence searchFenceId(String fenceId) {
        return fenceMapper.selectByPrimaryKey(fenceId);
    }

    @Override
    @Cacheable
    public List<Polygon> getPolygonsByCarownerId(String carownerId) {
        List<Polygon> polygons = new ArrayList<>();
        //TODO 根据carownerid获取govenmentid，再去获取围栏，这里返回全部
        List<Fence> fences = fenceService.getByGovernmentId(null, null, null);
        for (Fence fence : fences) {
            Iterator<Object> iterator = JSONArray.parseArray(fence.getFencePoints()).iterator();
            while (iterator.hasNext()) {
                JSONArray ja = (JSONArray) iterator.next();
                Iterator<Object> it = ja.iterator();
                Polygon polygon = new Polygon();
                while (it.hasNext()) {
                    JSONObject ob = (JSONObject) it.next();
                    polygon.addPoint((int) (ob.getDoubleValue("lng") * 1E5), (int) (ob.getDoubleValue("lat") * 1E5));
                }
                polygons.add(polygon);
            }
        }
        return polygons;
    }

    @Override
    public void setSelfBean(FenceService object) {
        this.fenceService = object;
    }
}
