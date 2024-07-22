create table if not exists ref_metric_name
(
    id   bigint generated always as identity primary key,
    name varchar(255) unique
);

create table if not exists ref_description
(
    id          bigint generated always as identity primary key,
    description varchar(255) unique
);

create table if not exists metric
(
    id                        bigint generated always as identity primary key,
    ref_metric_name_id        bigint references ref_metric_name (id),
    ref_metric_description_id bigint references ref_description (id),
    base_unit                 varchar(255),
    created_at                timestamp without time zone not null
);

create table if not exists sample
(
    id        bigint generated always as identity primary key,
    statistic varchar(255),
    value     float8,
    metric_id bigint references metric (id)
);

create table if not exists ref_available_tag
(
    id  bigint generated always as identity primary key,
    tag varchar(255) unique
);

create table if not exists available_tag
(
    id                   bigint generated always as identity primary key,
    ref_available_tag_id bigint references ref_available_tag (id),
    metric_id            bigint references metric (id)
);

create table if not exists ref_available_tag_value
(
    id    bigint generated always as identity primary key,
    value varchar(255) unique
);

create table if not exists available_tag_value
(
    id                         bigint generated always as identity primary key,
    ref_available_tag_value_id bigint references ref_available_tag_value (id),
    available_tag_id           bigint references available_tag (id)
);

create table if not exists statistic
(
    id                 bigint generated always as identity primary key,
    ref_metric_name_id bigint references ref_metric_name (id),
    start_time         timestamp without time zone not null,
    end_time           timestamp without time zone not null,
    max                float8,
    min                float8,
    average            float8
);

alter table ref_metric_name owner to postgres;
alter table ref_description owner to postgres;
alter table metric owner to postgres;
alter table sample owner to postgres;
alter table ref_available_tag owner to postgres;
alter table available_tag owner to postgres;
alter table ref_available_tag_value owner to postgres;
alter table available_tag_value owner to postgres;
alter table statistic owner to postgres;