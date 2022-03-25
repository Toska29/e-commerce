-- set foreign_key_checks = 0; for mysql

truncate table product cascade;
truncate table item cascade ;
truncate table cart cascade ;
truncate table cart_items cascade ;
truncate table app_user cascade ;


insert into product(id, name, price, quantity)
values(12, 'Luxury Mop', 2340, 3),
      (13, 'Macbook Air', 500, 4),
      (14, 'Rocking chair', 3456, 5),
      (15, 'Purple T-shirt', 3455, 6);

insert into item(id, product_id, quantity_added)
values(102, 14, 2),
       (122, 15, 3),
       (133, 12, 1);

insert into cart(id, total_price)
values (345, 0.0),
       (355, 0.0),
       (366, 0.0);

insert into cart_items(cart_id, items_id)
values (345, 102),
       (345, 122),
       (345, 133);

insert into app_user(id, first_name, last_name, email, my_cart_id, password)
values (5005, 'John', 'Badmus', 'john@myspace.com', 345, 'word'),
       (5010, 'Chris', 'Tuck', 'chris@myspace.com', 355, 'word'),
       (5011, 'Goodnews', 'Confidence', 'goodconfidence@myspce.com', 366, 'word');

-- set foreign_key_checks = 1; for mysql
