# Library Management Application

Restful API thar provides the functionality of all CRUD operations in  a library. 

Features
- Spring boot 2.5.6
- Java 8
- Maven
- H2 DB
- Unit Tests

## Getting Started

This application provides the ability to do all the CRUD operations, search based on any of the fields and import books as a Json list.

## Installing

1. Download the project to a new project
2. mvn clean install
3. Run the application using LibraryPocApplication

## Swagger Path

{hostname}/swagger-ui/

## Testing the API Endpoint

  - Enroll a Book 
    POST http://{hostname}/library/book
    Request Body : 
    json
    
    {
    "isbn": "1234",
    "title": "Alchemist",
    "author": "Poelo Coelho",
    "tags": [
        "motivational",
        "adventure"
    ]
  }
  
  - Retreive a Book 
   GET http://{hostname}/library/book/{isbn}
   
   - Update a Book 
      PATCH http://{hostname}/library/book
      Request Body : 
      json
      
                    {
                    "isbn": "1234",
                    "title": "Alchemist",
                    "author": "Poelo Coelho",
                    "tags": [
                        "motivational",
                        "adventure"
                    ]
                  }
  
  - Delete a Book
      DELETE  http://{hostname}/library/book/{isbn}

  - Search for Books
      POST http://{hostname}/library/book/search
      Request Body : 
      json
      
                    {
                    "isbn": "1234",
                    "title": "Alchemist",
                    "author": "Poelo Coelho",
                    "tags": [
                        "motivational",
                        "adventure"
                    ]
                  }
                  
    - Import Books from Json list
        POST http://{hostname}/library/book/search
        Request Body :
        json
      
      [
                    {
                    "isbn": "1234",
                    "title": "Alchemist",
                    "author": "Poelo Coelho",
                    "tags": [
                        "motivational",
                        "adventure"
                    ]
                  },
                                      {
                    "isbn": "7893",
                    "title": "Monk who sold his Ferrari",
                    "author": "Robin Sharma",
                    "tags": [
                        "motivational"
                    ]
                  }
  ]
