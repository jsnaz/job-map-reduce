## MapReduce job 

The objective of this project is setting up an Hadoop cluster locally 
on one computer using Docker containers and a MapReduce job to count the number of 
missing value of the Country column.  
Originally I developed the MapReduce application in Python but it appeared that Python was not installed on containers.  
Due to this, I developed the application in Java. 

### Setting up the Hadoop cluster
The Hadoop cluster is set up with Docker containers. The following repository was used for 
the docker-compose configuration:
https://github.com/big-data-europe/docker-hadoop

To run all the involved containers, after cloning the repository, run the following command:
```bash
docker-compose up -d
```
The Hadoop UI is accessible at http://localhost:9870

<img width="100%" alt="Hadoop UI" src="https://user-images.githubusercontent.com/65605546/210663478-8ae8112f-0b74-4ad6-86f6-c3ba043d3a4b.png">

### Prepare and send the files to the Hadoop cluster
#### Compile the Java application
The application should be compilied to generate a jar. That is done with Maven on IntelliJ.  
The jar is generated in the target directory.

#### Send the files
Enter in the Docker container of namenode:
```bash
docker exec -it namenode bash
```

Once in the container, create a tp-map-reduce directory:
```bash
mkdir tp-map-reduce
```

Create a tp-map-reduce in HDFS:
```
hdfs dfs -mkdir /tp-map-reduce
```

Exit the container and send the local files to container:
```bash
exit
docker cp target/map-reduce-tp-1.0-SNAPSHOT.jar namenode:/tp-map-reduce/
docker cp InputData.csv namenode:/tp-map-reduce/
```

Enter the container and send the csv file to HDFS
```bash
hdfs dfs -mkdir /tp-map-reduce
hdfs dfs -put /tp-map-reduce/InputData.csv /tp-map-reduce/InputData.csv
```

### Run the MapReduce application
```bash
hadoop jar tp-map-reduce/map-reduce-tp-1.0-SNAPSHOT.jar Runner /tp-map-reduce/InputData.csv /tp-map-reduce/out
```

The application calculate the total number of missing values for the Country column of the csv file.
Once the calculation is done, the result is written in HDFS in /tp-map-reduce/out

<img width="100%" alt="Capture d’écran 2023-01-04 à 22 12 08" src="https://user-images.githubusercontent.com/65605546/210663713-c914f524-d3c1-406a-a15d-8df9e006e14c.png">

It appears there is no missing values for the Country column. 
<img width="100%" alt="Capture d’écran 2023-01-04 à 22 10 21" src="https://user-images.githubusercontent.com/65605546/210663793-a9710c12-df2e-4919-b2d5-8ee54b288bcd.png">


This also can be verified using Python:
<img width="100%" alt="Capture d’écran 2023-01-04 à 23 44 32" src="https://user-images.githubusercontent.com/65605546/210664091-45a0e29b-4943-4a5a-bb07-738c16468e14.png">


### Total number of characters in the Country column
Given there was no missing values for the Country column and I wanted to see the result of an aggregation using MapReduce I made another MapReduce application that calculate the total number of characters in the Country column.  
In order to achieve that I developed another Mapper (WordCountMapper), the reducer remains the same.  
<img width="100%" alt="Capture d’écran 2023-01-04 à 22 25 42" src="https://user-images.githubusercontent.com/65605546/210664262-2f553a80-fb01-4363-8c1a-9741503fe7c6.png">

