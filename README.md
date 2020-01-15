# aspectjlogger
Helps to debug application by adding logs without touching its code. 

It uses AspectJweaver to weave the code at run time. It logs method signature, arguments, return value and exeception thrown.

How are the steps

# Steps

## Step#1
Download all files from artifact folder and keep it in a folder. For demonstration purposes we will keep it in C:/DebugApp folder. 

## Step#2
Change C:/DebugApp/aop.xml to add your point cut expression given here in bold.

- \<pointcut name="scope" expression="**execution(\* org.springframework.samples..\*(..))**" /\>

## Step#3
Add below VM arguments while running your Java Application.

- -Dorg.aspectj.weaver.loadtime.configuration=file:C:/DebugApp/aop.xml -Dlog4j.configuration=file:C:/DebugApp/log4j.properties -javaagent:"C:/DebugApp/aspectlogger-0.0.1.jar"

# Examples:
## EXECUTE JAR FILE
- java -Dorg.aspectj.weaver.loadtime.configuration=file:C:/DebugApp/aop.xml -Dlog4j.configuration=file:C:/DebugApp/log4j.properties -javaagent:"C:/DebugApp/aspectlogger-0.0.1.jar"  -jar target/spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar

## EXECUTE IN IDE OR JUNIT TEST FROM IDE
- In IDE while running Java Main class or JUnit test case, add the below VM arguments.

  -Dorg.aspectj.weaver.loadtime.configuration=file:C:/DebugApp/aop.xml -Dlog4j.configuration=file:C:/DebugApp/log4j.properties -javaagent:"C:/DebugApp/aspectlogger-0.0.1.jar"

## EXECUTE IN SERVERS LIKE TOMCAT
- For applications running on servers like Tomcat, add the VM argument in JAVA_OPTS enviorment variable.
  set JAVA_OPTS="-Dorg.aspectj.weaver.loadtime.configuration=file:C:/DebugApp/aop.xml -Dlog4j.configuration=file:C:/DebugApp/log4j.properties -javaagent:\\"C:/DebugApp/aspectlogger-0.0.1.jar\\""

# SAMPLE LOG OUTPUT
## NO ERRORS
- 2020-01-15 17:37:23 DEBUG root:43 - METHOD: execution(public org.springframework.samples.petclinic.owner.Pet org.springframework.samples.petclinic.owner.Owner.getPet(java.lang.String, boolean)). PARAMETERS: [smally, true]. RETURN VALUE: None

## ERRORS
- 2020-01-15 17:37:44 ERROR root:38 - METHOD: execution(public java.lang.String org.springframework.samples.petclinic.system.CrashController.triggerException()). PARAMETERS:  []. ERROR: Expected: controller used to showcase what happens when an exception is thrown

# BUILD 
- run below maven command from project folder.
- **mvn clean package**
- **aspectlogger-0.0.1.jar** will be in PROJECT_FOLDER/target folder. 
- replace this jar in C:/DebugApp

# NOTE
1. There could be recursion while logging if point cut is too broad. Some effort has been made to avoid recursion. But may not help if point cut is too broad.
2. In aop.xml, you can add exclusions 
3. More information on aop.xml can be found here. https://www.eclipse.org/aspectj/doc/released/devguide/ltw-configuration.html
4. If for some reasons weaving does not work. Add -showWeaveInfo in aop.xml 
\<weaver options="-verbose **-showWeaveInfo**"\>
5. Check file:C:/DebugApp/log4j.properties for log file location and name. It is named as log-myapp.log. It will be in current executing directory.











