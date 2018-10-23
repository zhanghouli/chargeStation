package com.jopool.chargingStation.www.controller;

import com.jopool.chargingStation.www.apppush.AppPushService;
import com.jopool.chargingStation.www.base.entity.CommonPicture;
import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.chargingStation.www.base.utils.FileUtil;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.vo.SessionUser;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.jweb.cache.Cache;
import com.jopool.jweb.controller.JWebController;
import com.jopool.jweb.enums.ModeEnum;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.utils.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gexin on 15/3/20.
 * 基础Controller
 */
public class BaseController extends JWebController<SessionUser> {

    //原图片
    protected static final String        IMG_TYPE_ORIGINAL   = "org";
    protected static       String        localUploadRootPath = ApplicationConfigHelper.getFilePath();
    protected              AtomicInteger suffix              = new AtomicInteger(0);
    @Resource
    protected Cache          cacheBean;
    @Resource
    protected CommonService  commonService;
    @Resource
    protected AppPushService appPushService;

    /**
     * 验证短信验证码
     *
     * @param phone
     * @param smsCode
     * @return
     */
    protected boolean checkSmsCode(String phone, String smsCode, String type) {
        String key = Constants.CACHE_KEY_SMS_CODE + type + phone;
        String code = (String) cacheBean.get(key);
        if (!StringUtils.isEmpty(code) && code.equals(smsCode)) {
            cacheBean.remove(key);
            return true;
        }
        return false;
    }

    /**
     * 简单json输入，复杂的通过resp包下的消息
     *
     * @param key
     * @param value
     * @return
     */
    protected Map<String, Object> createJsonMap(String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);
        return map;
    }

    /**
     * 时间常数
     *
     * @param timeStartStr
     * @param timeEndStr
     * @return
     */
    protected DateParam getDateParam(String timeStartStr, String timeEndStr) {
        DateParam dateParam = new DateParam();
        if (!StringUtils.isEmpty(timeStartStr)) {
            dateParam.setTimeStart(DateUtils.getStartDate(DateUtils.string2Date(timeStartStr, "yyyy-MM-dd")));
        }
        if (!StringUtils.isEmpty(timeEndStr)) {
            dateParam.setTimeEnd(DateUtils.getEndDate(DateUtils.string2Date(timeEndStr, "yyyy-MM-dd")));
        }
        return dateParam;
    }

    /**
     * 默认时间为前5分钟数据
     *
     * @return
     */
    protected DateParam getDefaultDateParam() {
        DateParam dateParam = new DateParam();
        dateParam.setTimeStart(DateUtils.addMinute(new Date(), -5));
        dateParam.setTimeEnd(new Date());
        return dateParam;
    }

    @Override
    protected SessionUser getSessionUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (SessionUser) request.getSession().getAttribute(Constants.SESSION_KEY_LOGIN_USER);
    }

    @Override
    protected String getPagination(String url, Pagination page) {
        return PaginationHelper.pagination(url, page);
    }

    /**
     * download
     */
    protected void download(String path, HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        String saveDir = ApplicationConfigHelper.getFilePath();
        if (ModeEnum.DEVELOP == ApplicationConfigHelper.getMode()) {
            saveDir = request.getSession().getServletContext().getRealPath(ApplicationConfigHelper.getFilePath());
        }
        path = saveDir + "/" + path;
        try {
            response.setHeader("Content-disposition", "attachment; filename=file.pdf");
            response.setContentType("application/octet-stream; charset=utf-8");
            outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(new File(path)));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    /**
//     * export jxls
//     */
//    protected void exportJxls(String templatePath, String fileName, Map beans, HttpServletResponse response) {
//        ServletOutputStream outputStream = null;
//        try {
//            fileName = new String(fileName.getBytes(), "ISO-8859-1");
//            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
//            response.setContentType("application/octet-stream; charset=utf-8");
//            XLSTransformer transformer = new XLSTransformer();
//            Workbook workbook = transformer.transformXLS(PassportController.class.getResourceAsStream(templatePath), beans);
//            outputStream = response.getOutputStream();
//            workbook.write(outputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != outputStream) {
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    protected CommonPicture uploadWxImage(HttpServletRequest request, String headimgurl, Passport passport) {
        if (ModeEnum.DEVELOP == ApplicationConfigHelper.getMode()) {
            localUploadRootPath = request.getSession().getServletContext().getRealPath(ApplicationConfigHelper.getFilePath());
        }
        //生成随机数
        String fileName = UUIDUtils.generateShortUuid();
        //文件名字创建
        //本地路径
        String absoluteOrigPicParentPath = getAbsoluteFolderPath(passport.getType(), passport.getId(), "avatar", IMG_TYPE_ORIGINAL);
        if (!new File(absoluteOrigPicParentPath).exists()) {
            new File(absoluteOrigPicParentPath).mkdirs();
        }
        //文件名字创建
        String fileNewName = genPicName("png", fileName);
        HttpURLConnection connection = null;
        CommonPicture commonPicture = null;
        try {
            URL url = new URL(headimgurl);
            connection = (HttpURLConnection) url.openConnection();
            //读取 路径下
            File newFile = new File(absoluteOrigPicParentPath + "/" + fileNewName);
            FileUtil.saveFile(connection.getInputStream(), absoluteOrigPicParentPath, fileNewName);
            commonPicture = commonService.buildOrgPic(ImageIO.read(new FileInputStream(newFile)), 0L, "avatar", fileName, fileNewName, "/" + getRelativeFolderPath(passport.getType(), passport.getId(), "avatar", IMG_TYPE_ORIGINAL), "avatar", passport.getId(), passport.getType(), passport.getId());
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return commonPicture;
    }


    /**
     * 生成相对路径并返回
     *
     * @param moduleName
     * @param sizeType
     * @return
     */
    protected String getAbsoluteFolderPath(String passportType, String passportId, String moduleName, String sizeType) {
        return localUploadRootPath.endsWith("/") ?
                new StringBuffer(localUploadRootPath).append(getRelativeFolderPath(passportType, passportId, moduleName, sizeType)).toString()
                : new StringBuffer(localUploadRootPath).append("/").append(getRelativeFolderPath(passportType, passportId, moduleName, sizeType)).toString();
    }

    /**
     * 生成相对路径并返回
     * /upload/image/passportType/passportID/moduleName/original/hostType_missec_count.extName
     *
     * @param moduleName
     * @param sizeType
     * @return
     */
    protected String getRelativeFolderPath(String passportType, String passportId, String moduleName, String sizeType) {
        return new StringBuffer("upload/image/").append(passportType).append("/").append(passportId).append("/").append(moduleName).append("/").append(sizeType).toString();
    }

    /**
     * 支付配置文件路径
     *
     * @param payType    类型 veryfy || cert
     * @param passportId 管理员ID
     * @return
     */
    protected String getAbsoluteFolderPayPath(String payType, String passportId) {
        return localUploadRootPath.endsWith("/") ?
                new StringBuffer(localUploadRootPath).append(getRelativeFolderPayPath(payType, passportId)).toString()
                : new StringBuffer(localUploadRootPath).append("/").append(getRelativeFolderPayPath(payType, passportId)).toString();
    }

    /**
     * 支付文件
     *
     * @param payType    类型
     * @param passportId 管理员ID
     * @return
     */
    protected String getRelativeFolderPayPath(String payType, String passportId) {
        return new StringBuffer("upload/").append(payType).append("/").append(passportId).append("/").toString();
    }

    /**
     * @param fileExtName
     * @param prefix
     * @return
     */
    protected String genPicName(String fileExtName, String prefix) {
        return new StringBuilder(prefix).append("_").append(System.currentTimeMillis()).append("_").
                append(getSuffix()).append(".").append(fileExtName).toString();
    }

    /**
     * get the suffix for image file
     *
     * @return
     * @todo:从共享缓存取
     */
    protected int getSuffix() {
        if (suffix.get() == 1000000L) {
            suffix = new AtomicInteger(0);
        }
        return suffix.incrementAndGet();
    }
}
