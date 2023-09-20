#!/bin/sh

set -e

# Aguarde até que o MySQL esteja pronto para aceitar conexões.
while ! mysqladmin ping -h"db" --silent; do
    sleep 1
done

# Execute suas migrations
# Aqui estou assumindo que você tem um script chamado migrate.sh para rodar suas migrations.
# Substitua isso pelo comando real que você usa para rodar suas migrations.
sh /scripts/migrate.sh

# Continue com o comando original
exec "$@"
