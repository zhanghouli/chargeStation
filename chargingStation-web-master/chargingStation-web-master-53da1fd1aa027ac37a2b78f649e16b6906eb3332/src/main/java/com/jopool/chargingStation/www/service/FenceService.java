package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.Fence;
import com.jopool.chargingStation.www.vo.FenceTreeVo;

import java.awt.*;
import java.util.List;

/**
 * Created by gexin on 2017/10/26.
 */
public interface FenceService {

    /**
     * 政府 电子围栏 新增
     *
     * @param fence
     */
    void addGovernmentFence(Fence fence);


    /**
     * 电子围栏 修改
     *
     * @param fence
     */
    void modifyGovernmentFence(Fence fence);


    /**
     * 电子围栏 删除
     *
     * @param fenceId
     */
    void removeGovernmentFence(String fenceId);

    /**
     * 政府 获取 电子围栏
     *
     * @param fenceId
     * @param name
     * @param governmentId
     * @return
     */
    List<Fence> getByGovernmentId(String fenceId, String name, String governmentId);


    /**
     * 电子围栏 树
     *
     * @param fence
     * @return
     */
    List<FenceTreeVo> getByGovernmentIdTree(Fence fence);


    /**
     * 围栏 Id 查找
     *
     * @param fenceId
     * @return
     */
    Fence searchFenceId(String fenceId);

    /**
     * 获取围栏
     *
     * @param carownerId
     * @return
     */
    List<Polygon> getPolygonsByCarownerId(String carownerId);
}
