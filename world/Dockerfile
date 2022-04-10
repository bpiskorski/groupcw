# Use the latest MySQL image
# FROM mysql
# COPY world.sql /docker-entrypoint-initdb.d
# ENV MYSQL_ROOT_PASSWORD example

# Use the latest MySQL image
FROM mysql
# Set the working directory
WORKDIR /tmp
# Copy all the files to the working directory of the container
COPY *.sql /tmp/
# COPY world_db/*.dump /tmp/
# Copy the main SQL file to docker-entrypoint-initdb.d.
# Scripts and SQL files in this folder are executed on container startup.
# This is specific to MySQL.
COPY world.sql /docker-entrypoint-initdb.d

# Set the root password
#ENV MYSQL_DATABASE world
ENV MYSQL_ROOT_PASSWORD example
#ENV MYSQL_PASSWORD pass
#ENV MYSQL_USER usr

#ENV image mysql:latest