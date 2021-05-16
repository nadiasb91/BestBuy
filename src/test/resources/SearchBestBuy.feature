Feature:

  Background:
    Given user is the best buy page
    And select a us country

  Scenario Outline: Search a product
    When insert a "<criteria>" on the search box
    And click submit
    Then a list of results must be display

    Examples:
      | criteria   |
      | television |

  Scenario Outline: Negative search a product with the criteria #$%^&*
    When insert a "<criteria>" on the search box
    And click submit
    Then a message must be display with search "<criteria>"

    Examples:
      | criteria |
      | #$%^&*   |


  Scenario Outline: Negative search a product with the criteria t
    When insert a "<criteria>" on the search box
    And click submit
    Then the result display should start with the "<criteria>"

    Examples:
      | criteria |
      | t        |

  Scenario Outline: Check the suggestion from the search t
    When insert a "<criteria>" on the search box
    Then the suggestion must match with the "<criteria>"

    Examples:
      | criteria |
      | t        |


  Scenario Outline: Negative search for an empty criteria
    When insert a "<criteria>" on the search box
    And click submit
    Then nothing should happen

    Examples:
      | criteria |
      |          |

  Scenario Outline: Add filter to a search results
    When insert a "<criteria>" on the search box
    And click submit
    And apply 3 filters to the results
    Then check that the list is updated with the filters

    Examples:
      | criteria   |
      | television |

  Scenario Outline: Open a product
    When insert a "<criteria>" on the search box
    And click submit
    Then open the first result
    And display the first three reviews


    Examples:
      | criteria   |
      | television |