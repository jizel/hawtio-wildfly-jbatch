# Standalone Hawtio with JBatch-plugin
### Hawtio-wildfly standalone instance with built-in JBatch plugin for managing JSR352 batch jobs. Optimized for Wildfly 10.

**Requirements:**
 - Running instance of Wildfly 10
 - Maven

 ### Authentication
 - Create user(s) in ApplicationRealm on your server (i.e. via add-user.sh/add-user.bat)
 - There are 3 supported roles - admin (all rights), supervisor(cannot start new jobs) and user(read only). Assign one of these roles to your user.
 - SSO needs to be added to standalone.xml (or standalone-full.xml). Add the line ```<single-sign-on/>``` to undertow subsystem config for localhost so it looks like this:
 
 ```
    <server name="default-server">
      <http-listener name="default" socket-binding="http" redirect-socket="https"/>
        <host name="default-host" alias="localhost">
           <location name="/" handler="welcome-content"/>
           <filter-ref name="server-header"/>
           <filter-ref name="x-powered-by-header"/>
           <single-sign-on/>
        </host>
    </server>
```
 
 - Now you can login to hawtio with your user's credentials and operate Batch jobs according to his user role.
  
 ### Build
 - Build project with *mvn clean install*
 - Wildfly 10 instance for arquillian unit tests is automatically downloaded into *./target* directory (takes about 130 MB of disk space)
 - Deploy the created war on your server (No other Hawt.io instance running on the server!)
 - Java 1.8 is supported


Plugin is discovered automatically by hawtio using Jolokia. REST api with json message format is implemented.
