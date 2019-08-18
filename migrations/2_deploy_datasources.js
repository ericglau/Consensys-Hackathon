/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
const ProvableKrakenETHXBT = artifacts.require('ProvableKrakenETHXBT');
const TestDatasourceETHXBT = artifacts.require('TestDatasourceETHXBT');

module.exports = function(deployer) {
  deployer.deploy(
    ProvableKrakenETHXBT,
    process.env.OraclizeAddrResolverI_Address,
    Buffer.from('ETHXBT'),
  );
  deployer.deploy(TestDatasourceETHXBT, Buffer.from('ETHXBT'));
};
