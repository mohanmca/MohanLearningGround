## What is Mark Price
* Mark price is a reference price used in derivatives markets
* It is designed to prevent market manipulation and ensure that the contract's trading price closely tracks the underlying asset's spot price.
* The mark price is calculated using a weighted average of the recent trading prices on the exchange or multiple exchanges, depending on the specific platform.

## Purpose of the mark price - Funding rate
* One primary purpose of the mark price is to determine the funding rate in perpetual swap contracts.
* The funding rate is a mechanism used to align the contract's price with the underlying spot market.
* If the contract is trading at a premium to the mark price, long positions pay a funding fee to short positions, and vice versa.
* This mechanism helps prevent significant divergences between the contract price and the spot price.

## Why Perpetual Contract?
1. Leverage - 2x, 100x
2. Liquidity - avoid from ownership transfer
3. Speculation and Hedging
4. 24/7 Trading
5. Accessibility

## What is Initial Margin
1. Reserved Margin + Initial margin positions
1. The amount of margin required to open a position

## What is reserved margin
1. Margin required to support current open orders (spot or derivative)
1. Included in Initial Margin

## Margin Assets Value
1. The total USD equivalent value of all eligible assets available for margin
2. Adjusted for any haircut + unrealized p&l - capital in hold for open spot orders
3. MAV = Sum(hair-cut adjusted assets) + sum (p&l) - sum (capital to support open orders)

## What is available margin
1. AvailableMargin = MAV - IM

## Margin Maintenance limit
1. Minimum amount of margin assets value required to prevent liquidation
2. The maintenance margin limit is typically expressed as a percentage of the initial margin or the position size

## What is Leverage
1. Ratio of Notional Value to Margin Assets Value

## What is notional value
1. Notional position size * Mark Price

## Funding Rate -Ve
1. Price of perpetual swap is less than spot price
2. Short position holders will pay the long position holders

## Funding Rate +Ve
1. Price of perpetual swap is more than spot price
2. Long position holders will pay the short position holders

## When does liquidation happens?
1. Make MAV < MM

## Steps to force liquidation
1. Balance + Realized Profit - MML -1$
2. You can debit the account using admin debit

## What is the index and how is it used?
1. Gemini uses an index provided by crypto index calculator Kaiko in order to determine an independent market price of crypto assets based on other platforms.
2. The index is used to add stability to the Gemini Mark Price.
3. The Mark Price is based on the Median (Best Bid, Best Ask, Last Trade) and then bound by the Index.
4. This bound is currently set at +/-0.05%.
5. The bound prevents the Mark Price from being moved too far away from the common market price.
6. This ensures that accounts are not impacted by peaks and troughs in the Gemini price that are not reflected on platforms elsewhere.

## How do I calculate my profit and loss (P&L)?

1. Profit and loss is based on an average price model.
2. Every trade that contributes to a current position is used to calculate the average price.
3. This is then compared to the current Mark Price in order to determine the current unrealised P&L.
4. As such, if the position were to be completely closed at the Mark Price, the unrealised P&L would become realized P&L, less any applicable trading fees.

## How is the Funding Amount calculated?
1. Funding Amount = TWAP(Perpetual Price - Spot Price) / 24
2. Perpetual = Median(Best Bid, Best Ask, Last) of the Perpetuals order book for each 1 min during the 60 min funding period.
3. _____Spot = Median(Best Bid, Best Ask, Last) of the Spot order book for each 1 min during the 60 min funding period.
4. If the Funding Amount  is > 0 then the Long Holders pay Short Holders.
5. Funding Payment = Position_size * Funding Amount
6. Position_size is the size of the position in units (not notional)

## How is TWAP is used in Funding Amount
1. TWAP(Perpetual Price - Spot Price) is calculated by taking an average of {(Open(Perpetual) - Open(Spot)), High(Perpetual) - High(Spot), Low(Perpetual) - Low(Spot), Close(Perpetual) - Close(Spot))} for each 1 min and then taking the average of the 60 1 min bars in each 1 hour.
2. TWAP = Time-Weighted Average Price


## How does the funding mechanism work?
1. Perpetuals use a funding mechanism to help ensure that the perpetual price remains tied to the spot price.
2. The Funding Payment is the Funding Amount multiplied by the position size. The time to the next Funding Payment and the Expected Funding Amount are both displayed in the Market Data panel in ActiveTrader.
3. The Funding Amount is calculated using the Time-Weighted Average Price (TWAP) of the Perpetual Price less the Spot Price. This value is then divided by 24 due to the Funding Payment being made every hour. For a more detailed explanation on the calculation.
4. The Funding Amount is capped at 0.XX% * Future Mark Price taken at the end of the funding window.

## How does the funding hourly frequency works?
1. Funding payments are calculated and paid hourly. The funding period starts in the first minute in the hour until the end of the last minute in the hour.
2. The time to the next Funding Payment and the Expected Funding Amount are both displayed in the Market Data panel in ActiveTrader. Payments will be made and received in GUSD.


## Reference
1. [Gemini Documentation](https://support.gemini.com/hc/en-us/sections/14828346584987-Trading-Derivatives)

## Generate ANKI
* mdanki crypto_trading.md CrptoTrading_Anki.apkg --deck "Mohan::Trading::Crypto"
