#!/bin/bash
#
# Copyright (C) 2022 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Verify bash version. macOS comes with bash 3 preinstalled.
if [[ ${BASH_VERSINFO[0]} -lt 4 ]]
then
  echo "You need at least bash 4 to run this script."
  exit 1
fi

# exit when any command fails
set -e

if [[ $# -lt 2 ]]; then
   echo "Usage: bash customizer.sh my.new.package [ApplicationName] [AppName]" >&2
   exit 2
fi

PACKAGE=$1
APPLICATION_NAME=$2
APP_NAME=$3
SUBDIR=${PACKAGE//.//} # Replaces . with /

for n in $(find . -type d \( -path '*/src/androidTest' -or -path '*/src/main' -or -path '*/src/test' \) )
do
  echo "Creating $n/java/$SUBDIR"
  mkdir -p $n/java/$SUBDIR
  echo "Moving files to $n/java/$SUBDIR"
  mv $n/java/android/gpillaca/upcomingmovies/* $n/java/$SUBDIR
  echo "Removing old $n/java/android/gpillaca/upcomingmovies"
  rm -rf mv $n/java/android
done

# Rename package and imports
echo "Renaming packages to $PACKAGE"
find ./ -type f -name "*.kt" -exec sed -i.bak "s/package android.gpillaca.upcomingmovies/package $PACKAGE/g" {} \;
find ./ -type f -name "*.kt" -exec sed -i.bak "s/import android.gpillaca.upcomingmovies/import $PACKAGE/g" {} \;

# Rename package in .xml
find ./ -type f -name "*.xml" -exec sed -i.bak "s/android.gpillaca.upcomingmovies/$PACKAGE/g" {} \;

# Gradle files
find ./ -type f -name "*.kts" -exec sed -i.bak "s/android.gpillaca.upcomingmovies/$PACKAGE/g" {} \;

echo "Cleaning up"
find . -name "*.bak" -type f -delete

# Rename Application
if [[ $APPLICATION_NAME ]]
then
    echo "Renaming application to $APPLICATION_NAME"
    find ./ -type f \( -name "AppUpComingMovies.kt" -or -name "*.xml" \) -exec sed -i.bak "s/AppUpComingMovies/$APPLICATION_NAME/g" {} \;
    find ./ -name "AppUpComingMovies.kt" | sed "p;s/AppUpComingMovies/$APPLICATION_NAME/" | tr '\n' '\0' | xargs -0 -n 2 mv

    find . -name "*.bak" -type f -delete
fi

# Rename app & rootProject.name
if [[ $APP_NAME ]]
then
   echo "Renaming rootProject.name to $APP_NAME"
      find ./ -type f \( -name "settings.gradle.kts" \) -exec sed -i.bak "s/UpcomingMovies/$APP_NAME/g" {} \;
      find ./ -type f -name "strings.xml" -exec sed -i.bak "s/UpcomingMovies/$APP_NAME/g" {} \;

      find . -name "*.bak" -type f -delete
fi

# Remove additional files
echo "Removing additional files"
rm -rf CONTRIBUTING.md LICENSE README.md customizer.sh
rm -rf screenshot/
rm -rf .git/
echo "Done!"
