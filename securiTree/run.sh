#! /bin/bash

mvn package && 
  mvn exec:java -Dexec.mainClass=com.epi_use.app.securiTree