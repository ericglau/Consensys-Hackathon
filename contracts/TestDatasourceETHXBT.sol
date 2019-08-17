pragma solidity >= 0.5.0 < 0.6.0;

contract TestDatasourceETHXBT {
    string public value;
    uint public lastUpdatedBlock;
    bytes public what;

    event OnUpdate();

    constructor(bytes memory _what) public payable {
        require(_what.length > 0, "what must not be empty");
        what = _what;
    }

    function setValue(string memory _value) public {
        value = _value;
        lastUpdatedBlock = block.number;
    }

    function update() public payable {
        emit OnUpdate();
    }
}