hdfs dfs -rm -r /user/hadoop/phone-output

hadoop jar /opt/apps/ecm/service/hadoop/3.2.1-1.2.1/package/hadoop-3.2.1-1.2.1/share/hadoop/tools/lib/hadoop-streaming-3.2.1.jar \
 -mapper /home/student4/lidazhi/hw-MapReduce/mapper.py \
 -file /home/student4/lidazhi/hw-MapReduce/mapper.py \
 -file /home/student4/lidazhi/hw-MapReduce/reducer.py \
 -reducer /home/student4/lidazhi/hw-MapReduce/reducer.py \
 -input /user/hadoop/input/HTTP_20130313143750.dat \
 -output /user/hadoop/phone-output 
