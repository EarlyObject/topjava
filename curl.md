#GET ALL MEAL ITEMS FOR LOGGED USER 

curl --location --request GET 'http://localhost:8080/topjava/rest/meals' \
                                   --header 'Content-Type: application/xml'

#GET MEAL ITEM

curl --location --request GET 'http://localhost:8080/topjava/rest/meals/id' \
--header 'Content-Type: application/xml'


#UPDATE MEAL ITEM 

curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/id' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
		"id": 100003,
        "dateTime": "2020-01-30T13:00:00",
        "description": "Обед с другом",
        "calories": 1005
}'


#DELETE MEAL ITEM 

curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/id' \
--header 'Content-Type: application/xml'



#CREATE MEAL ITEM

curl --location --request POST 'http://localhost:8080/topjava/rest/meals' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Content-Type: text/plain' \
--data-raw '{
        "dateTime": "2020-03-30T13:00:01",
        "description": "Tasty cake",
        "calories": 333
}'


#GET LIST OF MEAL ITEMS FILTERED BY DATE AND/OR TIME

curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=&endDate=&startTime=&endTime=' \
--header 'Content-Type: application/xml'