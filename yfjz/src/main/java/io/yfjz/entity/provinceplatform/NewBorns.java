package io.yfjz.entity.provinceplatform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;



/**
 * 省平台下载新生儿表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 14:33:28
 */


@XStreamAlias("newborns")
public class NewBorns implements Serializable {
    @XStreamAlias("newborn")
   private TChildDownloadEntity tChildDownloadEntity;

    public TChildDownloadEntity gettChildDownloadEntity() {
        return tChildDownloadEntity;
    }

    public void settChildDownloadEntity(TChildDownloadEntity tChildDownloadEntity) {
        this.tChildDownloadEntity = tChildDownloadEntity;
    }
}
