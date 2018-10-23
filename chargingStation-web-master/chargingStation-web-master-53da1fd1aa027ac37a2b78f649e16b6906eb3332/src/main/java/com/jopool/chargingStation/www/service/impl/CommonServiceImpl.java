package com.jopool.chargingStation.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.pay.common.PlatformEnum;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXUrlConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.request.AccessTokenReq;
import com.jopool.chargingStation.www.base.pay.wxpay.request.TokenReq;
import com.jopool.chargingStation.www.base.pay.wxpay.response.AccessTokenResp;
import com.jopool.chargingStation.www.base.pay.wxpay.response.TokenResp;
import com.jopool.chargingStation.www.base.utils.SmsCMCCUtils;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.constants.DescriptionMessage;
import com.jopool.chargingStation.www.dao.*;
import com.jopool.chargingStation.www.enums.CommonPictureTypeEnum;
import com.jopool.chargingStation.www.enums.MessageTemplateEnum;
import com.jopool.chargingStation.www.response.OpenAreaResp;
import com.jopool.chargingStation.www.response.RechargePackageResp;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.PayWxService;
import com.jopool.chargingStation.www.service.SystemConfigService;
import com.jopool.chargingStation.www.vo.AuthTreeVo;
import com.jopool.chargingStation.www.vo.FeedbackVo;
import com.jopool.chargingStation.www.vo.PassportAuthVo;
import com.jopool.chargingStation.www.vo.SessionUser;
import com.jopool.chargingStation.www.vo.common.SearchFeedBackVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.entity.Sms;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.HttpUtils;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.URLUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service.impl
 * @Author : soupcat
 * @Creation Date : 2017年08月23日 下午1:41
 */
@Service
public class CommonServiceImpl extends BaseServiceImpl implements CommonService, SelfBeanAware<CommonService> {
    private CommonService                 selfService;
    @Resource
    private PassportService               passportService;
    @Resource
    private PayWxService                  payWxService;
    @Resource
    private SystemConfigService           systemConfigService;
    @Resource
    private CommonTokenAuthAiderMapper    commonTokenAuthAiderMapper;
    @Resource
    private CommonOutsideSnsSendHisMapper commonOutsideSnsSendHisMapper;
    @Resource
    private CommonPictureMapper           commonPictureMapper;
    @Resource
    private OpenAreaMapper                openAreaMapper;
    @Resource
    private CommonBaseAuthMapper          commonBaseAuthMapper;
    @Resource
    private PassportAuthMapper            passportAuthMapper;
    @Resource
    private CommonAreaMapper              commonAreaMapper;
    @Resource
    private FeedbackMapper                feedbackMapper;
    @Resource
    private RechargePackageMapper         rechargePackageMapper;
    @Resource
    private MessageTemplateMapper         messageTemplateMapper;

    @Override
    public void postMsg(String type, String code, Sms sms) {
        boolean isSuccess = SmsCMCCUtils.postMsg(sms);
    }


    @Override
    public AccessTokenResp getAccessToken(String code, String platformId) {
        String key = Constants.CACHE_KEY_ACCESS_TOKEN_BY_CODE + code + platformId;
        WxConfig wxConfig = payWxService.getByShopIdPlatform(platformId, PlatformEnum.GZH.getValue());
        if (null == wxConfig) {
            return null;
        }
        AccessTokenResp accessToken = (AccessTokenResp) cacheBean.get(key);
        if (null == accessToken) {
            //访问access_token获取access_token/openid
            AccessTokenReq accessTokenReq = new AccessTokenReq(code, wxConfig);
            JSON accessTokenRespJson = HttpUtils.getJsonByGet(URLUtils.addQuery(WXUrlConfig.WX_ACCESS_TOKEN_URL, accessTokenReq));
            accessToken = JSON.toJavaObject(accessTokenRespJson, AccessTokenResp.class);
            cacheBean.put(key, accessToken, Constants.ACCESS_TOKEN_CACHE_TIME, TimeUnit.MINUTES);
        }
        return accessToken;
    }

    @Override
    public TokenResp getAccessToken(String platformId) {
        String key = Constants.CACHE_KEY_ACCESS_TOKEN + platformId;
        WxConfig wxConfig = payWxService.getByShopIdPlatform(platformId, PlatformEnum.GZH.getValue());
        if (null == wxConfig) {
            return null;
        }
        TokenResp tokenResp = (TokenResp) cacheBean.get(key);
        if (null == tokenResp || StringUtils.isEmpty(tokenResp.getAccess_token())) {
            TokenReq tokenReq = new TokenReq(wxConfig.getPayAppid(), wxConfig.getPaySecret());
            JSON tokenRespJson = HttpUtils.getJsonByGet(URLUtils.addQuery(WXUrlConfig.WX_TOKEN_URL, tokenReq));
            tokenResp = JSON.toJavaObject(tokenRespJson, TokenResp.class);
            cacheBean.put(key, tokenResp, Constants.ACCESS_TOKEN_CACHE_TIME, TimeUnit.MINUTES);
        }
        return tokenResp;
    }

    @Override
    public CommonTokenAuthAider getTokenAuthAiderByPhone(String phone) {
        return commonTokenAuthAiderMapper.selectLastByPhone(phone);
    }

    @Override
    public Map<PassportAuthVo, List<PassportAuthVo>> getAuthByPassportId(String passportId) {
        Map<PassportAuthVo, List<PassportAuthVo>> authVoListMap = new LinkedHashMap<PassportAuthVo, List<PassportAuthVo>>();
        //查找passport信息
        Passport passport = passportService.getById(passportId);
        //Id信息判断
        if (passport == null) {
            return authVoListMap;
        }
        //获得所有权限路径
        Map<CommonBaseAuth, List<CommonBaseAuth>> commonBaseAuthListMap = getBaseAuthByType(passport.getType());
        //用户拥有权限
        List<PassportAuth> passportAuthIdList = passportAuthMapper.selectByPassportId(passportId);
        List<String> passportAuthList = new ArrayList<String>();
        for (PassportAuth passportAuth : passportAuthIdList) {
            passportAuthList.add(passportAuth.getAuthId());
        }
        for (CommonBaseAuth commonBaseAuth : commonBaseAuthListMap.keySet()) {
            PassportAuthVo passportAuthVo = new PassportAuthVo();
            passportAuthVo.setId(commonBaseAuth.getId());
            //名字
            passportAuthVo.setName(commonBaseAuth.getName());
            //url
            passportAuthVo.setUrl(commonBaseAuth.getType());

            List<CommonBaseAuth> childrenBaseAuth = commonBaseAuthListMap.get(commonBaseAuth);
            List<PassportAuthVo> childrenList = new ArrayList<PassportAuthVo>();
            for (CommonBaseAuth children : childrenBaseAuth) {
                PassportAuthVo passportAuthVoChildren = new PassportAuthVo();
                passportAuthVoChildren.setId(children.getId());
                passportAuthVoChildren.setUrl(children.getType());
                passportAuthVoChildren.setName(children.getName());
                if ("个人中心".equals(commonBaseAuth.getName())) {
                    passportAuthVoChildren.setHasAuth(true);
                    passportAuthVo.setHasAuth(true);
                } else {
                    if (passportAuthList.contains(children.getId())) {
                        passportAuthVoChildren.setHasAuth(true);
                        passportAuthVo.setHasAuth(true);
                    }
                }
                childrenList.add(passportAuthVoChildren);
            }
            authVoListMap.put(passportAuthVo, childrenList);
        }
        return authVoListMap;
    }

    @Override
    public CommonPicture getPictureByHostId(String hostId, String type) {
        return commonPictureMapper.selectByHostIdOrType(hostId, type);
    }

    @Override
    public void modifyCommonPicture(CommonPicture commonPicture) {
        commonPictureMapper.updateByPrimaryKeySelective(commonPicture);
    }

    @Override
    public void addCommonPicture(CommonPicture commonPic, String avatar) {
        //TODO 图片添加
        commonPic.setId(UUIDUtils.createId());
        commonPic.setCreationTime(new Date());
        commonPic.setHostType(avatar);
        commonPic.setDescription(DescriptionMessage.PASSPORT_AVATAR);
        commonPic.setIsDeleted(false);
        commonPic.setName(StringUtils.isEmpty(commonPic.getName()) ? "" : commonPic.getName());
        commonPictureMapper.insertSelective(commonPic);
    }

    @Override
    public List<OpenAreaResp> getOpenArea() {
        List<OpenAreaResp> resps = new ArrayList<OpenAreaResp>();
        List<OpenArea> areas = openAreaMapper.selectAll();
        for (OpenArea area : areas) {
            OpenAreaResp resp = new OpenAreaResp(area);
            CommonArea commonArea = commonAreaMapper.selectByPrimaryKey(area.getAreaId());
            if (null != commonArea) {
                resp.setName(commonArea.getName());
                resp.setLocation(commonArea.getLocation());
            }
            resps.add(resp);
        }
        return resps;
    }

    @Override
    public Result addFeedback(String passportId, String content, String pics) {
        Passport passport = passportService.getById(passportId);
        if (passport == null) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        Feedback feedback = new Feedback();
        feedback.setId(UUIDUtils.createId());
        feedback.setPassportId(passportId);
        feedback.setType(passport.getType());
        feedback.setContent(content);
        feedback.setPics(pics);
        feedback.setIsViewed(false);
        feedback.setIsDeleted(false);
        feedback.setCreator(passportId);
        feedback.setCreationTime(new Date());
        feedbackMapper.insert(feedback);
        return new Result(Code.SUCCESS);
    }

    @Override
    public List<RechargePackageResp> getRechargePackages() {
        List<RechargePackageResp> resps = new ArrayList<RechargePackageResp>();
        List<RechargePackage> rechargePackages = rechargePackageMapper.selectAll();
        for (RechargePackage rechargePackage : rechargePackages) {
            RechargePackageResp resp = new RechargePackageResp(rechargePackage);
            resps.add(resp);
        }
        return resps;
    }

    @Override
    public List<FeedbackVo> searchFeedBackVo(SearchFeedBackVo searchFeedBackVo, RowBounds page) {
        List<FeedbackVo> feedbackVos = new ArrayList<FeedbackVo>();
        List<FeedbackVo> feedbacks = feedbackMapper.selectSearchFeedBackVo(searchFeedBackVo, page);
        for (FeedbackVo feedback : feedbacks) {
            FeedbackVo feedbackVo = new FeedbackVo(feedback);
            if (!StringUtils.isEmpty(feedback.getPics())) {
                String[] pics = feedback.getPics().split(",");
                feedbackVo.setPicList(Arrays.asList(pics));
            }
            feedbackVo.setPassportName(feedback.getPassportName());
            feedbackVos.add(feedbackVo);
        }
        return feedbackVos;
    }

    @Override
    public Result modifyFeedBackIsViewed(Feedback feedback) {
        feedbackMapper.updateByPrimaryKeySelective(feedback);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removeFeedBack(String feedbackId) {
        feedbackMapper.deleteByPrimaryKey(feedbackId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Feedback getById(String feedbackId) {
        Feedback feedback = feedbackMapper.selectByPrimaryKey(feedbackId);
        if (feedback != null) {
            feedback.setIsViewed(true);
        }
        feedbackMapper.updateByPrimaryKeySelective(feedback);
        return feedback;
    }

    @Override
    public void removeFeedbackId(String feedbackId) {
        feedbackMapper.deleteByPrimaryKey(feedbackId);
    }

    @Override
    public String getConfigValueByName(String name, String defaultValue) {
        CommonSystemConfig systemConfig = systemConfigService.getConfigByName(name);
        if (null == systemConfig) {
            return defaultValue;
        }
        return systemConfig.getVal();
    }

    @Override
    public void addTokenAuth(CommonTokenAuthAider commonTokenAuthAider) {
        commonTokenAuthAiderMapper.insertSelective(commonTokenAuthAider);
    }

    @Override
    public void addOutsideSnsSendHis(CommonOutsideSnsSendHis outsideSnsSendHis) {
        commonOutsideSnsSendHisMapper.insertSelective(outsideSnsSendHis);
    }

    @Override
    public CommonArea getByCommonAreaCode(String code) {
        return commonAreaMapper.selectByCommonAreaCode(code);
    }

    @Override
    public CommonArea getByCommonAreaId(String commonAreaId) {
        return commonAreaMapper.selectByPrimaryKey(commonAreaId);
    }

    @Override
    public Map<String, Map<String, String>> getArea() {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        //获取所有
        List<CommonArea> commonAreas = commonAreaMapper.selectAreaAll();
        //遍历
        for (CommonArea commonArea : commonAreas) {
            //省级
            if (commonArea.getLocation().endsWith(",0,0")) {
                //key 对应的是  Map<String,Sting>
                Map<String, String> oneLevelMap = map.get("0");
                if (oneLevelMap == null) {
                    oneLevelMap = new HashMap<String, String>();
                    map.put("0", oneLevelMap);
                }
                oneLevelMap.put(commonArea.getCode(), commonArea.getName());
            } else if (commonArea.getLocation().endsWith(",0")) {
                //市
                //取到 省级code
                String oneLevelCode = commonArea.getLocation().split(",")[0];
                Map<String, String> twoLevelMap = map.get("0," + oneLevelCode);
                if (twoLevelMap == null) {
                    twoLevelMap = new HashMap<String, String>();
                    map.put("0," + oneLevelCode, twoLevelMap);
                }
                twoLevelMap.put(commonArea.getCode(), commonArea.getName());
            } else {
                //县级
                String oneLevelCode = commonArea.getLocation().split(",")[0];
                String twoLevelCode = commonArea.getLocation().split(",")[1];
                Map<String, String> threeLevel = map.get("0," + oneLevelCode + "," + twoLevelCode);
                if (threeLevel == null) {
                    threeLevel = new HashMap<String, String>();
                    map.put("0," + oneLevelCode + "," + twoLevelCode, threeLevel);
                }
                threeLevel.put(commonArea.getCode(), commonArea.getName());
            }
        }
        return map;
    }

    @Override
    public void addPic(CommonPicture picture) {
        commonPictureMapper.insertSelective(picture);
    }

    @Override
    public String getLogoUrl(String logoId) {
        String logoUrl = "";
        if (null == logoId) {
            return logoUrl;
        }
        CommonPicture commonPicture = commonPictureMapper.selectByPrimaryKey(logoId);
        if (null != commonPicture) {
            logoUrl = commonPicture.getRelativeFolder().endsWith("/") ? commonPicture.getRelativeFolder() + commonPicture.getName() : commonPicture.getRelativeFolder() + "/" + commonPicture.getName();
        }
        return logoUrl;
    }

    @Override
    public List<AuthTreeVo> getAuthTreeByPassportId(String ownerPassportId, String passportId) {
        List<AuthTreeVo> tree = new ArrayList<AuthTreeVo>();
        //登录人账号判断
        Passport ownerPasspor = passportService.getById(ownerPassportId);
        if (ownerPasspor == null) {
            return tree;
        }
        //被查看权限账号判断
        Passport passport = passportService.getById(passportId);
        if (passport == null) {
            return tree;
        }
        //获取所有权限树，父为key  子为value
        Map<CommonBaseAuth, List<CommonBaseAuth>> map = getBaseAuthByType(passport.getType());
        //账号关联的 登录账号 所有 权限
        List<PassportAuth> passportAuthList = passportAuthMapper.selectByPassportId(ownerPassportId);
        List<String> ownerPassportAuthList = new ArrayList<String>();
        for (PassportAuth passportAuth : passportAuthList) {
            ownerPassportAuthList.add(passportAuth.getAuthId());
        }
        //被授权账号
        List<PassportAuth> passportAuth = passportAuthMapper.selectByPassportId(passportId);
        //关联 的id 数组
        List<String> passportAuths = new ArrayList<String>();
        for (PassportAuth passportAuth1 : passportAuth) {
            passportAuths.add(passportAuth1.getAuthId());
        }
        for (CommonBaseAuth commonBaseAuth : map.keySet()) {
            if ("个人中心".equals(commonBaseAuth.getName())) {
                continue;
            }
            AuthTreeVo authTreeVo = new AuthTreeVo();
            authTreeVo.setId(commonBaseAuth.getId());
            authTreeVo.setText(commonBaseAuth.getName());
            Map<String, Object> topState = new HashedMap();
            topState.put("opened", true);
            authTreeVo.setState(topState);
            List<CommonBaseAuth> commonBaseAuths = map.get(commonBaseAuth);
            List<AuthTreeVo> childTreeVo = new ArrayList<AuthTreeVo>();
            for (CommonBaseAuth commonBaseAuth1 : commonBaseAuths) {
                AuthTreeVo authTreeVo1 = new AuthTreeVo();
                authTreeVo1.setId(commonBaseAuth1.getId());
                authTreeVo1.setText(commonBaseAuth1.getName());
                if (passportAuths.contains(commonBaseAuth1.getId())) {
                    Map<String, Object> childState = new HashedMap();
                    childState.put("selected", true);
                    authTreeVo1.setState(childState);
                }
                childTreeVo.add(authTreeVo1);
            }
            authTreeVo.setChildren(childTreeVo);
            tree.add(authTreeVo);
        }
        return tree;
    }

    @Override
    public void removePassportAuths(String authId) {
        passportAuthMapper.deleteByPrimaryKey(authId);
    }

    @Override
    public List<PassportAuth> searchPassportAuths(String passportId) {
        return passportAuthMapper.selectByPassportId(passportId);
    }

    @Override
    public void addPassportAuth(PassportAuth passportAuth) {
        passportAuthMapper.insertSelective(passportAuth);
    }

    @Override
    public List<MessageTemplate> searchMessageTemplate(String type, RowBounds page) {
        List<MessageTemplate> messageTemplateList = messageTemplateMapper.selectMessageTemplate(type, page);
        List<MessageTemplate> messageTemplates = new ArrayList<>();
        for (MessageTemplate template : messageTemplateList) {
            template.setType(MessageTemplateEnum.getEnumName(template.getType()));
            MessageTemplate messageTemplate = new MessageTemplate(template);
            messageTemplates.add(messageTemplate);
        }
        return messageTemplates;
    }

    @Override
    public Result addOrModifyMessageTemplate(MessageTemplate messageTemplate, SessionUser sessionUser) {
        if (!StringUtils.isEmpty(messageTemplate.getId())) {
            MessageTemplate templateOld = messageTemplateMapper.selectByPrimaryKey(messageTemplate.getId());
            if (templateOld == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            MessageTemplate templateType = messageTemplateMapper.selectMessageType(messageTemplate.getType());
            if (templateType != null && !templateOld.getType().equals(templateType.getType())) {
                return new Result(Code.ERROR, "修改的模版已存在");
            }
            templateOld.setType(messageTemplate.getType());
            templateOld.setTemplateId(messageTemplate.getTemplateId());
            templateOld.setModifier(sessionUser.getPassportId());
            templateOld.setModifyTime(new Date());
            messageTemplateMapper.updateByPrimaryKeySelective(templateOld);
        } else {
            MessageTemplate templateNew = new MessageTemplate();
            MessageTemplate templateType = messageTemplateMapper.selectMessageType(messageTemplate.getType());
            if (templateType != null) {
                return new Result(Code.ERROR, "此模版已存在");
            }
            templateNew.setId(UUIDUtils.createId());
            templateNew.setTemplateId(messageTemplate.getTemplateId());
            templateNew.setType(messageTemplate.getType());
            templateNew.setCreator(sessionUser.getPassportId());
            templateNew.setCreationTime(new Date());
            templateNew.setIsDeleted(false);
            messageTemplateMapper.insert(templateNew);
        }
        return new Result(Code.SUCCESS);
    }

    @Override
    public void removeMessageTemplate(String id) {
        messageTemplateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public MessageTemplate searchMessageTemplateType(String type) {
        return messageTemplateMapper.selectMessageType(type);
    }

    @Override
    public MessageTemplate getMessageId(String id) {
        return messageTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public OpenArea getAreaId(String areaId) {
        return openAreaMapper.selectAreaId(areaId);
    }

    @Override
    public CommonPicture buildOrgPic(BufferedImage bufferedImage, Long fileSize, String moduleName, String fileOriginalName, String fileNewName, String relativeFolder, String hostType, String hostID, String passportType, String passportId) {
        CommonPicture pic = null;
        CommonPicture avatar = selfService.getPictureByHostId(passportId, CommonPictureTypeEnum.AVATAR.getValue());
        if (CommonPictureTypeEnum.AVATAR.getValue().equals(hostType) && null != avatar) {
            avatar.setHeight(bufferedImage.getHeight());
            avatar.setWidth(bufferedImage.getWidth());
            avatar.setOrgName(fileOriginalName);
            avatar.setHostType(hostType);
            avatar.setHostId(hostID);
            avatar.setSize(Integer.parseInt(fileSize.toString()));
            avatar.setName(fileNewName);
            avatar.setRelativeFolder(relativeFolder);
            avatar.setRemoteRelativeUrl(avatar.getRelativeFolder() + "/" + fileNewName);
            avatar.setCreationTime(new Date());
            avatar.setCreator(passportId);
            selfService.modifyCommonPicture(avatar);
            pic = avatar;
        } else {
            CommonPicture picture = new CommonPicture();
            picture.setId(UUIDUtils.createId());
            picture.setHeight(bufferedImage.getHeight());
            picture.setWidth(bufferedImage.getWidth());
            picture.setOrgName(fileOriginalName);
            picture.setHostType(hostType);
            picture.setHostId(hostID);
            picture.setSize(Integer.parseInt(fileSize.toString()));
            picture.setName(fileNewName);
            picture.setIsDeleted(false);
            picture.setDescription(CommonPictureTypeEnum.AVATAR.getValue().equals(hostType) ? "用户头像" : "用户反馈");
            picture.setCreator(passportId);
            picture.setRelativeFolder(relativeFolder);
            picture.setRemoteRelativeUrl(picture.getRelativeFolder() + "/" + fileNewName);
            selfService.addPic(picture);
            pic = picture;
        }
        return pic;
    }

    /**
     * 用户type 获取所有信息
     *
     * @param passportType
     * @return
     */
    public Map<CommonBaseAuth, List<CommonBaseAuth>> getBaseAuthByType(String passportType) {
        Map<CommonBaseAuth, List<CommonBaseAuth>> baseAuthListMap = new LinkedHashMap<CommonBaseAuth, List<CommonBaseAuth>>();
        //common_base_auth 表中  按照type查找 升序
        List<CommonBaseAuth> commonBaseAuths = commonBaseAuthMapper.selectPassportType(passportType);
        //遍历对象
        for (CommonBaseAuth commonBaseAuth : commonBaseAuths) {
            //对象Id查找
            List<CommonBaseAuth> list = commonBaseAuthMapper.selectParentId(commonBaseAuth.getId());

            baseAuthListMap.put(commonBaseAuth, list);
        }
        return baseAuthListMap;
    }

    @Override
    public void setSelfBean(CommonService object) {
        this.selfService = object;
    }
}
