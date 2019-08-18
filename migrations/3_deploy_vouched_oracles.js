/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
const VouchedOracles = artifacts.require('VouchedOracles');

module.exports = function(deployer) {
  deployer.deploy(VouchedOracles, 2);
};
