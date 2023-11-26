create user earbee identified by root
    default tablespace USERS
    temporary tablespace TEMP;
    
grant connect, DBA to earbee;

commit;

    