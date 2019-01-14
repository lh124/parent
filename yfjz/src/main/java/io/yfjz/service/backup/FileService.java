package io.yfjz.service.backup;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 读取文件及保存文件的业务接口
 *
 */
public interface FileService {

    /**
     * 获取指定路径下所有的文件并且分页显示
     * @param dirPath 指定的路径
     * @param startPage 分页的页数
     * @param pageCount 分页的页面总数
     * @return
     */
    Map<String,Object> getAllFilesWithPage(String dirPath, Integer startPage, Integer pageCount);

    /**
     * 上传文件到指定目录
     * @param file  源文件
     * @param destination 目标路径
     * @return
     */
    Map<String,Object> uploadFile(MultipartFile file, String destination);

    /**
     * 通过shell备份或者还原数据文件
     * @param shellPath
     * @return
     */
    Process execShell(String shellPath, String shell);

    /**
     * 检查备份是否成功
     * @param process
     * @return
     * @throws InterruptedException
     */
    Map<String,Object> backupJadge(Process process) throws InterruptedException;

}
