-- user => 예약어 MemberUser 로 대체
create table MemberUser(
    user_id Number primary key,
    user_name VARCHAR2(50) not null,
    user_pwd VARCHAR2(50) not null,
    user_email varchar2(50) not null,
    user_phone varchar2(50) not null,
    user_role varchar2(50) not null,
    constraint uniq_MemberUser UNIQUE(user_name, user_email, user_phone)
);

-- Review
create table Review(
    review_id Number primary key,
    user_id Number not null,
    review_title varchar2(50) not null,
    review_content varchar2(1000) not null,
    create_date Date not null,
    update_date Date not null,
    review_views Number not null,
    review_recommends Number not null,
    CONSTRAINT fk_userid FOREIGN key(user_id) REFERENCES MemberUser(user_id)   
);


-- Review_comment
create table Review_Comment(
    comment_id Number primary key,
    review_id Number not null,
    create_date Date not null,
    update_date Date not null,
    review_views Number not null,
    comment_group Number not null,
    comment_depth Number not null,
    user_id int not null,
    constraint fk_review_id foreign key(review_id) references Review(review_id),
    constraint fk_comment_user_id foreign key(user_id) references MemberUser(user_id)
);


-- Path
create table Path(
    path_id Number primary key,
    user_id Number not null,
    create_date Date not null,
    update_date Date not null,
    path_title Varchar2(50) not null,
    path_view Number not null,
    path_recommends Number not null,
    constraint fk_path_user_id foreign key(user_id) references MemberUser(user_id)   
);


-- mark
create table Mark(
    mark_id Number primary key,
    path_id Number not null,
    mark_posX Number not null,
    mark_posY Number not null,
    place_order Number not null,
    constraint fk_mark_path_id foreign key(path_id) references Path(path_id)
);


-- favorite
create table Favorite(
    favorite_id Number primary key,
    user_id Number not null,
    path_id Number not null,
    constraint fk_favorite_user_id foreign key(user_id) references MemberUser(user_id),
    constraint fk_favorite_path_id foreign key(path_id) references Path(path_id)
);

-- path_comment
create table path_comment(
    comment_id Number primary key,
    path_id Number not null,
    create_date Date not null,
    update_date Date not null,
    review_content Varchar2(50) not null,
    comment_group VARCHAR2(100) not null,
    comment_depth VARCHAR2(100) not null,
    user_id int not null,
    constraint fk_PC_path_id foreign key(path_id) references Path(path_id),
    constraint fk_PC_user_id foreign key(user_id) references MemberUser(user_id)
);

CREATE SEQUENCE MemberUser_Sequence 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1   
    NOCYCLE;

CREATE SEQUENCE Review_Sequence  
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1   
    NOCYCLE;

CREATE SEQUENCE Review_Comment_Sequence  
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1   
    NOCYCLE;

CREATE SEQUENCE Path_Sequence  
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1   
    NOCYCLE;

CREATE SEQUENCE Mark_Sequence  
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1   
    NOCYCLE;

CREATE SEQUENCE Favorite_Sequence  
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1   
    NOCYCLE;

CREATE SEQUENCE Path_Comment_Sequence  
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1   
    NOCYCLE;

-- 생성된 테이블 조회    
SELECT * FROM tabs; 

-- 생성된 시퀀스 조회
select * from user_sequences;