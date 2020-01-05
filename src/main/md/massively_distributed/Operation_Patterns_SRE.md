## SLO and Error budget

* Error budget to determine acceptable risk and make informed decisions about when changes should be made. The error budget is a limit on how much time the system is allowed to be down, defined by the contracted service-level agreement (SLA) or the intended service-level objective (SLO). Many clients stop new releases when they might miss the service-level agreement (SLA). Error budget goes a step further and encourages testing and releasing only if downtime is left in the SLA.

* Tools for operational metrics
  * Prometheus (Event and alert monitoring System)
    * Prometheus is not intended as a dashboarding solution. Although it can be used to graph specific queries, it is not a full-fledged dashboarding solution and needs to be hooked up with Grafana to generate dashboards; this has been cited as a disadvantage due to the additional setup complexity
    * Data storage format - The ability to specify an arbitrary list of labels and to query based on these in real time is why Prometheus' data model is called multi-dimensional.
    * Prometheus collects data in the form of time series. The time series are built through a pull model: the Prometheus server queries a list of data sources (sometimes called exporters) at a specific polling frequency.
    * Prometheus provides its own query language PromQL (Prometheus Query Language) that lets users select and aggregate data. PromQL is specifically adjusted to work in convention with a Time-Series Database and therefore provides time-related query functionalities.
    * Promethus vs (Datadog, elk, nagios, dynatrace)
  * Grafana - Grafana is the open source analytics and monitoring solution for every database
    * Grafana was designed to work as a UI for analyzing metrics. Can be alternative to Kibana
    * Dashboard, Datasource, Grpah, Panel, Plugin, Query, Timeseries and Visualization
    * Grafana supports querying Prometheus. The Grafana data source for Prometheus is included since Grafana 2.5.0 (2015-10-28).
    * Grafana requires a database to store its configuration data, such as users, data sources, and dashboards. MySql, Postgres and SqlLite are currently supported
    * [Grafana Glossary] (https://grafana.com/docs/grafana/latest/guides/glossary/)
    * [Grafana Data Source] (https://grafana.com/docs/grafana/latest/features/datasources/)
  * Zipkin
    * Zipkin is a distributed tracing system. It helps gather timing data needed to troubleshoot latency problems in service architectures. Features include both the collection and lookup of this data.
    * Zipkin also has an instrumentation library to instrument other libraries to support tracing

* [Prometheus_(software)](https://en.wikipedia.org/wiki/Prometheus_software)
* [ZIPKIN TUTORIAL: GET STARTED EASILY WITH DISTRIBUTED TRACING](https://www.scalyr.com/blog/zipkin-tutorial-distributed-tracing/)
* [Spring Cloud – Tracing Services with Zipkin](https://www.baeldung.com/tracing-services-with-zipkin)