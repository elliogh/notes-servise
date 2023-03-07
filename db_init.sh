#!/bin/bash

createdb notesdb
createuser postgres --password postgres
psql -c "GRANT ALL PRIVILEGES ON DATABASE notesdb TO postgres;"
