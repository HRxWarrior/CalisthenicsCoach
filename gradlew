#!/bin/sh
# Gradle start up script for POSIX
app_path=$0
while
    APP_HOME=${app_path%"${app_path##*/}"}
    [ -h "$app_path" ]
do
    ls=$( ls -ld -- "$app_path" )
    link=${ls#*' -> '}
    case $link in
      /*)   app_path=$link ;;
      *)    app_path=$APP_HOME$link ;;
    esac
done
APP_BASE_NAME=${0##*/}
APP_HOME=$( cd "${APP_HOME:-./}" > /dev/null && pwd -P ) || exit
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then JAVACMD=$JAVA_HOME/jre/sh/java
    else JAVACMD=$JAVA_HOME/bin/java; fi
    if [ ! -x "$JAVACMD" ] ; then die "ERROR: JAVA_HOME invalid: $JAVA_HOME"; fi
else
    JAVACMD=java
    if ! command -v java >/dev/null 2>&1; then die "ERROR: JAVA_HOME not set and java not found"; fi
fi
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
if "$cygwin" || "$msys" 2>/dev/null; then APP_HOME=$( cygpath --path --mixed "$APP_HOME" ); CLASSPATH=$( cygpath --path --mixed "$CLASSPATH" ); fi
set -- "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
eval "set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS \"\$@\""
exec "$JAVACMD" "$@"
