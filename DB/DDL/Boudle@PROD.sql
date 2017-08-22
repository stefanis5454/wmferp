drop table Bundle;

create table Bundle(
bundle_id  char(20) not null, 
bundle_tm_id  varchar(50),
bundle_jd_id  varchar(50),
bundle_name    varchar(100),
total_rrp       double(10,2),
promotion_price    double(10,2),
juhuasuan_price    double(10,2),
daily_price     double(10,2),
coo  varchar(50),
tm_sku  varchar(50),
jd_sku  varchar(50)

) character set = utf8;