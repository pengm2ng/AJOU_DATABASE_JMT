create table "DeptDiv"
(
    dept_div_cd varchar(2)  not null
        constraint deptdiv_pk
            primary key,
    dept_div_nm varchar(20) not null
);

comment on table "DeptDiv" is '부서구분';

comment on column "DeptDiv".dept_div_cd is '부서구분코드';

comment on column "DeptDiv".dept_div_nm is '부서구분명';

create table "GovofcDiv"
(
    govofc_div_cd varchar(4)  not null
        constraint govofcdiv_pk
            primary key,
    dept_div_cd   varchar(2)  not null
        constraint govofcdiv_deptdiv_dept_div_cd_fk
            references "DeptDiv"
            on update cascade on delete cascade,
    govofc_div_nm varchar(20) not null
);

comment on table "GovofcDiv" is '관서구분';

comment on column "GovofcDiv".govofc_div_cd is '관서구분코드';

comment on column "GovofcDiv".dept_div_cd is '관서구분코드 FK';

comment on column "GovofcDiv".govofc_div_nm is '관서명';


create table "HgdeptDiv"
(
    hgdept_div_cd varchar(4)  not null
        constraint hgdeptdiv_pk
            primary key,
    hgdept_div_nm varchar(20) not null
);

comment on table "HgdeptDiv" is '실국';

comment on column "HgdeptDiv".hgdept_div_cd is '실국코드';

comment on column "HgdeptDiv".hgdept_div_nm is '실국명';

create table "Dept"
(
    dept_cd_nm varchar(7)  not null
        constraint dept_pk
            primary key,
    dept_nm    varchar(20) not null
);

comment on table "Dept" is '부서';

comment on column "Dept".dept_cd_nm is '부서코드';

comment on column "Dept".dept_nm is '부서명';

create table "GovofcDiv_HgdeptDiv"
(
    govofc_div_cd varchar(4) not null
        constraint govofcdiv_hgdeptdiv_linker_fk
            references "GovofcDiv"
            on update cascade on delete cascade,
    hgdept_div_cd varchar(4) not null
        constraint hgdeptdiv_govofcdiv_linker_fk
            references "HgdeptDiv"
            on update cascade on delete cascade
);

comment on table "GovofcDiv_HgdeptDiv" is '관서_실국 링커';

comment on column "GovofcDiv_HgdeptDiv".govofc_div_cd is '관서 FK';

comment on column "GovofcDiv_HgdeptDiv".hgdept_div_cd is '실국 FK';

create table "HgdeptDiv_Dept"
(
    hgdept_div_cd varchar(4) not null
        constraint hgdeptdiv_dept_linker_fk
            references "HgdeptDiv"
            on update cascade on delete cascade,
    dept_cd_nm    varchar(4) not null
        constraint dept_hgdeptdiv_linker_fk
            references "Dept"
            on update cascade on delete cascade
);

comment on table "HgdeptDiv_Dept" is '실국_부서 링커';

comment on column "HgdeptDiv_Dept".hgdept_div_cd is '실국 FK';

comment on column "HgdeptDiv_Dept".dept_cd_nm is '부서 FK';


create table AccnutDiv
(
    accnut_div_cd varchar(3)  Not NULL,
    accnut_div_nm varchar(20) Not NULL,
    primary key (accnut_div_cd)
);
create table BizPlace
(
    biz_reg_no varchar(10) Not NULL,
    place_nm   varchar(50) Not NULL,
    primary key (biz_reg_no)
);
create table UserRecommend
(
    biz_reg_no   varchar(10) Not NULL,
    like_account int         Not NULL,
    foreign key (biz_reg_no) references BizPlace (biz_reg_no)
);
create table ExpendtrExcut
(
    accnut_yy          varchar(4)  Not NULL,
    accnut_div_cd      varchar(3)  Not NULL,
    dept_cd_nm         varchar(7)  Not NULL,
    summry_info        date        Not NULL,
    expendtr_rsltn_amt int         Not NULL,
    biz_reg_no         varchar(10) Not NULL,
    foreign key (accnut_div_cd) references AccnutDiv (accnut_div_cd),
    foreign key (dept_cd_nm) references Dept (dept_cd_nm),
    foreign key (biz_reg_no) references BizPlace (biz_reg_no)
);
