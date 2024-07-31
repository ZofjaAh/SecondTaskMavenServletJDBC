SELECT  'CREATE DATABASE maven_servlet_jdbc'
WHERE  NOT  EXISTS ( SELECT  FROM pg_database WHERE datname = 'maven_servlet_jdbc' )\gexec