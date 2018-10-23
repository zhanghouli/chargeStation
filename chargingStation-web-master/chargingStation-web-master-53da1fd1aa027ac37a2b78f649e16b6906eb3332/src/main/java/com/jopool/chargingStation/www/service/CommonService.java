package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.pay.wxpay.response.AccessTokenResp;
import com.jopool.chargingStation.www.base.pay.wxpay.response.TokenResp;
import com.jopool.chargingStation.www.response.OpenAreaResp;
import com.jopool.chargingStation.www.response.RechargePackageResp;
import com.jopool.chargingStation.www.vo.AuthTreeVo;
import com.jopool.chargingStation.www.vo.FeedbackVo;
import com.jopool.chargingStation.www.vo.PassportAuthVo;
import com.jopool.chargingStation.www.vo.SessionUser;
import com.jopool.chargingStation.www.vo.common.SearchFeedBackVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.entity.Sms;
import org.apache.ibatis.session.RowBounds;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service
 * @Author : soupcat
 * @Creation Date : 2017年08月23日 上午11:23
 */
public interface CommonService {

    /**
     * 发送验证码
     *
     * @param type
     * @param sms
     */
    void postMsg(String type, String code, Sms sms);

    /**
     * 获取所有基本权限(选中passportId拥有的权限)
     *
     * @param passportId
     * @return
     */
    Map<PassportAuthVo, List<PassportAuthVo>> getAuthByPassportId(String passportId);


    /**
     * 获取微信token，缓存2小时
     *
     * @param platformId
     * @return
     */
    AccessTokenResp getAccessToken(String code, String platformId);

    /**
     * 获取微信token，缓存2小时
     *
     * @param platformId
     * @return
     */
    TokenResp getAccessToken(String platformId);

    /**
     * 根据手机号获取最近一条短信记录
     *
     * @param phone
     * @return
     */
    CommonTokenAuthAider getTokenAuthAiderByPhone(String phone);

    /**
     * 获取图片 by hostId or Type
     *
     * @param hostId
     * @param type
     * @return
     */
    CommonPicture getPictureByHostId(String hostId, String type);

    /**
     * 修改 图片
     *
     * @param commonPicture
     */
    void modifyCommonPicture(CommonPicture commonPicture);

    /**
     * 新建common图片
     *
     * @param newCommonPic
     * @param avatar
     */
    void addCommonPicture(CommonPicture newCommonPic, String avatar);

    /**
     * 开通城市
     *
     * @return
     */
    List<OpenAreaResp> getOpenArea();

    /**
     * 意见反馈
     *
     * @param content
     * @param pics
     * @return
     */
    Result addFeedback(String passportId, String content, String pics);

    /**
     * 充值套餐
     *
     * @return
     */
    List<RechargePackageResp> getRechargePackages();

    /**
     * 反馈 建议 分页 查询
     *
     * @param searchFeedBackVo
     * @param page
     * @return
     */
    List<FeedbackVo> searchFeedBackVo(SearchFeedBackVo searchFeedBackVo, RowBounds page);

    /**
     * 修改 改变 已阅状态
     *
     * @param feedback
     * @return
     */
    Result modifyFeedBackIsViewed(Feedback feedback);


    /**
     * 删除 反馈 建议
     *
     * @param feedbackId
     * @return
     */
    Result removeFeedBack(String feedbackId);


    /**
     * 单条记录 查询
     *
     * @param feedbackId
     * @return
     */
    Feedback getById(String feedbackId);


    /**
     * 删除 反馈记录
     *
     * @param feedbackId
     */
    void removeFeedbackId(String feedbackId);

    /**
     * 根据名字获取配置信息
     *
     * @param name
     * @return
     */
    String getConfigValueByName(String name, String defaultValue);

    /**
     * add tokenAuth
     *
     * @param commonTokenAuthAider
     */
    void addTokenAuth(CommonTokenAuthAider commonTokenAuthAider);

    /**
     * add 验证码记录
     *
     * @param outsideSnsSendHis
     */
    void addOutsideSnsSendHis(CommonOutsideSnsSendHis outsideSnsSendHis);

    /**
     * 按照 code  查找
     *
     * @param code
     * @return
     */
    CommonArea getByCommonAreaCode(String code);

    /**
     * id 查找 地址
     *
     * @param commonAreaId
     * @return
     */
    CommonArea getByCommonAreaId(String commonAreaId);

    /**
     * 获取 地址
     *
     * @return
     */
    Map<String, Map<String, String>> getArea();

    /**
     * 添加图片
     *
     * @param picture
     */
    void addPic(CommonPicture picture);

    /**
     * 获取url
     *
     * @param id
     * @return
     */
    String getLogoUrl(String id);

    /**
     * 权限 树
     *
     * @param ownerPassportId
     * @param passportId
     * @return
     */
    List<AuthTreeVo> getAuthTreeByPassportId(String ownerPassportId, String passportId);

    /**
     * 删除  auth
     *
     * @param authId
     * @return
     */
    void removePassportAuths(String authId);


    /**
     * 查询 数组 权限
     *
     * @param passportId
     * @return
     */
    List<PassportAuth> searchPassportAuths(String passportId);

    /**
     * 权限 添加
     *
     * @param passportAuth
     */
    void addPassportAuth(PassportAuth passportAuth);

    /**
     * 微信模版 查找
     *
     * @param type
     * @param page
     * @return
     */
    List<MessageTemplate> searchMessageTemplate(String type, RowBounds page);

    /**
     * 模版 添加 或 修改
     *
     * @param messageTemplate
     */
    Result addOrModifyMessageTemplate(MessageTemplate messageTemplate, SessionUser sessionUser);

    /**
     * 模版 id 删除
     *
     * @param id
     */
    void removeMessageTemplate(String id);

    /**
     * 模版 类型 查找
     *
     * @param type
     * @return
     */
    MessageTemplate searchMessageTemplateType(String type);

    /**
     * id  查找
     *
     * @param id
     * @return
     */
    MessageTemplate getMessageId(String id);

    /**
     * 城市 名称  查找 开通城市
     *
     * @param areaId
     * @return
     */
    OpenArea getAreaId(String areaId);


    /**
     * buildOrgPic
     *
     * @param bufferedImage
     * @param fileSize
     * @param moduleName
     * @param fileOriginalName
     * @param fileNewName
     * @param relativeFolder
     * @param hostType
     * @param hostID
     * @param passportType
     * @param passportId
     * @return
     */
    CommonPicture buildOrgPic(BufferedImage bufferedImage, Long fileSize, String moduleName, String fileOriginalName, String fileNewName, String relativeFolder, String hostType, String hostID, String passportType, String passportId);
}
