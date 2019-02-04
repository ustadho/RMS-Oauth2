create table c_authority(
    id  varchar(36) not null,
    description    varchar(255) not null,
    name    varchar(255) not null,
    primary key(id),
    unique (name)
);

create table c_role(
    id          varchar(36) not null,
    name        varchar(255) not null,
    description varchar(255) not null,
    primary key(id),
    unique (name)
);

create table c_role_authority(
    role_id     varchar(36),
    authority_id   varchar(255),
    primary key(role_id, authority_id),
    foreign key(authority_id) references c_authority(id),
    foreign key(role_id) references c_role(id)
);

create table c_user(
    id  varchar(36),
    username    varchar(255),
    active      boolean not null,
    role_id     varchar(255) not null,
    primary key(id),
    unique (username)
);

create table c_user_password(
    user_id     varchar(36) not null,
    password    varchar(255) not null,
    primary key(user_id),
    foreign key(user_id) references c_user(id)
);

insert into c_authority(id, name, description) values
('CP011', 'USER_VIEW', 'View User List'),
('CP012', 'USER_ADD', 'Adding User'),
('CP013', 'USER_EDIT', 'Edit User'),
('CP014', 'USER_DELETE', 'Delete User'),
('CP021', 'PRODUCT_VIEW', 'View Product List'),
('CP022', 'PRODUCT_ADD', 'Adding Product'),
('CP023', 'PRODUCT_EDIT', 'Edit Product'),
('CP024', 'PRODUCT_DELETE', 'Delete Product');

insert into c_role(id, name, description) values
('ADMIN', 'ADMIN', 'Administrator'),
('MANAGER', 'MANAGER', 'Manager'),
('STAFF', 'STAFF', 'Staff');

insert into c_role_authority(role_id, authority_id) values
('ADMIN', 'CP011'), ('ADMIN', 'CP012'), ('ADMIN', 'CP013'), ('ADMIN', 'CP014'),
('ADMIN', 'CP021'), ('ADMIN', 'CP022'), ('ADMIN', 'CP023'), ('ADMIN', 'CP024'),
('MANAGER', 'CP021'), ('MANAGER', 'CP022'), ('MANAGER', 'CP023'), ('MANAGER', 'CP024'),
('STAFF', 'CP021');

insert into c_user(id, username, active, role_id) values
('U01', 'admin', true, 'ADMIN'),
('U02', 'manager', true, 'MANAGER'),
('U03', 'staff', true, 'STAFF');

insert into c_user_password(user_id, password) values
('U01', '$2y$12$CsmwMqUtM7aMmicV8//7tu6Rik0JTAje0yNw0nmaTyAfItkXlnye.'),
('U02', '$2y$12$CsmwMqUtM7aMmicV8//7tu6Rik0JTAje0yNw0nmaTyAfItkXlnye.'),
('U03', '$2y$12$CsmwMqUtM7aMmicV8//7tu6Rik0JTAje0yNw0nmaTyAfItkXlnye.');