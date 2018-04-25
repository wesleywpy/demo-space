在hive CLI中执行
1. add jar {jar_path}
2. create temporary function my_lower as 'com.wesley.study.udf.Lower';
3. select id, my_lower(name) from table_temp;