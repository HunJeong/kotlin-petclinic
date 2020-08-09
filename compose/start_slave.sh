#!/bin/bash

master_host="master"
master_port=3306
master_user="root"

slave_host="slave"
slave_port=3306
slave_user="root"

# wait master
until echo '\q' | mysql -h"$master_host" -P"$master_port" -u"$master_user" -e"show master status"; do
    >&2 echo "MySQL is unavailable - sleeping"
    sleep 1
done

# wait slave
until echo '\q' | mysql -h"$slave_host" -P"$slave_port" -u"$slave_user" -e"show slave status"; do
    >&2 echo "MySQL is unavailable - sleeping"
    sleep 1
done

log_file=`mysql -h"$master_host" -P"$master_port" -u"$master_user" -e"show master status" | grep -P 'mysql-bin\.[0-9]+' -o`
log_pos=`mysql -h"$master_host" -P"$master_port" -u"$master_user" -e"show master status" | grep -P '\s[0-9]+' -o | grep -P '[0-9]+' -o`

mysql -h"$slave_host" -P"$slave_port" -u"$slave_user" -e "STOP SLAVE;"
mysql -h"$slave_host" -P"$slave_port" -u"$slave_user" -e "RESET SLAVE;"
mysql -h"$slave_host" -P"$slave_port" -u"$slave_user" -e "CHANGE MASTER TO MASTER_HOST=\"$master_host\", MASTER_USER=\"$master_user\", MASTER_LOG_FILE=\"$log_file\", MASTER_PORT=$master_port, MASTER_LOG_POS=$log_pos;"
mysql -h"$slave_host" -P"$slave_port" -u"$slave_user" -e "START SLAVE;"

# CHANGE MASTER TO MASTER_HOST="master", MASTER_USER="repluser", MASTER_PASSWORD="replpw", MASTER_LOG_FILE="mysql-bin.000003", MASTER_PORT=3306, MASTER_LOG_POS=154;
# CHANGE MASTER TO MASTER_HOST="master", MASTER_USER="root", MASTER_LOG_FILE="mysql-bin.000003", MASTER_PORT=3306, MASTER_LOG_POS=154

echo "Slave connected"