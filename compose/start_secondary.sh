#!/bin/bash

primary_host="primary"
primary_port=3306
primary_user="root"

secondary_host="secondary"
secondary_port=3306
secondary_user="root"

# wait primary
until echo '\q' | mysql -h"$primary_host" -P"$primary_port" -u"$primary_user" -e"show master status"; do
    >&2 echo "MySQL is unavailable - sleeping"
    sleep 1
done

# wait secondary
until echo '\q' | mysql -h"$secondary_host" -P"$secondary_port" -u"$secondary_user" -e"show slave status"; do
    >&2 echo "MySQL is unavailable - sleeping"
    sleep 1
done

log_file=`mysql -h"$primary_host" -P"$primary_port" -u"$primary_user" -e"show master status" | grep -P 'mysql-bin\.[0-9]+' -o`
log_pos=`mysql -h"$primary_host" -P"$primary_port" -u"$primary_user" -e"show master status" | grep -P '\s[0-9]+' -o | grep -P '[0-9]+' -o`

mysql -h"$secondary_host" -P"$secondary_port" -u"$secondary_user" -e "STOP SLAVE;"
mysql -h"$secondary_host" -P"$secondary_port" -u"$secondary_user" -e "RESET SLAVE;"
mysql -h"$secondary_host" -P"$secondary_port" -u"$secondary_user" -e "CHANGE MASTER TO MASTER_HOST=\"$primary_host\", MASTER_USER=\"$primary_user\", MASTER_LOG_FILE=\"$log_file\", MASTER_PORT=$primary_port, MASTER_LOG_POS=$log_pos;"
mysql -h"$secondary_host" -P"$secondary_port" -u"$secondary_user" -e "START SLAVE;"

# CHANGE MASTER TO MASTER_HOST="master", MASTER_USER="repluser", MASTER_PASSWORD="replpw", MASTER_LOG_FILE="mysql-bin.000003", MASTER_PORT=3306, MASTER_LOG_POS=154;
# CHANGE MASTER TO MASTER_HOST="master", MASTER_USER="root", MASTER_LOG_FILE="mysql-bin.000003", MASTER_PORT=3306, MASTER_LOG_POS=154

echo "Secondary connected"