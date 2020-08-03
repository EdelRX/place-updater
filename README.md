###### **Place Updater**

I have completed this exercise as first step of the remote coding challenge sent to me by VAKT  part of their recruitment process for the Mid-Senior Java Developer position. 

First of all, I'd like to thank VAKT for giving me this opportunity. I am very interested in the position and am happy to be considered.

The exercise introductory description read as follows:

A company receives data in csv format from a vendor every 2 weeks, this csv contains full set of the data, there is no representation in the file if a record has changed, not changed, added or deleted. 
Company has a another csv that contains data in its own format. 

Purpose of this exercise to write a Java program ideally using spring boot to create company's copy of csv to match the changes that vendor has done. 

Output csv always contains full set of data i.e. unchanged and changed. To limit the scope of this exercise any addition or deletion of rows in vendor csv can be ignored.


###### **Assumptions**

I have created a full Spring Boot microservice written in Java programming language serving under a REST controller interface. To make the data handling easier and even more realistic I have added an embedded H2 database. Company's initial data is loaded onto the database upon the application's initialization. A set of unit tests have also been included, asserting a total line coverage of 80%, method coverage of an 83%, and class coverage of a 75%.

I encountered several issues while developing the required features which I promptly made assumptions to resolve, as follows:

- The exercise description read as "UNLOCCODE without any spaces is to be used to match the records for comparison, if UNLOCCODE is missing then Place name is to be used for comparison." However, I found that the UNLOCCODE field wasn't always unique for each individual record. Meaning that a vendor could provide a certain UNLOCCODE value that would match against several distinct records making it difficult to know which one is being referred to. Thus I made the search by "UNLOCCODE" and "Place Name"  for record matching if UNLOCCODE is provided assuming that "Place Name" is always provided.

-Regarding the fields which were to be considered for changes, the exercise read as follows: "If any records has changes like vendor_id missing, latitude changed or place name changed then that record in output csv is to marked is_active false and a new record is to be added". I found that the field "latitude" sent to us by the vendor is not being stored in the company's internal records, i.e, it's not present in the "company-place.csv" file. These are the fields contined in the "company-place.csv" file: "id,name,is_active,created_at,updated_at,UNLOCODE,place_identity_id,vendor_place_id". No latitude field is to be found, thus it cannot be considered for changes. I then allowed for changes in the "name" field instead, provided to us as "Place Name" by the vendor. As a result, the following fields are considered for change with the vendor data input: "UNLOCCODE, Place ID, Place Name" instead of "UNLOCCODE, Place ID, Latitude".

###### **Instructions to run**

The program provided is a Spring Boot application running on port 28094, so in order to run it you need to place your terminal window in the folder containing the project and run the command "mvn spring-boot:run". Another option is to run the application directly in your IDE by creating the appropiate run configurations.

The REST controller is receiving the vendor data directly through it's csv file. The endpoint URL is as follows: http://localhost:28094//interview/placeupdater/update

To send the CSV file as the request body, the request body type must be selected to "form-data" and the full "vendor-place.csv" file must be included under the key "vendor-file".

I used POSTMAN to test the controller interface, so here's how it was configured:



The interface will return the full "output-place.csv" file for download, but the "Send and Download" option must be selected for the request:


Otherwise POSTMAN will draw the csv file's content at the request response section.



