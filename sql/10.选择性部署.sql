

# Structure for table "order_seq"
#

DROP TABLE IF EXISTS `order_seq`;
CREATE TABLE `order_seq` (
  `seq_name` varchar(50) NOT NULL,
  `current_val` int(11) NOT NULL,
  `max_val` int(4) NOT NULL,
  `increment_val` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "order_seq"
#

INSERT INTO `order_seq` VALUES ('oc_user',1,9999,1);

#
DROP FUNCTION IF EXISTS `currval`;

CREATE  FUNCTION `currval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin        
    declare value integer;         
    set value = 0;         
    select current_val into value from order_seq where seq_name = v_seq_name;   
   return value;   
end;

DROP FUNCTION IF EXISTS `nextval`;

CREATE   FUNCTION `nextval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin  
		declare max integer;
		select max_val into max from order_seq where seq_name = v_seq_name; 
		
    if currval(v_seq_name)<max then
    		update order_seq set current_val = current_val + increment_val  where seq_name = v_seq_name; 
    else 
    		update order_seq set current_val = 1000  where seq_name = v_seq_name;
  	end if; 
  	
    return currval(v_seq_name);  
end;

 

## select nextval('oc_user') as end from dual;

## SELECT  CONCAT(DATE_FORMAT(now() ,'%Y%m%d%H%i%s') , nextval('oc_user'))  FROM dual;