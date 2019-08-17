pragma solidity ^0.5.0;
contract OracleSet {
    
    address[] oracles;
    uint index;
    uint nonce;

    // register caller as an oracle
    function register() public {
        if (!contains(oracles, msg.sender)) {
            oracles.push(msg.sender);
        }
    }
    
    // get the array of all oracles
    function getAllOracles() public view returns (address[] memory) {
       return oracles;
    }
    
    // get an array of oracles of given size, using round robin from the array of oracles
    function getOracles(uint size) public returns (address[] memory) {
        if (size >= oracles.length) {
            return oracles;
        }
        
        address[] memory result = new address[](size);
        for (uint i = 0; i<size; i++) {
            uint oracleIndex = (index + i) % oracles.length;
            result[i] = oracles[oracleIndex];
        }
        index = (index + 1) % oracles.length;
        return result;
    }
    
    // get an array of oracles of given size, randomly
    function getRandomOracles(uint size) public returns (address[] memory) {
        if (size >= oracles.length) {
            return oracles;
        }
        
        address[] memory result = new address[](size);
        int[] memory usedIndices = new int[](size);
        // init array elements to -1
        for (uint i = 0; i<size; i++) {
            usedIndices[i] = -1;
        }
        
        for (uint i = 0; i<size; i++) {
            uint oracleIndex;
            do {
                oracleIndex = random();
            } while(contains(usedIndices, int(oracleIndex)));
            usedIndices[i] = int(oracleIndex);
            result[i] = oracles[oracleIndex];
        }
        return result;
    }
 
    // pseudo random number generator
    function random() public returns (uint) {
        uint randomNumber = uint(keccak256(abi.encodePacked(now, msg.sender, nonce))) % oracles.length;
        nonce++;
        return randomNumber;
    }
    
    // check if array contains the number
    function contains(int[] memory array, int number) internal pure returns (bool) {
        for (uint i = 0; i<array.length; i++) {
            if (array[i] == number) {
                return true;
            }
        }
        return false;
    }
    
    // check if array contains the address
    function contains(address[] memory array, address addr) internal pure returns (bool) {
        for (uint i = 0; i<array.length; i++) {
            if (array[i] == addr) {
                return true;
            }
        }
        return false;
    }
 
}