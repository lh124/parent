//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;

public class Constant {

    public static final long FILESIZE = 2 * 1000 * 1024 * 1024;// 文件大小2GB设置
    public static final long FORMATTER = 1024 * 1024;// 使用文件的size  除以此参数，转换文件的格式为平时查看的习惯：MB


    //shiro 全局常量，获取当前登录用户的机构编码及机构名称
    public static String GLOBAL_ORG_ID = null;
    public static String GLOBAL_ORG_NAME = null;

    //入库类型
    public static final int IN_TYPE_NORMAL=0;//正常入库
    public static final int IN_TYPE_RETURN=1; //归还入库

    //报损类型
    public static final int WASTAGE_SCRAP=0;//报废
    public static final int WASTAGE_LOSS=1; //损耗

    //工作台属性
    //登记台
    public static final int TOWER_TYPE_REGISTER=1;
    //接种台
    public static final int TOWER_TYPE_INOCULATE=2;
    //儿保台
    public static final int TOWER_TYPE_CHILD_HEALTHCARE=3;
    //预检台
    public static final int TOWER_TYPE_CHILD_CHECK=4;
    //留观
    public static final int TOWER_TYPE_OBSERVATION=5;


    //疫苗出库类型
    //出库类型:1领疫苗，2报损，3退货,
    public static final int OUT_TYPE_RECEIVE=1;
    public static final int OUT_TYPE_DAMAGE=2;
    public static final int OUT_TYPE_RETURN=3;

    //数据库备份还原参数
    //备份路径
    public static final String DOWNLOADFILEPATH = "/home/backup_mysql";
//  public static final String DOWNLOADFILEPATH = "E://lib";
    //还原路径
    public static final String UPLOADFILEPATH = "/home/restore";
    //还原文件名
    public static final String FILENAME = "restore_file.des3";
    //shell脚本路径
    public static String SHELLPATH = "/home/shell/";
    //备份脚本名称
    public static final String BACKUPSHELL = "/mysql_backup.sh";
    //还原脚本名称
    public static final String RESTORESHELL = "/mysql_restore.sh";

    //录入类型
    //扣减库存
    public static final String INOCULATE_TYPE_REMOVE="remove";
    //不扣减库存
    public static final String INOCULATE_TYPE_NORMAL="normal";
    /**排队叫号启用类型*/
    public static final String QUEUE_TYPE="queue_type";
    /**接种台多队列启用类型*/
    public static final String QUEUE_DISTINCTION="setQueuesDistinction";

    //角色名称 村医
    public static final String ROLE_COUNTRY="村医";

    public Constant() {
    }

    public static enum MenuType {
        CATALOG(0),
        MENU(1),
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum ScheduleStatus {
        NORMAL(0),
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
