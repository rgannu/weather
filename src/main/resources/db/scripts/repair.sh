#!/usr/bin/env bash

source "$(dirname "${0}")/../conf/database.conf"

DIR_MIGRATION="$(dirname "${0}")"

/opt/unifly/persistence/bin/flyway.sh \
    -url=jdbc:postgresql://$DB_HOST/$DB_DB \
    -user=$DB_MIGRATE_USER \
    -password=$DB_MIGRATE_PASSWORD \
    -schemas=$DB_SCHEMA \
    -placeholders.db.owner=$DB_USER \
    -placeholders.db.user=$DB_USER \
    -table=schema_version \
    -locations=filesystem:$DIR_MIGRATION repair

exit $?
