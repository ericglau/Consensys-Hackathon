/* eslint-disable @typescript-eslint/explicit-function-return-type */
const Migrations = artifacts.require('Migrations');

module.exports = function(deployer) {
  deployer.deploy(Migrations);
};
