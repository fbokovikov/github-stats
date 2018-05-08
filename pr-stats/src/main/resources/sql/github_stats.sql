--login with system/oracle
CREATE USER github_stats IDENTIFIED BY github_stats;

ALTER USER github_stats quota 100M on system;

GRANT CREATE SESSION TO github_stats;

GRANT CREATE TABLE TO github_stats;

CREATE TABLE github_stats.github_branch (
    branch_name VARCHAR2(100 CHAR),
    branch_owner VARCHAR2(40 CHAR),
    sha VARCHAR2(100 char),
    updated_at TIMESTAMP,
    repository_name VARCHAR2(100 CHAR)
);

ALTER TABLE github_stats.github_branch
ADD CONSTRAINT github_branch_pk PRIMARY KEY (branch_name);