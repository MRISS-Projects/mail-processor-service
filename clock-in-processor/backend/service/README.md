# Mail Processor Service Backend Service

## Configuration for Command Line Deployment

1. Include the following at user `settings.xml`, or add `properties`  and `repositories`  sections in a default
   profile, if it already exists.

```xml
<profile>
    <id>development</id>
    <activation>
        <activeByDefault>true</activeByDefault>
    </activation>
    <properties>
        <!-- email account name (the name before @) allowed to submit mail messages -->
        <allowed.account>[MY_ALLOWED_ACCOUNT]</allowed.account>

        <!-- comma separated list of full email addresses allowed as senders -->
        <allowed.sender.list>[ALLOWED_SENDERS_LIST]</allowed.sender.list>

        <!-- google cloud project id -->
        <google.appengine.project>[GCLOUD_PROJECT_ID]</google.appengine.project>
    </properties>
    <repositories>
        <repository>
            <id>MRISS-Projects-maven-repo</id>
            <url>https://raw.githubusercontent.com/MRISS-Projects/maven-repo/master</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>MRISS-Projects-maven-repo-plugins</id>
            <url>https://raw.githubusercontent.com/MRISS-Projects/maven-repo/master</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>
</profile>
```

2. Run `mvn clean install exec:exec`

3. Host machine should be configured with a gcloud account with permissions to admin google appengine (add/create
   services). The selected account can be seen using `gcloud config list account`.