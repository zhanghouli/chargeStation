package com.jopool.chargingStation.www.controller;

import com.jopool.chargingStation.www.base.client.AliyunOSSClient;
import com.jopool.chargingStation.www.base.entity.CommonPicture;
import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.chargingStation.www.base.pay.wxpay.common.HttpKit;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXUrlConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.response.Attachment;
import com.jopool.chargingStation.www.base.pay.wxpay.response.TokenResp;
import com.jopool.chargingStation.www.base.utils.FileUtil;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.PayWxService;
import com.jopool.chargingStation.www.vo.ImageSizeVo;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.enums.ModeEnum;
import com.jopool.jweb.utils.DateUtils;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by synn on 2017/8/25.
 */
@Controller
@RequestMapping("/common/file")
public class CommonFileController extends BaseController {
    private static final Logger        logger            = LoggerFactory.getLogger(CommonFileController.class);
    public static final  String        IMG_TYPE_ORIGINAL = "org";//原图片
    // private static       String        localUploadRootPath = ApplicationConfigHelper.getFilePath();
    private              AtomicInteger suffix            = new AtomicInteger(0);
    @Resource
    private PassportService passportService;
    @Resource
    private CommonService   commonService;
    @Resource
    private PayWxService    payWxService;

    /**
     * JP003001文件上传
     * http://wiki.jopool.net/pages/viewpage.action?pageId=4227204
     *
     * @param request
     * @param response
     * @param file
     * @return
     */
    @RequestMapping("upload.htm")
    public
    @ResponseBody
    Result upload(HttpServletRequest request, HttpServletResponse response, MultipartFile file, @RequestParam(defaultValue = "true") boolean isFullPath, String bucket) {
        if (ModeEnum.DEVELOP == ApplicationConfigHelper.getMode()) {
            localUploadRootPath = request.getSession().getServletContext().getRealPath(ApplicationConfigHelper.getFilePath());
        }
        try {
            FileUtil.saveFile(file.getInputStream(), getAbsoluteFolderPayPath(bucket, getSessionUser().getPassportId()), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(Code.ERROR);
        }
        return new Result(Code.SUCCESS, Result.createJsonMap("path", getRelativeFolderPayPath(bucket, getSessionUser().getPassportId()) + file.getOriginalFilename()));
    }


    /**
     * KE文件上传
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("uploadKE.htm")
    @ResponseBody
    public Map<String, Object> uploadKE(HttpServletRequest request, HttpServletResponse response, MultipartFile imgFile, @RequestParam(defaultValue = "ke") String bucket) {
        String saveContextPath = DateUtils.date2String(new Date(), "yyyyMMdd");
        String fileId = UUIDUtils.generateShortUuid();
        String suffix = "";
        String fileName = imgFile.getOriginalFilename();
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf(".");
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                suffix = fileName.substring(dot + 1);
            }
        }
        String key = bucket + "/" + saveContextPath + "/" + fileId + "." + suffix;
        try {
            AliyunOSSClient.getInstance().putObject(ApplicationConfigHelper.getoSSbucketName(), key, new ByteArrayInputStream(imgFile.getBytes()));
        } catch (IOException e) {
            return Result.createJsonMap(new String[]{"error", "url"}, new Object[]{0, ""});
        }
        return Result.createJsonMap(new String[]{"error", "url"}, new Object[]{0, ApplicationConfigHelper.getoSSOpenUrl() + "/" + key});
    }

//    /**
//     * JP003002 base64文件上传
//     * http://wiki.jopool.net/pages/viewpage.action?pageId=4555051
//     *
//     * @param request
//     * @param response
//     * @param fileBase64
//     * @param fileFormat
//     * @return
//     */
//    @RequestMapping("uploadBase64.htm")
//    public
//    @ResponseBody
//    Result uploadBase64(HttpServletRequest request, HttpServletResponse response, String fileBase64, String fileFormat) {
//        String fileId = UUIDUtils.generateShortUuid();
//        String saveDir = ApplicationConfigHelper.getFilePath();
//        if (ModeEnum.DEVELOP == ApplicationConfigHelper.getMode()) {
//            saveDir = request.getSession().getServletContext().getRealPath(ApplicationConfigHelper.getFilePath());
//        }
//        String saveContextPath = DateUtils.date2String(new Date(), "yyyyMMdd");
//        String savePath = saveDir + File.separator + saveContextPath;
//
//        String newFileName = fileId + "." + fileFormat;
//        try {
//            File targetFile = new File(savePath, newFileName);
//            if (!targetFile.getParentFile().exists()) {
//                targetFile.getParentFile().mkdirs();
//            }
//            byte[] bytes = new BASE64Decoder().decodeBuffer(fileBase64);
//            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
//            byte[] buffer = new byte[1024];
//            FileOutputStream out = new FileOutputStream(targetFile);
//            int bytesum = 0;
//            int byteread = 0;
//            while ((byteread = in.read(buffer)) != -1) {
//                bytesum += byteread;
//                out.write(buffer, 0, byteread); //文件写操作
//            }
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        String path = request.getContextPath();
//        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/files/";
//        return new Result(Code.SUCCESS, Result.createJsonMap("path", basePath + saveContextPath + File.separator + newFileName));
//    }


    /**
     * 图片上传
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7241851
     *
     * @return
     */
    @RequestMapping("uploadBase64.htm")
    @ResponseBody
    public Result uploadBase64(BaseParam baseParam, String fileBase64, String fileName, String hostType, HttpServletRequest request, HttpServletResponse response) {
        String moduleName = hostType;
        if (ModeEnum.DEVELOP == ApplicationConfigHelper.getMode()) {
            localUploadRootPath = request.getSession().getServletContext().getRealPath(ApplicationConfigHelper.getFilePath());
        }

        CommonPicture picture = null;
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(fileBase64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            String absoluteOrigPicParentPath = getAbsoluteFolderPath(passport.getType(), passport.getId(), moduleName, IMG_TYPE_ORIGINAL);
            if (!new File(absoluteOrigPicParentPath).exists()) {
                new File(absoluteOrigPicParentPath).mkdirs();
            }
            String fileNewName = genPicName(fileName.substring(fileName.lastIndexOf(".") + 1), hostType);
            File newFile = new File(absoluteOrigPicParentPath + "/" + fileNewName);
            FileUtil.saveFile(inputStream, absoluteOrigPicParentPath, fileNewName);
            picture = commonService.buildOrgPic(ImageIO.read(new FileInputStream(newFile)), 0L, moduleName, fileName, fileNewName, "/" + getRelativeFolderPath(passport.getType(), passport.getId(), moduleName, IMG_TYPE_ORIGINAL), hostType, passport.getId(), passport.getType(), passport.getId());
        } catch (IOException e) {
            logger.error("PictureManagerImpl.savePicAfterWechatUpload", e);
            return null;
        }
        return new Result(Code.SUCCESS, Result.createJsonMap(new String[]{"id", "path"}, new Object[]{picture.getId(), commonService.getLogoUrl(picture.getId())}));
    }


    /**
     * 图片微信上传
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7241851
     *
     * @return
     */
    @RequestMapping("uploadWxFile.htm")
    @ResponseBody
    public Result uploadWxFile(BaseParam baseParam, String serverId, String hostType, HttpServletRequest request, HttpServletResponse response) {
        String moduleName = hostType;
        if (ModeEnum.DEVELOP == ApplicationConfigHelper.getMode()) {
            localUploadRootPath = request.getSession().getServletContext().getRealPath(ApplicationConfigHelper.getFilePath());
        }
        CommonPicture picture = null;
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        //token
        TokenResp tokenResp = commonService.getAccessToken(Constants.SYSTEM_ID);
        if (null == tokenResp || StringUtils.isEmpty(tokenResp.getAccess_token())) {
            return new Result(Code.ERROR, "获取access_token失败");
        }
        try {
            String url = WXUrlConfig.WX_GET_MEDIA_URL + tokenResp.getAccess_token() + "&media_id=" + serverId;
            Attachment attachment = HttpKit.download(url);
            String absoluteOrigPicParentPath = getAbsoluteFolderPath(passport.getType(), passport.getId(), moduleName, IMG_TYPE_ORIGINAL);
            if (!new File(absoluteOrigPicParentPath).exists()) {
                new File(absoluteOrigPicParentPath).mkdirs();
            }
            String fileNewName = genPicName(attachment.getSuffix(), hostType);
            File newFile = new File(absoluteOrigPicParentPath + "/" + fileNewName);
            FileUtil.saveFile(attachment.getFileStream(), absoluteOrigPicParentPath, fileNewName);
            picture = commonService.buildOrgPic(ImageIO.read(new FileInputStream(newFile)), 0L, moduleName, attachment.getFileName(), fileNewName, "/" + getRelativeFolderPath(passport.getType(), passport.getId(), moduleName, IMG_TYPE_ORIGINAL), hostType, passport.getId(), passport.getType(), passport.getId());
        } catch (Exception e) {
            logger.error("PictureManagerImpl.savePicAfterWechatUpload", e);
            return null;
        }
        return new Result(Code.SUCCESS, Result.createJsonMap(new String[]{"id", "path"}, new Object[]{picture.getId(), commonService.getLogoUrl(picture.getId())}));
    }


    /**
     * 图片 上传 并获取大小
     *
     * @param request
     * @param response
     * @param file
     * @param isFullPath
     * @param bucket
     * @return
     */
    @RequestMapping("uploadImage.htm")
    public
    @ResponseBody
    Result uploadImage(HttpServletRequest request, HttpServletResponse response, MultipartFile file, @RequestParam(defaultValue = "true") boolean isFullPath, String bucket, String passportId) {
        if (ModeEnum.DEVELOP == ApplicationConfigHelper.getMode()) {
            localUploadRootPath = request.getSession().getServletContext().getRealPath(ApplicationConfigHelper.getFilePath());
        }
        Pattern p = Pattern.compile("(bmp|gif|jpg2000|psd|swf|svg|PSD|SWF|SVG|jpg|jpeg|png|JPG|PNG)");
        //新的文件名
        String fileId = UUIDUtils.generateShortUuid();
        //类型
        String suffix = "";
        //上传的文件名
        String fileName = file.getOriginalFilename();
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf(".");
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                suffix = fileName.substring(dot + 1);
            }
        }
        Matcher m = p.matcher(suffix);
        if (!m.matches()) {
            return new Result(Code.ERROR);
        }
        String absoluteOrigPicParentPath = "";
        String lookPic = "";
        Passport passport = passportService.getById(passportId);
        String fileNewName = genPicName(suffix, fileId);
        if (passport != null) {
            absoluteOrigPicParentPath = getAbsoluteFolderPath(passport.getType(), passport.getId(), fileName.substring(0, fileName.lastIndexOf(".")), IMG_TYPE_ORIGINAL);
            lookPic = getRelativeFolderPath(passport.getType(), passport.getId(), fileName.substring(0, fileName.lastIndexOf(".")), IMG_TYPE_ORIGINAL);
        } else {
            absoluteOrigPicParentPath = getAbsoluteFolderPath(getSessionUser().getPassportType(), getSessionUser().getPassportId(), fileName.substring(0, fileName.lastIndexOf(".")), IMG_TYPE_ORIGINAL);
            lookPic = getRelativeFolderPath(getSessionUser().getPassportType(), getSessionUser().getPassportId(), fileName.substring(0, fileName.lastIndexOf(".")), IMG_TYPE_ORIGINAL);
        }
        ImageSizeVo imageSizeVo = new ImageSizeVo();
        imageSizeVo.setName(fileId + "." + suffix);
        imageSizeVo.setOrgName(fileName);
        imageSizeVo.setRelativeFolder("/" + lookPic);
        imageSizeVo.setRemoteRelativeUrl(imageSizeVo.getRelativeFolder() + "/" + fileNewName);
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            imageSizeVo.setSize((int) file.getSize());
            imageSizeVo.setHeight(bufferedImage.getHeight());
            imageSizeVo.setWidth(bufferedImage.getWidth());
            FileUtil.saveFile(file.getInputStream(), absoluteOrigPicParentPath, fileNewName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(Code.SUCCESS, Result.createJsonMap("imageVo", imageSizeVo));
    }
}

