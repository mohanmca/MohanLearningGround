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


## What is reserved margin
1. Margin required to support current open orders (spot or derivative)
1. Included in Initial Margin

## What is Initial Margin
1. Reserved Margin + Initial margin positions
1. The amount of margin required to open a position

## Margin Maintenance limit
1. Minimum amount of margin assets value required to prevent liquidation

## Margin Assets Value
1. The total USD equivalent value of all eligible assets available for margin
2. Adjusted for any haircut + unrealized p&l - capital in hold for open spot orders
3. MAV = Sum(hair-cut adjusted assets) + sum (p&l) - sum (capital to support open orders)

## What is available margin
1. AvailableMargin = MAV - IM

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


## Generate ANKI
* mdanki crypto_trading.md CrptoTrading_Anki.apkg --deck "Mohan::Trading::Crypto"
