* Collateral Management
  * Agreement setup
  * Collateral allocation
  * Margin Call
  * Confirmations
  * Inventory management
  * Dispute management
  * Data management
  * Portofolio reoncilliations
  * Interest
* Collateral is one way to mitigate credit risk
* Credit risk exists when counterparty has an obligation to make payments or deliveries in future
* First leg - Initiation of Collateral Agreement
	* Borrower offers securities as collateral
	* Lender - lends cash
* Second leg - Termination of Collateral Agreement
	* Borrower - returns cash + interest
	* Lender - hands over collateralized securities
* OTC derivatives should be centrally cleared and reported using CCP (Central counter party)
* Collateral advantages
  * Reduced credit risk
  * Capital savings (risk weighted assets)
  * Access to higher risk trades
  * More efficient trading between counterparties
  * More STP transactions without hassle of credit review and settlements
* Collateral disadvantages
  * Increases operational risk - false sense of security
  * Increases the contract cost
    * Legal procedure, Perfection risk, review risk, enforcement risk, priority risk, recharacterisation risk
  * Settlement risk, Liquidity risk
  * Concentration risk
  * Can reduce trading activity
  * Pricing risk and model risk
  * Expensive  
* Collateral Eligibility
  * Liquid (High liquid and marketable)
  * Easy to settle (Treasury bonds, AAA corporate bonds, large-cap equities, and many mortgage-backed bonds)
  * High quality (Investment grade - S&P AAA-BBB, Moddy's Aaa-Baa)
* Collateral currency priority - USD, EUR, GBP, JPY and others
* As of 2013, Bonds that were collateral
  * US Govt bonds - 33%  
  * JPY Govt bonds - 22%  
  * EUR Govt bonds - 19%  
* As of 2013, Most prefered collateral
  * Govt bonds - 25%
  * Corporate bonds - 27%
  * Equities - 25%
  * LC - 5%
* Enterprise view of collateral: ![Enterprise view of collateral][ev]
* Exposure management
  * Derived from Collateral agreement
  * Trade recognition, Trades allocation to agreements, exposure netting across portfolio
  * Who collects dividend of the equities that was given as Collateral
    * Who pays tax for dividend? should we handover the equity and transfer to other security to avoid tax
* Collateral Management
  * Trades that has credit risk alone requires Collateral
  * When to place margin call defined in agreements
  * Netting across portfolio for a counterparty may be allowed
* Collateral
  * May be deposited to 3rd party custodian
  * Cleared by CCP 
* Agreements  
  * Master agreement - ISDA 
  * CSA
  * GMRA for repo, MSFTA, reinsurance agreements 
* Collateral management front to bank: ![Collateral management front to bank][cmf2b]
* CSA
  * Margin call - A request type to post additional collateral 
  * Initial Margin 
  * Variation Margin -  when underlying trade value changes, any of the both the party has to pay to cover the gain
  * Market to Market - CCP does this
  * Mark to Model - Finding intrisic value using some model to find if we are ITM/OTM (Black Scholes)
  * Haircut - reduce the value from the mark to market (it is preagreed %) or reduce the value of underlying asset
  * Threashold Amount - Amount of credit risk that parties ready to accept
  * Substitution - Replace collateral with other collateral (Divident avoid for tax)
  * Rehypothecation - Allowing collateral to be traded in the market, but we need by and return collateral later
  * CCP - (Hypothecation) Buyer for seller, seller for buyer
  * Netting
  * Minimum transfer amount - Lowest transaction of collateral transanction
  * Independent Amount - The amount of collateral required over and above the mark to market of a portfolio. It is designed to cater for changes in the market value of a portfolio between margin calls
  * Valuations and Timings
    * Valuation Agent, Date, Time and Notification Time
  * Termination Events
    * Illegality, Tax Event, Tax Event upon Merger, Credit Event Upong Merger
* When there is dispute
  * What is disputed?
  * What is not disputed that should be settled  

[ev]: img/EnterpriseCM.PNG "Enterprise View"  
[cmf2b]: img/CMF2B.PNG "Collateral Management Front to Bank"  

# References
* [Collateral Management Part 1 Basics] (https://www.youtube.com/watch?v=zt4WQqu1qqQ)
* [Investment Grade](https://www.google.com/search?tbm=isch&q=investment+grade)
* [Bond Rating](https://www.google.com/search?tbm=isch&q=bond+rating)
* [Calypso Workflows & Messaging](http://www2.calypso.com/Portals/0/Documents/Brochures/Calypso-Collateral-Management.pdf)
* [AcadiaSoft Hub](http://www.acadiasoft.com/wp-content/uploads/2017/04/Protocoll-Fact-Sheet-Final.pdf)
* AcadiaSoft, Inc. is a provider of margin automation for counterparties engaged in collateral management worldwide.
