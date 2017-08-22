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


insert into Product values
("0522135290", "奈彩米快易锅6.5L RDS（红色）", "快易锅", "奈彩米", "电商", 4288.0, 1772.0, 1968.0, 2187.0, "德国", "45388774785", "10451162597")

insert into Product values
("0522135291", "奈彩米快易锅6.5L RDS（绿色）", "快易锅", "奈彩米", NULL, 4288.0, 1772.0, 1968.0, 2187.0, "德国", "45388774785", "10451162597")


select CONCAT(coalesce(product_id,""), coalesce(product_name, "") ,
coalesce(category,""), coalesce(product_line, "") ,
coalesce(status,""), coalesce(product_rrp, "") ,
coalesce(promotion_price,""), coalesce(juhuasuan_price, "") ,
coalesce(daily_price,""), coalesce(coo, "") ,
coalesce(tm_sku,""), coalesce(jd_sku, "") ) from Product p

delete from Product where 1 = 1

SELECT * from Product p
where LOCATE('0522',CONCAT_WS('*@*@',product_id,product_name,category
,product_line,status,product_rrp,promotion_price,juhuasuan_price,daily_price
,coo,tm_sku,jd_sku));

INSERT INTO Product VALUES ('0522135290','奈彩米快易锅6.5L RDS（红色）','快易锅','奈彩米','电商',4288.0,1772.0,1968.0,2187.0,'德国','45388774785','10451162597')

INSERT INTO Product VALUES ('BJB','百洁布','','','',118.0,70.8,82.6,94.4,'','‘544167455394','')

use 
select * from product where table_name='表名'

SELECT * from Product p where LOCATE('绿色',CONCAT_WS('*@*@',product_id,product_name,category,product_line,status,product_rrp,promotion_price,juhuasuan_price,daily_price,coo,tm_sku,jd_sku));

create wmf01 default character set utf8


select * from Product p where p.product_id in 
(select product_id from BundleProduct b 
where b.bundle_id = '012312321354')


SELECT product_id,product_name,category,product_line,status,product_rrp,promotion_price,juhuasuan_price,daily_price,coo,tm_sku,jd_sku from westside.wmferp.models.Product p where LOCATE('22',CONCAT_WS('*@*@',product_id,product_name,category,product_line,status,product_rrp,promotion_price,juhuasuan_price,daily_price,coo,tm_sku,jd_sku));

INSERT INTO Product VALUES ('1895486032','SPITZENKLASSE西式厨师刀20cm','Chef's knife ','SPITZENKLASSE','',1388.0,832.8,971.5999999999999,1110.4,'德国','','')

SELECT product_id,product_name,category,product_line,status,product_rrp,promotion_price,juhuasuan_price,daily_price,coo,tm_sku,jd_sku from Product p where LOCATE('22',CONCAT_WS('*@*@',product_id,product_name,category,product_line,status,product_rrp,promotion_price,juhuasuan_price,daily_price,coo,tm_sku,jd_sku));
