create function like_count_update() returns trigger as
$$
declare
    new_biz_reg_no varchar(10) := (select biz_reg_no from "BizPlace" where place_nm=new.place_nm);
begin

    if not exists(select * from "UserRecommend" where biz_reg_no = new_biz_reg_no) then
        insert into "UserRecommend" values (new_biz_reg_no, 0);
    end if;
    update "UserRecommend" U set like_count = like_count + 1 where U.biz_reg_no = new_biz_reg_no;
    return null;
end;

$$
    language 'plpgsql';


create trigger like_count_delegation
    instead of update
    on "ExpendtrTotalExcut"
    for each row
execute procedure like_count_update();

-- 아래와 같이 사용
update "ExpendtrTotalExcut"
set like_count = like_count + 1
where place_nm = '감사한양';
