drop table BundleProduct;

create table BundleProduct(
bundle_id  char(20) not null, 
product_id  char(20) not null
);

insert into BundleProduct values
("012312321354", "0522135290")