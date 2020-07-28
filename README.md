# Rabo-Statement-Processor

Logic used

Filtering the duplicate References: Tried to add the refrences in a set which doesn't allow me to add any, if there is reference already present in it. These records has been extracted to a seperate list as duplicate refrences and assinged the repective status.

End Balance Mismatch records: Added the start balance with the mutation to get a sum which is compared against the end balance received. All the records which are mismateched are extracted to a list and assinged the repective status.


To Run the program (In Eclipse or STS):

1. Open the IDE and right click in the package explorer and select Import

2. Under Select wizard, select 'Check out Maven Projects from SCM' under Maven.

3. In the Traget location wizard, proivde the URL: https://github.com/vardhan1192/Rabo-Statement-Processor.git and click finish 

4. Select the project which is listed and clcik finish.

5. Once the project is imported, right click on the project select Run As -> Spring Boot App
