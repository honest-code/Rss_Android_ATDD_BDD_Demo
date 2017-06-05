#@android
Feature: Filter news by user input
  as an user
  I want to see a list of news filter by input

  Scenario: Show a list of filtered news
    Given User is on news list and data is ok
    When user input a founded key string in news list search box
    Then a list of news filtered by input

  Scenario: Show a list of filtered news
    Given User is on news list and data is ok
    When user input an not found key string in news list search box
    Then an empty news list is showed

#  Scenario: Show a list of filtered news
#    Given User is on news list
#    When input a string in search
#    Then a list of news
