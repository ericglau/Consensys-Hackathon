pragma solidity >= 0.5.0 < 0.6.0;
import "../node_modules/provable-eth-api/provableAPI_0.5.sol";

contract ProvableKrakenETHXBT is usingProvable {
    mapping(bytes32 => bool) pendingQueryIds;

    string public value;
    uint public lastUpdatedBlock;
    bytes public what;

    constructor(address oracleAddress, bytes memory _what) public payable {
        require(oracleAddress != address(0), "oracle address must not be 0");
        require(_what.length > 0, "what must not be empty");
        OAR = OracleAddrResolverI(oracleAddress);
        what = _what;
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