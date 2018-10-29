#!/bin/sh

echo "The application will start in ${MYSERIES_SLEEP}s..." && sleep ${MYSERIES_SLEEP}
exec java -jar "${HOME}/app.jar" "$@"
