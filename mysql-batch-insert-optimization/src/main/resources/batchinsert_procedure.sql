drop procedure if exists mall_db_test.batchinsert;

DELIMITER $$
create procedure mall_db_test.batchinsert(cnt int)
	begin
		declare i int default 0;
		start transaction;
		while i<cnt
			do
		   	insert oms_order(member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, receiver_name, receiver_phone, delete_status) values(1,2,"abc", "2020-09-01 00:00:00", "aaa", 1000, 1000, 100, 100, 10, 10, 0, "ccc", "15811111111", 0);
			set i=i+1;
		end while;
		commit;
	end  $$

DELIMITER;

-- call mall_db_test.batchinsert(1000000)