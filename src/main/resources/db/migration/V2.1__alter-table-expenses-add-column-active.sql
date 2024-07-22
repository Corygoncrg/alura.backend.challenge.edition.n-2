alter table expenses add active boolean;
update expenses
SET active = true;