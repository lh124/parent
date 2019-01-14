#!/bin/bash
echo "开始备份数据库......"
path=/home/backup_mysql/
if [ ! -d "$path" ]; then
  mkdir "$path"
fi
count=$(ls -lR|grep "^-"|wc -l)
if [ count>30 ]; then
  find "$path" -mtime +30 -name "*.*" -exec rm -rf {} \;
fi
innobackupex --user=root --password='jytc$123' $path
dir=$(ls -l $path |awk '/^d/ {print $NF}')
cd $path
#subfix=.tar.gz
filesubfix=.des3
for i in $dir
do
  echo $i
  #tar -zcvf $i$subfix $i
  tar -zcvf - $i|openssl des3 -salt -k 'JYtc2017Yfjz'| dd of=$i$filesubfix
  rm -rf $i
done
file="$path$i$filesubfix"
echo $file
if [ -f "$file" ]
then
 echo "备份成功！"
else
 echo "备份失败！"
fi
