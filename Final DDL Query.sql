create table "DeptDiv"
(
    dept_div_cd varchar(2)  not null
        constraint deptdiv_pk
            primary key,
    dept_div_nm varchar(30) not null
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
        constraint govofcdiv_deptdiv_fk
            references "DeptDiv"
            on update cascade on delete cascade,
    govofc_div_nm varchar(30) not null
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
    hgdept_div_nm varchar(30) not null
);

comment on table "HgdeptDiv" is '실국';

comment on column "HgdeptDiv".hgdept_div_cd is '실국코드';

comment on column "HgdeptDiv".hgdept_div_nm is '실국명';

create table "Dept"
(
    dept_cd_nm varchar(7)  not null
        constraint dept_pk
            primary key,
    dept_nm    varchar(30) not null
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
    dept_cd_nm    varchar(7) not null
        constraint dept_hgdeptdiv_linker_fk
            references "Dept"
            on update cascade on delete cascade
);

comment on table "HgdeptDiv_Dept" is '실국_부서 링커';

comment on column "HgdeptDiv_Dept".hgdept_div_cd is '실국 FK';

comment on column "HgdeptDiv_Dept".dept_cd_nm is '부서 FK';

create table "AccnutDiv"
(
    accnut_div_cd varchar(3)  not null
        constraint accnutdiv_pk
            primary key,
    accnut_div_nm varchar(20) not null
);

comment on table "AccnutDiv" is '회계구분';

comment on column "AccnutDiv".accnut_div_cd is '회계구분코드';

comment on column "AccnutDiv".accnut_div_nm is '회계구분명';

create table "BizPlace"
(
    biz_reg_no varchar(10) not null
        constraint bizplace_pk
            primary key,
    place_nm   varchar(50) not null
);

comment on table "BizPlace" is '거래처';

comment on column "BizPlace".biz_reg_no is '사업자번호';

comment on column "BizPlace".place_nm is '거래처명';

create table "UserRecommend"
(
    biz_reg_no varchar(10) not null
        constraint userrecommend_bizplace_fk
            references "BizPlace"
            on update cascade on delete cascade,
    like_count int         not null
);

comment on table "UserRecommend" is '유처 추천';

comment on column "UserRecommend".biz_reg_no is '사업자번호 FK';

comment on column "UserRecommend".like_count is '추천수';

create table "ExpendtrExcut"
(
    accnut_yy          varchar(4)                   not null,
    accnut_div_cd      varchar(3)                   not null,
    dept_cd_nm         varchar(7) default '*******' not null
        constraint expendtrexcut_dept_fk
            references "Dept"
            on update cascade on delete set default,
    paymnt_command_de  date                         not null,
    expendtr_rsltn_amt int                          not null,
    biz_reg_no         varchar(10)                  not null
        constraint expendtrexcut_bizplace_fk
            references "BizPlace"
            on update cascade on delete cascade
);

comment on table "ExpendtrExcut" is '지출집행내역';

comment on column "ExpendtrExcut".accnut_yy is '회계연도';

comment on column "ExpendtrExcut".accnut_div_cd is '회계구분코드 FK';

comment on column "ExpendtrExcut".dept_cd_nm is '부서코드 FK';

comment on column "ExpendtrExcut".paymnt_command_de is '지급명령일자';

comment on column "ExpendtrExcut".expendtr_rsltn_amt is '지출금액';

comment on column "ExpendtrExcut".biz_reg_no is '사업자번호 FK';
