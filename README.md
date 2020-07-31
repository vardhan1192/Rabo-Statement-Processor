# Rabo-Statement-Processor

Logic used

Filtering the duplicate References: Tried to add the refrences in a set which doesn't allow me to add any, if there is reference already present in it. These records has been extracted to a seperate list as duplicate refrences and assinged the repective status.

End Balance Mismatch records: Added the start balance with the mutation to get a sum which is compared against the end balance received. All the records which are mismateched are extracted to a list and assinged the repective status.


To Run the program (In Eclipse or STS):

1. Open the IDE and right click in the package explorer and select Import

2. In the Import wizard, under Git select 'Projects from Git(Smart Import)'.

3. Under Select Repository Source, select 'Clone URI'.

4. Provide the URL: https://github.com/vardhan1192/Rabo-Statement-Processor.git in the URI field and click next to see the list of branches available.

5. Select the master branch and click next to configure the local storage location for the project.

6. In the import projects wizard, select the project which is shown 'Import as Maven' and click finish.

7. Once the project is imported, right click on the porject and select Run As -> Spring Boot App

8. As the application starts, hit a POST request using the URL: http://localhost:8080/processStatement/transactions from the Postman using the reference data provided in the record.json file

9. Data can be manipulated for different situations.


Note:

Since the SSL hasn't been configured, please use HTTP when hitting the requests




