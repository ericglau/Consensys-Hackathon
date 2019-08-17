# Consensys-Hackathon

## Prerequisites

1. Connect to an existing blockchain network or run your own using [Ganache-CLI](https://github.com/trufflesuite/ganache)
2. Modify the `truffle-config.js` to suite your configuration

## Development

```sh
ganache-cli

# Make note of the output consisting of OAR = OraclizeAddrResolverI(...);
npx ethereum-bridge -a 9 -H 127.0.0.1 -p 8545 --dev

# Fill in values as appropriate
cp `.env.example` `.env`

npm run deploy
```

## Testing

```sh
truffle test
```
