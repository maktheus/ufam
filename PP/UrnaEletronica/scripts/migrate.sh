#!/bin/sh

echo "Rodando migrations..."

for file in /migrations/*.sql
do
  echo "Rodando $file..."
  mysql -u myuser -pmypassword -h db mydatabase < "$file"
done

echo "Migrations completadas."
