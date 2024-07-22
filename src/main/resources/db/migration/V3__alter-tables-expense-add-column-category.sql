alter table expenses add category ENUM(
'FOOD', 'HEALTH','HOME',
'TRANSPORT','EDUCATION','LEISURE','UNFORESEEN','OTHER') Default 'OTHER';
update expenses
SET category = 'OTHER';
