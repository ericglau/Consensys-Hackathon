pragma solidity >= 0.5.0 < 0.6.0;

interface Oracle {
    function value() external returns (string memory);
    function lastUpdatedBlock() external returns (uint);
    function what() external returns (string memory);
}