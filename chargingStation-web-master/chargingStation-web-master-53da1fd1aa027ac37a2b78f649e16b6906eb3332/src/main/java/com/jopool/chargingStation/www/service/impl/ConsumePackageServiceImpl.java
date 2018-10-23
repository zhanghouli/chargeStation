package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.ChargeCostPackage;
import com.jopool.chargingStation.www.base.entity.ConsumePackage;
import com.jopool.chargingStation.www.base.entity.ConsumePackageItem;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.dao.ChargeCostPackageMapper;
import com.jopool.chargingStation.www.dao.ConsumePackageItemMapper;
import com.jopool.chargingStation.www.dao.ConsumePackageMapper;
import com.jopool.chargingStation.www.dao.StationMapper;
import com.jopool.chargingStation.www.response.ConsumePackageItemResp;
import com.jopool.chargingStation.www.response.ConsumePackageResp;
import com.jopool.chargingStation.www.service.ConsumePackageService;
import com.jopool.chargingStation.www.service.OperatorService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.vo.ConsumePackageItemVo;
import com.jopool.chargingStation.www.vo.ConsumePackageVo;
import com.jopool.chargingStation.www.vo.common.SearchConsumePackageItemVo;
import com.jopool.chargingStation.www.vo.common.SearchConsumePackageVo;
import com.jopool.chargingStation.www.vo.common.SearchRechargePackageVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by synn on 2017/8/28.
 */
@Service
public class ConsumePackageServiceImpl extends BaseServiceImpl implements ConsumePackageService {

    @Resource
    private ConsumePackageItemMapper consumePackageItemMapper;
    @Resource
    private ConsumePackageMapper     consumePackageMapper;
    @Resource
    private ChargeCostPackageMapper  chargeCostPackageMapper;
    @Resource
    private StationMapper            stationMapper;
    @Resource
    private OperatorService          operatorService;
    @Resource
    private PassportService          passportService;

    @Override
    public void addConsumePackage(ConsumePackage consumePackage) {
        consumePackageMapper.insert(consumePackage);
    }

    @Override
    public ConsumePackage searchConsumePackageById(String consumePackageId) {
        return consumePackageMapper.selectByPrimaryKey(consumePackageId);
    }

    @Override
    public Result modifyConsumePackage(ConsumePackage consumePackage) {
        consumePackageMapper.updateByPrimaryKeySelective(consumePackage);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result modifyStatus(String consumePackageId) {
        ConsumePackage consumePackage = consumePackageMapper.selectByPrimaryKey(consumePackageId);
        if (consumePackage == null) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        if (consumePackage.getIsEnabled()) {
            consumePackage.setIsEnabled(false);
            List<ConsumePackageItem> consumePackageItems = consumePackageItemMapper.selectByConsumePackageId(consumePackageId);
            if (consumePackageItems.size() > 0) {
                for (ConsumePackageItem consumePackageItem : consumePackageItems) {
                    consumePackageItem.setIsEnabled(false);
                    consumePackageItemMapper.updateByPrimaryKeySelective(consumePackageItem);
                }
            }
        } else {
            consumePackage.setIsEnabled(true);
        }
        consumePackageMapper.updateByPrimaryKeySelective(consumePackage);

        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removeConsumePackageById(String consumePackageId) {
        consumePackageMapper.deleteByPrimaryKey(consumePackageId);
        consumePackageItemMapper.deletedByConsumePackageId(consumePackageId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public List<ConsumePackageVo> searchConsumePackageVo(SearchConsumePackageVo searchConsumePackageVo, RowBounds page) {

        List<ConsumePackageVo> consumePackages = consumePackageMapper.selectConsumePackageVo(searchConsumePackageVo, page);
//        for (ConsumePackage consumePackage : consumePackages) {
//            ConsumePackageVo consumePackageVo = new ConsumePackageVo(consumePackage);
//            Operator operator = operatorService.searchById(consumePackage.getOperatorId());
//            //空指针判断
//            if (operator == null) {
//                consumePackageVos.add(consumePackageVo);
//            } else {
//                Passport passport = passportService.getById(operator.getPassportId());
//                if (passport != null) {
//                    consumePackageVo.setRealName(passport.getRealName());
//                }
//                consumePackageVos.add(consumePackageVo);
//            }
//        }
        return consumePackages;
    }

    @Override
    public void addConsumePackageItem(ConsumePackageItem consumePackageItem) {
        consumePackageItemMapper.insert(consumePackageItem);
    }

    @Override
    public ConsumePackageItem getById(String consumePackageItemId) {
        return consumePackageItemMapper.selectByPrimaryKey(consumePackageItemId);
    }

    @Override
    public Result modifyConsumePackageItem(ConsumePackageItem consumePackageItem) {
        consumePackageItemMapper.updateByPrimaryKey(consumePackageItem);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result modifyStatusConsumePackageItem(String consumePackageItemId) {
        ConsumePackageItem consumePackageItem = consumePackageItemMapper.selectByPrimaryKey(consumePackageItemId);
        if (consumePackageItem == null) {
            return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
        }
        if (consumePackageItem.getIsEnabled()) {
            consumePackageItem.setIsEnabled(false);
        } else {
            consumePackageItem.setIsEnabled(true);
        }
        consumePackageItemMapper.updateByPrimaryKey(consumePackageItem);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removeConsumePackageItemId(String consumePackageItemId) {
        consumePackageItemMapper.deleteByPrimaryKey(consumePackageItemId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public List<ConsumePackageItemVo> searchConsumePackageItemVo(String consumePackageId, SearchConsumePackageItemVo searchConsumePackageItemVo, RowBounds page) {
        List<ConsumePackageItemVo> consumePackageItemVos = new ArrayList<ConsumePackageItemVo>();
        List<ConsumePackageItem> consumePackageItems = consumePackageItemMapper.selectConsumePackageItemVo(consumePackageId, searchConsumePackageItemVo, page);
        for (ConsumePackageItem consumePackageItem : consumePackageItems) {
            ConsumePackageItemVo consumePackageItemVo1 = new ConsumePackageItemVo(consumePackageItem);
            ConsumePackage consumePackage = consumePackageMapper.selectByPrimaryKey(consumePackageItem.getConsumePackageId());
            if (consumePackage != null) {
                consumePackageItemVo1.setConsumeName(consumePackage.getName());
                consumePackageItemVos.add(consumePackageItemVo1);
            }
        }
        return consumePackageItemVos;
    }

    @Override
    public List<ConsumePackage> searchOperatorId(String operatorId) {
        return consumePackageMapper.selectOperatorId(operatorId);
    }

    @Override
    public List<ConsumePackageItem> getItemsByConsumePackageId(String consumePackageId) {
        return consumePackageItemMapper.selectByConsumePackageId(consumePackageId);
    }

    @Override
    public ConsumePackageResp getConsumePackageDetail(String consumePackageId) {
        ConsumePackageResp resp = new ConsumePackageResp();
        ConsumePackage consumePackage = consumePackageMapper.selectByPrimaryKey(consumePackageId);
        resp.setId(consumePackage.getId());
        resp.setName(consumePackage.getName());
        List<ConsumePackageItemResp> consumePackageItemResps = new ArrayList<ConsumePackageItemResp>();
        List<ConsumePackageItem> consumePackageItems = consumePackageItemMapper.selectByConsumePackageId(consumePackageId);
        for (ConsumePackageItem consumePackageItem : consumePackageItems) {
            ConsumePackageItemResp consumePackageResp = new ConsumePackageItemResp(consumePackageItem);
            consumePackageItemResps.add(consumePackageResp);
        }
        resp.setConsumePackageItems(consumePackageItemResps);
        return resp;
    }

    @Override
    public List<ConsumePackage> getConsumePackageList(String operatorId) {
        List<ConsumePackage> consumePackageList = consumePackageMapper.selectOperatorId(operatorId);
        return consumePackageList;
    }

    @Override
    public List<ConsumePackage> listOperatorIdByConsumePackage(String operatorId, SearchConsumePackageVo searchConsumePackageVo, RowBounds page) {
        return consumePackageMapper.selectByOperatorId(operatorId, searchConsumePackageVo, page);
    }

    @Override
    public List<ChargeCostPackage> searchChargeCostPackage(SearchRechargePackageVo searchRechargePackageVo, RowBounds page) {
        return chargeCostPackageMapper.search(searchRechargePackageVo, page);
    }

    @Override
    public ChargeCostPackage getChargeCostPackageById(String id) {
        return chargeCostPackageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void modifyChargeCostPackage(ChargeCostPackage chargeCostPackage) {
        chargeCostPackageMapper.updateByPrimaryKeySelective(chargeCostPackage);
    }

    @Override
    public void addChargeCostPackage(ChargeCostPackage chargeCostPackage) {
        chargeCostPackageMapper.insertSelective(chargeCostPackage);
    }

    @Override
    public Result removeChargeCostPackage(String chargeCostPackageId) {
        chargeCostPackageMapper.deleteByPrimaryKey(chargeCostPackageId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public List<ChargeCostPackage> getChargeCostPackage() {
        return chargeCostPackageMapper.selectChargeCostPackage();
    }

    @Override
    public ChargeCostPackage getChargeCostPackageByPow(String power) {
        return chargeCostPackageMapper.selectByPow(power);
    }

    @Override
    public int getStationUserPackage(String chargeCostPackageId) {
        return stationMapper.selectStationByPackage(chargeCostPackageId);
    }

}
