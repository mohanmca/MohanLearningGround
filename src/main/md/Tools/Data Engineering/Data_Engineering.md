## Common DE requirements
1. Prove the data is accurate
2. Meet Customer SLA
3. Resilient production pipelines
4. Rapidly incorporating new requirements
5. Easy monitoring and maintenance

## Vendors
1. Airbyte
2. Fivetran
3. Stitch
4. Talend
5. [Data Integration Tools](https://www.gartner.com/reviews/market/data-integration-tools/compare/fivetran-vs-talend)

## Regular DE production issues
1. Not real-time
2. You can execute pipeline only during night-time

## Delta Table.
1. It uses Parquet
2. Supports ACID transaction
3. DLT is both streaming source and streaming sync (can act as input and output)

## Why spark?
1. Stream and batch has same code-base to handle

## Health Care
1. Using Sql-Server and SSIS
2. Azure DataFactory was used to automatically load the data into Delta-Lake
3. Few prefer to use separate delta-table for each pipeline
4. 