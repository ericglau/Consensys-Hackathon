Java web3 server for spinning up <num_oracles> oracles for XBTUSD, and constantly updates their values every (given number of) seconds.

- 50% are consistent
- 30% are somewhat consistent
- 20% are bad

##### Requirements:
Java 1.8

##### Build:
`mvn clean install`

##### Run:
From this directory, run:  
`java -jar target/web3server-1.0-SNAPSHOT-jar-with-dependencies.jar <num_oracles> <update_wait_millis> <private_key>`

Example:  
`java -jar target/web3server-1.0-SNAPSHOT-jar-with-dependencies.jar 10 1000 0x433df55606c17972e4444a11a7699972a52993f985ba29608fca2775d8248cfc`
