## How many kinds of requsts are there?
1. Private
2. Public
    1. Accessible via GET
    2. The parameters for the request are included in the query string.
3. Domain wise
   1. Order placement
   2. Order status
   3. Account Status

## API Keys
1. "API Key" that will serve as your user name
1. "API Secret" that you will use to sign messages.

## Nounce based request
1. Monotonically increasing nounce
1. Only once it should be used to avoid record and play-back
1. Time based nonce. 
   1. Nonce has to be within +/- 30 seconds of Unix Epoch timestamp to be deemed valid.
1. Non-Time-based
   1. Should keep increasing   
1. We recommend using a timestamp at millisecond or higher precision.   

## Session
1. Multiple API-keys can be generated for multiple session
1. Cancel-All-Session-Orders can cancel all the orders of that session
1. The nonce associated with a request needs to be increasing with respect to the session that the nonce is used on.

## Requires Heartbeat
1. When provisioning a session key you have the option of marking the session as "Requires Heartbeat".
1. If connectivity to the exchange is lost for any reason, then all outstanding orders on this session should be canceled.
1. To maintain the session, the trading system should send a heartbeat message at a more frequent interval. We suggest at most 15 seconds between heartbeats.
1. The heartbeat message is provided for convenience when there is no trading activity. 
1. Any authenticated API call will reset the 30 second clock, even if explicit heartbeats are not sent.

## How to avoid 2FA in sandbox
1. set cookie - GEMINI-SANDBOX-2FA: 0000000 (7 digit any number)

## How are rate limits applied?

1. If 10 reuqests are valid for 2 second, If you send 20 requests in close succession over two seconds, then you could expec
1. the first ten requests are processed
1. the next five requests are queued
1. the next five requests receive a 429 response, meaning the rate limit for this group of endpoints has been exceeded


## How to avoid rate-limit
1. Polling Order Status API endpoints may be subject to rate limiting
1. Gemini recommends using WebSocket API to get market data

## What is the use of Websocket Order Events API
1. Order Events: Filled: A fill event indicates a partial or a complete fill. A complete fill is distinguished by a remaining_amount of 0.
1. Market Data: Trade event: Elements of the type trade have the following fields:


## JSON Payload of the requests
1. The payload of the requests will be a JSON object
1. Rather than being sent as the body of the POST request, it will be base-64 encoded and stored as a header in the request.

```python
import requests
import json
import base64
import hmac
import hashlib
import time

url = "https://api.gemini.com/v1/mytrades"
gemini_api_key = "mykey"
gemini_api_secret = "1234abcd".encode()

payload_nonce = time.time()

payload =  {"request": "/v1/mytrades", "nonce": payload_nonce}
encoded_payload = json.dumps(payload).encode()
b64 = base64.b64encode(encoded_payload)
signature = hmac.new(gemini_api_secret, b64, hashlib.sha384).hexdigest()

request_headers = {
    'Content-Type': "text/plain",
    'Content-Length': "0",
    'X-GEMINI-APIKEY': gemini_api_key,
    'X-GEMINI-PAYLOAD': b64,
    'X-GEMINI-SIGNATURE': signature,
    'Cache-Control': "no-cache"
    }

response = requests.post(url, headers=request_headers)

my_trades = response.json()
print(my_trades)
```

## What is HMAC?
1. Hash-based Message Authentication Code
1. Signature for authenticating API requests, specifically for services that require such a signature as part of their security protocol.
1. When you need to securely communicate with an API that requires HMAC authentication. This is common in financial services, including cryptocurrency exchanges like Gemini.

## What is HMAC?
1. HMAC combines a cryptographic hash function (like SHA-256 or SHA-384) with a secret cryptographic key. 
1. The resulting HMAC value is a fixed-size string that can be used to verify the authenticity and integrity of a message.

## Typical Gemini headers

```
POST /v1/mytrades
Content-Type: text/plain
Content-Length: 0
X-GEMINI-APIKEY: mykey
X-GEMINI-PAYLOAD:ewogICAgInJlcXVlc3QiOiAiL3YxL29yZGVyL3N
    0YXR1cyIsCiAgICAibm9uY2UiOiAxMjM0NTYsCgogICAgIm9yZGV
    yX2lkIjogMTg4MzQKfQo=
X-GEMINI-SIGNATURE: 337cc8b4ea692cfe65b4a85fcc9f042b2e3f
    702ac956fd098d600ab15705775017beae402be773ceee10719f
    f70d710f
```

## What is basic new-order-api?

1. POST https://api.gemini.com/v1/order/new
2. ```py
   payload = {
   "request": "/v1/order/new",
    "nonce": payload_nonce,
    "client_order_id": "requst-btc-121323",
    "option": ["maker-or-cancel" | "immediate-or-cancel" | "fill-or-kill"],
    "symbol": "btcusd",
    "amount": "5",
    "price": "3633.00",
    "side": "buy",
    "type": "exchange limit",
    "account": "Not required for AccountKey, required for master-key",
    "stop_price": "3622"
}```

## Public endpoint for Gemini-API

```
curl https://api.gemini.com/v1/book/trumpgusdperp?limit_bids=0&limit_asks=0
curl https://api.gemini.com/v1/symbols
curl https://api.gemini.com/v1/symbols/details/trumpgusdperp
curl https://api.gemini.com/v2/derivatives/candles/trumpgusdperp/1m
curl https://api.gemini.com/v1/trades/TRUMPGUSDPERP
curl https://api.gemini.com/v1/pubticker/trumpgusdperp
curl https://api.gemini.com/v1/book/trumpgusdperp
curl https://api.gemini.com/v2/candles/trumpgusdperp/1m
wscat --connect='wss://api.gemini.com/v1/marketdata/trumpgusdperp?top_of_book=false\&bids=false\&offers=false\&trades=true'
wscat --connect='wss://api.gemini.com/v1/marketdata/btcusd?top_of_book=false\&bids=true\&offers=false\&trades=true'
```

## Accessible API
1. [BTC - Ticker](https://api.gemini.com/v1/pubticker/btcusd)
2. [ETH - Current Order Book](https://api.gemini.com/v1/book/ethusd?limit_bids=5&limit_asks=5)
3. [BTC Trade history](https://api.gemini.com/v1/trades/btcusd)
4. [All the symbols API](https://api.gemini.com/v1/symbols)
5. [1hr - Candles API](https://api.gemini.com/v2/candles/btcusd/1hr)
6. [1m - 1m Candles API](https://api.gemini.com/v2/candles/btcusd/1m)
7. [BTCUSD SYmbol details](https://api.gemini.com/v1/symbols/details/btcusd)
8. [Network token for SOL](https://api.gemini.com/v1/network/SOL)
9. [Canldes API documentation](https://docs.gemini.com/rest-api/#candles)
10. [Gemini Fee Promos](https://api.gemini.com/v1/feepromos)
11. [PriceFeed](https://api.gemini.com/v1/pricefeed)
12. [BTCGUSDPERP Funding amount](https://api.gemini.com/v1/fundingamount/BTCGUSDPERP)
13. [Funding amount download](https://api.gemini.com/v1/fundingamountreport/records.xlsx?symbol=BTCGUSDPERP&fromDate=2021-01-01&toDate=2024-04-25&numRows=100000)
14. [Funding amount json download](https://api.gemini.com/v1/fundingamountreport/records.json?symbol=BTCGUSDPERP&fromDate=2021-01-01&toDate=2024-04-25&numRows=100000)
15. [FX-Price](https://api.gemini.com/v2/fxrate/gbpusd/2024-01-01)
16. [Staking rates](https://api.gemini.com/v1/staking/rates)

## Tokenized stock
1. Dinari
2. Arbitrium

## Generate mdanki
mdanki api_gemini.md api_gemini.apkg --deck "Mohan::DeepWork::Api"
