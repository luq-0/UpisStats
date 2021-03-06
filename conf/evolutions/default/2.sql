# --- !Ups

create table os2017 (
  id                            bigserial not null,
  broj_ucenika                  integer,
  ime                           varchar(255),
  mesto                         varchar(255),
  okrug                         varchar(255),
  adresa                        varchar(255),
  telefon                       varchar(255),
  likovno6                      float,
  tehnicko6                     float,
  geografija6                   float,
  biologija6                    float,
  sport6                        float,
  drugi_strani6                 float,
  matematika6                   float,
  istorija6                     float,
  engleski6                     float,
  muzicko6                      float,
  fizicko6                      float,
  vladanje6                     float,
  fizika6                       float,
  srpski6                       float,
  likovno7                      float,
  tehnicko7                     float,
  geografija7                   float,
  biologija7                    float,
  sport7                        float,
  drugi_strani7                 float,
  matematika7                   float,
  istorija7                     float,
  engleski7                     float,
  muzicko7                      float,
  fizicko7                      float,
  vladanje7                     float,
  fizika7                       float,
  srpski7                       float,
  hemija7                       float,
  likovno8                      float,
  tehnicko8                     float,
  geografija8                   float,
  biologija8                    float,
  sport8                        float,
  drugi_strani8                 float,
  matematika8                   float,
  istorija8                     float,
  engleski8                     float,
  muzicko8                      float,
  fizicko8                      float,
  vladanje8                     float,
  fizika8                       float,
  srpski8                       float,
  hemija8                       float,
  likovno_p                     float,
  tehnicko_p                    float,
  geografija_p                  float,
  biologija_p                   float,
  sport_p                       float,
  drugi_strani_p                float,
  matematika_p                  float,
  istorija_p                    float,
  engleski_p                    float,
  muzicko_p                     float,
  fizicko_p                     float,
  vladanje_p                    float,
  fizika_p                      float,
  srpski_p                      float,
  hemija_p                      float,
  prosek_sesti                  float,
  prosek_sedmi                  float,
  prosek_osmi                   float,
  prosek_ukupno                 float,
  matematika                    float,
  srpski                        float,
  kombinovani                   float,
  bodovi_iz_skole               float,
  bodovi_sa_zavrsnog            float,
  bodovi_ukupno                 float,
  bodovi_sa_prijemnog           float,
  opstina                       varchar(255),
  ukupno_ucenika                integer,
  svrsenih_ucenika              integer,
  nesvrsenih_ucenika            integer,
  vukovaca                      integer,
  nagradjenih                   integer,
  svi_bodova6                   float,
  svi_bodova7                   float,
  svi_bodova8                   float,
  svi_prosek6                   float,
  svi_prosek7                   float,
  svi_prosek8                   float,
  svi_bodova_ukupno             float,
  svi_bodova_zavrsni            float,
  svi_bodova_ocene              float,
  svi_prosek_ukupno             float,
  drugi_maternji6               float,
  drugi_maternji7               float,
  drugi_maternji8               float,
  drugi_maternji_p              float,
  constraint pk_os2017 primary key (id)
);

create table smerovi2017 (
  id                            bigserial not null,
  broj_ucenika                  integer,
  sifra                         varchar(255),
  ime                           varchar(255),
  mesto                         varchar(255),
  okrug                         varchar(255),
  smer                          varchar(255),
  podrucje                      varchar(255),
  kvota                         integer,
  likovno6                      float,
  tehnicko6                     float,
  geografija6                   float,
  biologija6                    float,
  sport6                        float,
  drugi_strani6                 float,
  matematika6                   float,
  istorija6                     float,
  engleski6                     float,
  muzicko6                      float,
  fizicko6                      float,
  vladanje6                     float,
  fizika6                       float,
  srpski6                       float,
  likovno7                      float,
  tehnicko7                     float,
  geografija7                   float,
  biologija7                    float,
  sport7                        float,
  drugi_strani7                 float,
  matematika7                   float,
  istorija7                     float,
  engleski7                     float,
  muzicko7                      float,
  fizicko7                      float,
  vladanje7                     float,
  fizika7                       float,
  srpski7                       float,
  hemija7                       float,
  likovno8                      float,
  tehnicko8                     float,
  geografija8                   float,
  biologija8                    float,
  sport8                        float,
  drugi_strani8                 float,
  matematika8                   float,
  istorija8                     float,
  engleski8                     float,
  muzicko8                      float,
  fizicko8                      float,
  vladanje8                     float,
  fizika8                       float,
  srpski8                       float,
  hemija8                       float,
  likovno_p                     float,
  tehnicko_p                    float,
  geografija_p                  float,
  biologija_p                   float,
  sport_p                       float,
  drugi_strani_p                float,
  matematika_p                  float,
  istorija_p                    float,
  engleski_p                    float,
  muzicko_p                     float,
  fizicko_p                     float,
  vladanje_p                    float,
  fizika_p                      float,
  srpski_p                      float,
  hemija_p                      float,
  prosek_sesti                  float,
  prosek_sedmi                  float,
  prosek_osmi                   float,
  prosek_ukupno                 float,
  matematika                    float,
  srpski                        float,
  kombinovani                   float,
  bodovi_iz_skole               float,
  bodovi_sa_zavrsnog            float,
  bodovi_ukupno                 float,
  bodovi_sa_prijemnog           float,
  jezik                         varchar(255),
  trajanje                      integer,
  kvota_umanjenje               integer,
  drugi_maternji6               float,
  drugi_maternji7               float,
  drugi_maternji8               float,
  drugi_maternji_p              float,
  constraint uq_smerovi2017_sifra unique (sifra),
  constraint pk_smerovi2017 primary key (id)
);

create table takmicenja2017 (
  id                            bigserial not null,
  predmet                       varchar(255),
  bodova                        integer,
  mesto                         integer,
  rang                          integer,
  ucenik_id                     bigint,
  constraint uq_takmicenja2017_ucenik_id unique (ucenik_id),
  constraint pk_takmicenja2017 primary key (id)
);

create table ucenik2017 (
id                            bigserial not null,
sifra                         integer,
drugi_strani_jezik            varchar(255),
likovno6                      integer,
tehnicko6                     integer,
geografija6                   integer,
biologija6                    integer,
sport6                        integer,
drugi_strani6                 integer,
matematika6                   integer,
istorija6                     integer,
engleski6                     integer,
muzicko6                      integer,
fizicko6                      integer,
vladanje6                     integer,
fizika6                       integer,
srpski6                       integer,
likovno7                      integer,
tehnicko7                     integer,
geografija7                   integer,
biologija7                    integer,
sport7                        integer,
drugi_strani7                 integer,
matematika7                   integer,
istorija7                     integer,
engleski7                     integer,
muzicko7                      integer,
fizicko7                      integer,
vladanje7                     integer,
fizika7                       integer,
srpski7                       integer,
hemija7                       integer,
likovno8                      integer,
tehnicko8                     integer,
geografija8                   integer,
biologija8                    integer,
sport8                        integer,
drugi_strani8                 integer,
matematika8                   integer,
istorija8                     integer,
engleski8                     integer,
muzicko8                      integer,
fizicko8                      integer,
vladanje8                     integer,
fizika8                       integer,
srpski8                       integer,
hemija8                       integer,
likovno_p                     float,
tehnicko_p                    float,
geografija_p                  float,
biologija_p                   float,
sport_p                       float,
drugi_strani_p                float,
matematika_p                  float,
istorija_p                    float,
engleski_p                    float,
muzicko_p                     float,
fizicko_p                     float,
vladanje_p                    float,
fizika_p                      float,
srpski_p                      float,
hemija_p                      float,
prosek_sesti                  float,
prosek_sedmi                  float,
prosek_osmi                   float,
prosek_ukupno                 float,
matematika                    float,
srpski                        float,
kombinovani                   float,
bodovi_iz_skole               float,
bodovi_sa_zavrsnog            float,
bodovi_ukupno                 float,
bodovi_sa_prijemnog           float,
bodovi_sa_takmicenja          float,
broj_zelja                    integer,
upisana_zelja                 integer,
krug                          integer,
osnovna_id                    bigint,
upisana_id                    bigint,
drugi_maternji6               integer,
drugi_maternji7               integer,
drugi_maternji8               integer,
drugi_maternji_p              float,
constraint uq_ucenik2017_sifra unique (sifra),
constraint pk_ucenik2017 primary key (id)
);

create table lista_zelja2017 (
  id                            bigserial not null,
  smer_id                       bigint,
  ucenik_id                     bigint,
  ispunio_uslov                 boolean,
  constraint pk_lista_zelja2017 primary key (id)
);

alter table takmicenja2017 add constraint fk_takmicenja2017_ucenik_id foreign key (ucenik_id) references ucenik2017 (id) on delete restrict on update restrict;

alter table ucenik2017 add constraint fk_ucenik2017_osnovna_id foreign key (osnovna_id) references os2017 (id) on delete restrict on update restrict;
create index ix_ucenik2017_osnovna_id on ucenik2017 (osnovna_id);

alter table ucenik2017 add constraint fk_ucenik2017_upisana_id foreign key (upisana_id) references smerovi2017 (id) on delete restrict on update restrict;
create index ix_ucenik2017_upisana_id on ucenik2017 (upisana_id);

alter table lista_zelja2017 add constraint fk_lista_zelja2017_smer_id foreign key (smer_id) references smerovi2017 (id) on delete restrict on update restrict;
create index ix_lista_zelja2017_smer_id on lista_zelja2017 (smer_id);

alter table lista_zelja2017 add constraint fk_lista_zelja2017_ucenik_id foreign key (ucenik_id) references ucenik2017 (id) on delete restrict on update restrict;
create index ix_lista_zelja2017_ucenik_id on lista_zelja2017 (ucenik_id);

# --- !Downs

alter table if exists takmicenje2017 drop constraint if exists fk_takmicenje2017_ucenik_id;
alter table if exists ucenik2017 drop constraint if exists fk_ucenik2017_osnovna_id;
drop index if exists ix_ucenik2017_osnovna_id;

alter table if exists ucenik2017 drop constraint if exists fk_ucenik2017_upisana_id;
drop index if exists ix_ucenik2017_upisana_id;

drop table if exists os2017 cascade;
drop table if exists smerovi2017 cascade;
drop table if exists takmicenje2017 cascade;
drop table if exists ucenik2017 cascade;
drop table if exists lista_zelja2017 cascade;
