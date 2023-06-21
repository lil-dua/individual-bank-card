# individual-bank-card


Basic individual bank card development by Java. There're 3 main:
  - jcide: folder include source of code java card (this contain logic how to use smart card)
  - netbean: main application that connect with java card to save and send/receive data between use and card
  - database.sql: database format example for data of smart card

How to use?
  1. Preparation before run application
  
    Change default protocol in JCIDE to T = 1
    In JCIDE -> IDE -> IDE Options -> Run/Debugs -> Enable OCSC Interface
    Create database with name: data, maybe you can insert file database.sql (recommemd) or not.
    
  2. Run application

    Make sure you host was enabled before (I'm currently using Xampp Control)
    Open JCIDE and select in folder jcide file project with .proj and run that module first
    Then open netbean or what ever IDE can run Java project, build and run module "bankcard".
    
 Hope you doing well!
