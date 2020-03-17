# aspectjlogger
Helps to debug application by adding logs without touching its code. 

It uses AspectJweaver to weave the code at run time. It logs method signature, arguments, return value, exeception thrown and execution time.

How are the steps

# Steps

## Step#1
Download all files from artifact folder and keep it in a folder. For demonstration purposes we will keep it in C:/DebugApp folder. 

## Step#2
Change C:/DebugApp/aop.xml to add your point cut expression given here in bold.

- \<pointcut name="scope" expression="**execution(\* org.springframework.samples..\*(..))**" /\>

## Step#3
Add below VM arguments while running your Java Application.

- -Dorg.aspectj.weaver.loadtime.configuration=file:C:/DebugApp/aop.xml  -javaagent:"C:/DebugApp/aspectjcsvlogger-0.0.1.jar"

# Examples:
## EXECUTE JAR FILE
- java -Dorg.aspectj.weaver.loadtime.configuration=file:C:/DebugApp/aop.xml  -javaagent:"C:/DebugApp/aspectjcsvlogger-0.0.1.jar"  -jar target/spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar

## EXECUTE IN IDE OR JUNIT TEST FROM IDE
- In IDE while running Java Main class or JUnit test case, add the below VM arguments.

  -Dorg.aspectj.weaver.loadtime.configuration=file:C:/DebugApp/aop.xml  -javaagent:"C:/DebugApp/aspectjcsvlogger-0.0.1.jar"

## EXECUTE IN SERVERS LIKE TOMCAT
- For applications running on servers like Tomcat, add the VM argument in JAVA_OPTS enviorment variable.
  set JAVA_OPTS="-Dorg.aspectj.weaver.loadtime.configuration=file:C:/DebugApp/aop.xml  -javaagent:\\"C:/DebugApp/aspectjcsvlogger-0.0.1.jar\\""

# SAMPLE LOG OUTPUT
## Log Log Location
- Location of the log will be current directory.
- File name will be myapplog<<datetime>>.csv

## Log fields
- Date time in yyyy-MM-dd HH:mm:ss.SSS format. Example 2020-02-02 12:13:34.450
- Method name in long format. Example  public java.lang.String org.springframework.samples.petclinic.model.Person.getLastName()
- Parameter values
- Return value
- Execution time in millisecond format


# BUILD 
- run below maven command from project folder.
- **mvn clean package**
- **aspectjcsvlogger-0.0.1.jar** will be in PROJECT_FOLDER/target folder. 
- replace this jar in C:/DebugApp

# NOTE
1. There could be recursion while logging if point cut is too broad. Some effort has been made to avoid recursion. But may not help if point cut is too broad.
2. In aop.xml, you can add exclusions 
3. More information on aop.xml can be found here. https://www.eclipse.org/aspectj/doc/released/devguide/ltw-configuration.html
4. If for some reasons weaving does not work. Add -showWeaveInfo in aop.xml 
\<weaver options="-verbose **-showWeaveInfo**"\>
6. Ensure artifacts/aspectjcsvlogger-0.0.1.jar is NOT read only.











