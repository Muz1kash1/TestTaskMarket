create table products
(
    id                bigserial not null unique,
    product_name      text      not null,
    description       text,
    organisation_name text,
    price             numeric   not null check (price > 0),
    quantity          int       not null check (quantity >= 0),
    keywords          text[],
    chars             text[],
    primary key (id)
);
create table users
(
    id       bigserial not null unique,
    username text      not null unique,
    mail     text      not null unique,
    password text      not null,
    balance  numeric   not null check (balance >= 0) default (0),
    enabled  bool      not null                      default (true),
    roles    text    not null default ('USER'),
    primary key (id)
);
create table discounts
(
    id            bigserial not null unique,
    discount_size float     not null check ( discount_size between 0 and 100),
    duration      date      not null check ( duration >= current_date),
    primary key (id)
);
create table notions
(
    id          bigserial not null unique,
    header      text      not null,
    notion_date date      not null,
    notion_text text,
    primary key (id)
);
create table user_notions
(
    id        bigserial not null unique,
    user_id   bigserial not null,
    notion_id bigserial not null,
    foreign key (user_id) references users (id),
    foreign key (notion_id) references notions (id),
    primary key (id)
);
create table organisations
(
    id                       bigserial not null unique,
    organisation_name        text,
    organisation_description text,
    logotype_id              bigserial,
    enabled                  bool      not null default (false),
    organisation_owner_id    bigserial not null,
    foreign key (organisation_owner_id) references users (id),
    primary key (id)
);
create table product_reviews
(
    id         bigserial not null unique,
    user_id    bigserial not null,
    product_id bigserial not null,
    reviewText text,
    rating     int       not null,
    foreign key (user_id) references users (id),
    primary key (id)
);
create table product_discounts
(
    id          bigserial not null unique,
    product_id  bigserial not null,
    discount_id bigserial not null,
    foreign key (product_id) references products (id),
    foreign key (discount_id) references discounts (id),
    primary key (id)
);
create table purchases
(
    id            bigserial not null unique,
    user_id       bigserial not null,
    product_id    bigserial not null,
    refunded      bool      not null default (false),
    price         numeric   not null check (price >= 0),
    purchase_date date      not null,
    foreign key (user_id) references users (id),
    foreign key (product_id) references products (id),
    primary key (id)
);
create table organisation_products
(
    id              bigserial not null unique,
    organisation_id bigserial not null,
    product_id      bigserial not null,
    foreign key (organisation_id) references organisations (id),
    foreign key (product_id) references products (id),
    primary key (id)
);

