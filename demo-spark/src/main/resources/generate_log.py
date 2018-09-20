#coding=UTF-8

import random
import time

url_paths = [
    "room/qingyangqu",
    "room/jinniuqu",
    "room/wuhouqu",
    "room/chenghuaqu",
    "room/tianfuxinqu",
    "room/jinjiangqu",
    "room/wenjiangqu",
    "room/gaoxinqu",
    "questions",
    "room"
]

ip_slices = [119,192,23,168,17,10,111,101,250,72,63,87,64,97,99,128,24,53,25]


http_referers = [
	"http://www.baidu.com/s?wd={query}",
	"https://www.sogou.com/web?query={query}",
	"http://cn.bing.com/search?q={query}",
	"https://search.yahoo.com/search?p={query}"
]

search_keyword = [
	"优客逸家 成都 金沙房源",
	"优客逸家 成都 沙湾房源",
	"优客逸家 成都 红牌楼房源",
	"优客逸家 成都 建设路房源",
	"优客逸家 成都 世纪城房源",
	"优客逸家 成都 华阳房源"
]

status_codes = [200, 301, 404, 502]

def sample_url():
	return random.sample(url_paths, 1)[0]

def sample_ip():
	slice = random.sample(ip_slices, 4)
	return ".".join([str(item) for item in slice])

def sample_referer():
	if random.uniform(0, 1) > 0.2:
		return "-"

	refer_str = random.sample(http_referers, 1)[0]
	query_str = random.sample(search_keyword, 1)[0]
	return refer_str.format(query=query_str)

def sample_code():
	return random.sample(status_codes, 1)[0]

def generate_log(count = 10):
	time_str = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()) 

	f = open("/home/yani/logs/access.log", "w+")

	while count >= 1: 
		query_log = "{ip}\t{localtime}\t\"GET /{url} HTTP/1.1\"\t{code}\t{referer}".format(localtime=time_str, url=sample_url(), ip=sample_ip(), referer=sample_referer(), code=sample_code())
		f.write(query_log + "\n")
		count = count - 1

if __name__ == '__main__':
	generate_log()