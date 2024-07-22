alter table receipts add active boolean;
update receipts
SET active = true;