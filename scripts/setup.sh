#!/bin/bash
DELAY=30
DELAY2=10
echo "****** Waiting for ${DELAY} seconds for replicaset configuration to be applied ******"

sleep $DELAY

mongosh <<EOF
  config = {
    "_id": "rs",
    "members": [{
      "_id": 0,
      "host": "localhost:27017",
      "priority": 2
    }]
  }

  rs.initiate(config, { force: true })
  rs.status();

  exit
EOF

sleep $DELAY2

mongosh <<EOF
  use admin;
  admin = db.getSiblingDB("admin");
  admin.createUser(
    {
	    user: '$MONGO_USERNAME',
      pwd: '$MONGO_PASSWORD',
      roles: [ { role: "root", db: "admin" } ]
    }
  );
  db.getSiblingDB("admin").auth('$MONGO_USERNAME', '$MONGO_PASSWORD');
  rs.status();

  exit
EOF
