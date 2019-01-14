#!/bin/bash

echo "开始恢复数据库..."
restore_path=/home/restore/
dd if='/home/restore/restore_file.des3' |openssl des3 -d -k 'JYtc2017Yfjz'|tar -zvxf - -C /home/restore/
#restore_file=restore_file.tar.gz
restore_file=restore_file.des3
dir=$(ls -l $restore_path |awk '/^d/ {print $NF}')
cd $restore_path
#dd if='/home/restore/restore_file.des3'|openssl des3 -d -k 123456|tar zxvf -
mysql_data=/home/mysql/data/
service mysql stop
echo "Shutdowning mysql server..."

rm -rf $mysql_data*
for i in $dir
do
  echo $i
  innobackupex --apply-log $ /re_path$i
  innobackupex --copy-back --rsync $restore_path$i
  chown -R mysql.mysql $mysql_data
done
 service mysql start
rm -rf $restore_path*

#data=$(ps -ef|grep mysql|grep -v grep|wc -l)

#if [ $data -eq 2 ]
#then
#   echo '数据库恢复成功'
#else
#  echo '数据库恢复失败'
#fi
 
