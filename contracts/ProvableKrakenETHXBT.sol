pragma solidity >= 0.5.0 < 0.6.0;
import "../node_modules/provable-eth-api/provableAPI_0.5.sol";

contract ProvableKrakenETHXBT is usingProvable {
    mapping(bytes32 => bool) pendingQueryIds;

    string public value;
    uint public lastUpdatedBlock;
    string public what;

    constructor(address oracleAddress) public payable {
        OAR = OracleAddrResolverI(oracleAddress);
        update();
    }

    function __callback(bytes32 queryId, string memory result, bytes memory _proof) public {
        require(
        msg.sender == provable_cbAddress(),
        "sender address must equal provable callback address");
        require(pendingQueryIds[queryId], "queryId is not in pending state");
        value = result;
        lastUpdatedBlock = block.number;
        delete pendingQueryIds[queryId];
    }

    function update() public payable {
        require(provable_getPrice("URL") >= msg.value, "the sender pay for the transaction");
        bytes32 queryId = provable_query(
        "URL", "json(https://api.kraken.com/0/public/Ticker?pair=ETHXBT).result.XETHXXBT.c.0");
        pendingQueryIds[queryId] = true;
    }
}