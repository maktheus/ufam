# Usar a imagem oficial do MySQL
FROM mysql:latest

# Configuração de variáveis de ambiente para MySQL
ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_DATABASE=mydatabase
ENV MYSQL_USER=myuser
ENV MYSQL_PASSWORD=mypassword

# Copiar scripts de inicialização (opcional)
# COPY ./scripts/ /docker-entrypoint-initdb.d/

# Expor a porta 3306 para permitir a conexão
EXPOSE 3306
