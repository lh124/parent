package io.yfjz.service.file.impl;

import io.yfjz.dao.basesetting.TMvRelevanceDao;
import io.yfjz.dao.basesetting.TMvUploadDao;
import io.yfjz.entity.basesetting.TMvUploadEntity;
import io.yfjz.service.file.SysFileService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.FileUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.UploadUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

/**
 * @author 刘琪
 * @class_name: SysFileServiceImpl
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-07-23 17:09
 */
@Service
public class SysFileServiceImpl implements SysFileService {
    private static Logger logger=Logger.getLogger(SysFileServiceImpl.class);

    @Autowired
    TMvUploadDao tMvUploadDao;

    @Autowired
    TMvRelevanceDao tMvRelevanceDao;

    @Override
    public R uploadFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            net.sf.json.JSONObject json = new net.sf.json.JSONObject();
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iter.next());
                    String originalFilename = file.getOriginalFilename();//文件的真实名称
                    if (Constant.FILESIZE < file.getSize()) {
                        return R.error(-1, "上传文件过大，请选择小于" + Constant.FILESIZE / Constant.FORMATTER + "MB的文件重试");
                    }
                    if (!file.isEmpty()) {
                        File tmpFile = FileUtils.multipartToFile(request, file);
                        String respBody = UploadUtils.getInstance().fileUploadFileToServer(tmpFile); //如果返回的不是图片地址，返回异常信息
                        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(respBody);
                        int code = jsonObject.getInteger("code");
                        String msg = jsonObject.getString("msg");
                        if (code < 0) {
                            continue;
                        }
                        String filesize = FileUtils.getFileSize(tmpFile);
                        json.put("code", code);
                        json.put("data", msg);
                        json.put("filename", originalFilename);

                        //保存文件上传成功之后的结果
                        TMvUploadEntity tMvUploadEntity = new TMvUploadEntity();
                        tMvUploadEntity.setCreateTime(new Date());
                        tMvUploadEntity.setId(UUID.randomUUID().toString());
                        tMvUploadEntity.setRealname(originalFilename);
                        tMvUploadEntity.setFilesize(filesize);
                        tMvUploadEntity.setUrl(msg);
                        tMvUploadDao.save(tMvUploadEntity);
                        if (tmpFile.exists()) {
                            tmpFile.delete();
                        }
                    } else {
                        return R.error(-1, "请选择文件上传");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.error(-2, "系统异常<>"+e.getMessage());
        }
        return R.ok(0,"上传成功");
    }

    @Transactional
    @Override
    public R deleteFileFromServer(String[] ids) {
        try {
            if (ids.length<=0){
                return R.error(-1,"请选择需要删除的视频列表");
            }
            for (int i = 0; i < ids.length; i++) {
                String mvid = ids[i];
                try {
                    TMvUploadEntity tMvUploadEntity = tMvUploadDao.queryObject(mvid);
                    if (tMvUploadEntity != null)
                    //不关心是否能删除服务器上的数据，如果服务器上不存在，删除为报异常
                    UploadUtils.getInstance().deletefileFromServer(tMvUploadEntity.getUrl());
                } catch (Exception e) {
                    logger.error("从服务器上删除失败");
                }
                tMvRelevanceDao.deleteByMvId(mvid);
                tMvUploadDao.delete(mvid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("从服务器上删除文件出现系统异常");
            return R.error(-1, "删除失败，请联系管理员");
        }
        return R.ok(0,"删除成功");
    }
}
