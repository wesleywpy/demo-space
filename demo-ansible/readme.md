## 准备
1. 修改inventory文件，确定目标主机
2. 修改group_vars目录中的all.yml
3. 在playbook.yml中选择需要执行的role，修改对应defaults目录下的环境变量

安装命令
> ansible-playbook playbook.yml -i inventory

分步安装
> ansible-playbook playbook.yml -i inventory --step

## 注意
所有安装包需提前上传到目标主机