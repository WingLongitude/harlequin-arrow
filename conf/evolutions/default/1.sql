# --- First database schema

# --- !Ups

CREATE TABLE IF NOT EXISTS resource (
  url VARCHAR(255) not null,
  user VARCHAR(255),
  organizationUUID VARCHAR(255),
  country VARCHAR(255),
  status VARCHAR(255),
  statusNotes VARCHAR(255),
  createdAt VARCHAR(255),
  indexStartAt VARCHAR(255),
  indexedAt VARCHAR(255),
  PRIMARY KEY(url)
);

CREATE INDEX user_index ON resource(user);
CREATE INDEX organizationUUID_index ON resource(organizationUUID);
CREATE INDEX country_index ON resource(country);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists public.resource;

SET REFERENTIAL_INTEGRITY TRUE;