drop table Product;

create table Product(
product_id  char(20) not null, 
product_name    varchar(100),
category   varchar(50),
product_line   varchar(50), 
status     varchar(50),
product_rrp     double(10,2),
promotion_price    double(10,2),
juhuasuan_price    double(10,2),
daily_price     double(10,2),
coo  varchar(50),
tm_sku  varchar(50),
jd_sku  varchar(50)
) character set = utf8;