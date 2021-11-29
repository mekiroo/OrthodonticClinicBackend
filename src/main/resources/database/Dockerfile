FROM mysql:8.0
ENV MYSQL_ROOT_PASSWORD password
ENV MYSQL_DATABASE orthodontic_clinic_db

COPY init.sql /docker-entrypoint-initdb.d
