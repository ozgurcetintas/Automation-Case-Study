Feature: As a user, I want to create credentials

  @ValidCase
  Scenario: Successful scenario

    Given Check page title as Meditopia Premium

    Given Successful payment

  @ValidCase
  Scenario: Failed Scenario

    Given Failed payment

  @ValidCase
  Scenario: Mobile safari scenario

    Given Successful mobile payment