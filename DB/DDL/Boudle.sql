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

insert into Bundle values
("012312321354", "0722112321", "0922112321", "奈彩米炒锅平底锅煎锅套装", 4299.0, 2299.0, 2499.0, 3299.0, "混合", "530173441389", "10450385743");

insert into Bundle values
("012312321352", "0722112322", "0922112322", "奈彩米高压锅套装", 6299.0, 2299.0, 2499.0, 3299.0, "混合", "530173441389", "10450385743");



SELECT * from Bundle p where LOCATE('高压',CONCAT_WS('*@*@',
bundle_id,bundle_tm_id,bundle_jd_id,bundle_name,total_rrp,coo,tm_sku,jd_sku,promotion_price,juhuasuan_price,daily_price ));


select * from Bundle b where b.bundle_id in 
(select bundle_id from BundleProduct b 
where b.product_id = '0522135290')