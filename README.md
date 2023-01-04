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
hadoop jar tp-map-reduce/map-reduce-tp-1.0-SNAPSHOT.jar WordCount /tp-map-reduce/InputData.csv /tp-map-reduce/output.txt
```

The application calculate the total number of missing values for the Country column of the csv file.
Once the calculation is done, the result is written in HDFS in /tp-map-reduce/out

It appears there is no missing values for the Country column. This also can be verified using Python:


### Total number of characters in the Country column
Given there was no missing values for the Country column, I made another MapReduce application that
calculate the total number of characters in the Country column. I wanted to see the result of an aggregation using MapReduce.

