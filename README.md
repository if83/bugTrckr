# Running project locally:

1. clone the project;
2. open project in IDE;
3. create manually database _bugtrckr_ (in MySQL-server the command: `CREATE DATABASE IF NOT EXISTS bugtrckr;`);
4. in the file *AgileSoftware/src/main/resources/application.properties* change properties (*jdbc.username* and *jdbc.password*) to your own;
5. run maven goal `mvn clean package` for execute database-scripts and create war-package;
6. run the project in the local server (for example *Tomcat*)


In the Database will be user with admin rights, e-mail: admin@ss.com, and password: admin

### Troubleshooting

   In case a Tomcat will give you a warning like org.apache.catalina.webresources.Cache.getResource Unable to add the resource ... to the cache because there was insufficient free space

   Please, In your *$CATALINA_BASE/conf/context.xml* add block before `</Context>`

   `&lt;Resources cachingAllowed="true" cacheMaxSize="100000" /&gt;`
