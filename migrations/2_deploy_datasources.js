/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
const ProvableKrakenXBTUSD = artifacts.require('ProvableKrakenXBTUSD');
const TestDatasource = artifacts.require('TestDatasource');

module.exports = function(deployer) {
  deployer.deploy(
    ProvableKrakenXBTUSD,
    process.env.OraclizeAddrResolverI_Address,
    Buffer.from('XBTUSD'),
  );
  for (let i = 0; i < 10; i++) {
    deployer.deploy(TestDatasource, Buffer.from('XBTUSD'));
  }
};
