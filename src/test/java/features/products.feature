Feature: Product API

  Scenario: Get All Products List
    Given The API endpoint is available
    When Send GET request to productsList
    And Should receive the list of all products
    Then The status code should be 200