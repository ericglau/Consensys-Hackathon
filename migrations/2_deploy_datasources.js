/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
const ProvableKrakenETHXBT = artifacts.require('ProvableKrakenETHXBT');

module.exports = function(deployer) {
  deployer.deploy(
    ProvableKrakenETHXBT,
    process.env.OraclizeAddrResolverI_Address,
    Buffer.from('ETHXBT'),
  );
};
