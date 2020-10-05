# Sample Kotlin Gradle project

Interview exercise
You are working for Crypto Inc. and we would like you to create a program to show the top 10 BUY or SELL orders in the Crypto Inc. marketplace.
The Crypto Inc. 'Live Order Board' should support these features:

1. Placing an order. An order can be either a BUY or a SELL and should capture
    - user id
    - coin type (Litecoin, Ethereum.. etc)
    - order quantity (how many coins)
    - price per coin (e.g.: £125)
    
1. Cancel a registered order - this will remove the order from 'Live Order Board'

1. Get summary information of live orders (see explanation below)

Imagine we have received the following orders:
    - a) SELL: 350.1 Ethereum @ £13.6 [user1]
    - b) SELL: 50.5 for £14 [user2]
    - c) SELL: 441.8 for £13.9 [user3]
    - d) SELL: 3.5 for £13.6 [user4]

Our ‘Live Order Board’ should provide us the following summary information:
    - 353.6 for £13.6 // order a + order d
    - 441.8 for £13.9 // order c
    - 50.5 for £14 // order b

The first thing to note here is that orders for the same price should be merged together (even when they are from different users). In this case it can be seen that order a) and d) were for the same price (£13.6) and this is why only their sum (353.6) is displayed (for £13.6) and not the individual orders (350.1 and 3.5).The last thing to note is that for SELL orders the orders with lowest prices are displayed first. Opposite is true for the BUY orders.

Please provide the implementation of the live order board which will be packaged and shipped as a library to be used by the UI team. No database or UI/WEB is needed for this assignment (we're absolutely fine with in memory solution). The only important thing is that you just write it according to your normal standards.

NOTE: if during your implementation you'll find that something could be designed in multiple different ways, just implement the one which seems most reasonable to you and if you could provide a short (once sentence) reasoning why you choose this way and not another one, it would be great.


## Prerequisites
- Java 8 or above
- Gradle 5 (for Kotlin DSL support)

## IntelliJ Configuration

### Set up the project (using IntelliJ IDEA)
1. Start IntelliJ IDEA.
1. From the File menu select 'New' and then 'Project from existing sources'.
1. Navigate to the root directory of the project and click 'Open'.
1. If not already chosen, select 'Import project from external model' and choose Gradle - then click 'Next'.
1. Tick the box next to 'Use auto-import' and click 'Finish'.

This last step ensures that when dependencies are changed in `build.gradle.kts`, IntelliJ will automatically update the project.

### Set up the code style

This ensures that we all have the same formatting rules (especially the line length and import settings, which match our detekt configuration):

1. Open IntelliJ's preferences dialogue.
1. Expand 'Editor' and click on 'Code Style'.
1. On the right of the 'Scheme' selected click on the cog.
1. Choose 'Import Scheme' and then 'IntelliJ IDEA code style XML'.
1. Browse to the 'intellij' directory and double click on `KotlinCodeStyle.xml`.
1. In the Import Scheme dialogue tick the box next to 'Current scheme' and then click 'OK'.
1. Click the 'Ok' or 'Apply' button.


## Run unit tests

```bash
./gradlew test
```

## Build 

```bash
./gradlew build
```

## Run

```bash
./gradlew run
```

## Build extensions

### Spring Dependency

Allows grouped versions of dependencies to be defined, so that dependencies do not have to redefine/repeat version 
numbers.

[Spring Dependency Plugin documentation](https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/)


### Detekt

Runs static code analysis (and formatting) to provide basic code quality assurance.

```bash
./gradlew detekt
```

[Detekt Plugin documentation](https://arturbosch.github.io/detekt/kotlindsl.html) 
 