pragma solidity >= 0.5.5 < 0.6.0;
import "./Oracle.sol";

contract VouchedOracles {
    
    mapping(address => uint256) public oraclesBeingEvaluated;
    mapping(address => mapping(address => bool)) public voters;
    mapping(address => address[]) public votersArray;
    mapping(address => int) public votes;

    mapping(address => bool) public vouchedOracles;
    mapping(address => uint) public vouchedOracleIndex;
    mapping(bytes => Oracle[]) public vouchedOraclesByWhat;

    uint256 votingPeriodInBlocks;

    constructor(uint _votingPeriodInBlocks) public {
        require(_votingPeriodInBlocks > 0, "voting period must be greater than 0");
        votingPeriodInBlocks = _votingPeriodInBlocks;
    }

    modifier isBeingEvaluated(address oracleAddr) {
        require(oraclesBeingEvaluated[oracleAddr] > 0, "oracle must be in the evaluating stage");
        _;
    }
    
    modifier isNotBeingEvaluated(address oracleAddr) {
        require(oraclesBeingEvaluated[oracleAddr] == 0, "oracle must not be in the evaluating stage");
        _;
    }

    modifier isNotAlreadyVoting(address oracleAddr, address voter) {
        require(!voters[oracleAddr][voter], "voter has already voted");
        _;
    }

    modifier isAlreadyVouched(address oracleAddr) {
        require(vouchedOracles[oracleAddr], "oracle must be vouched");
        _;
    }

    modifier votingPeriodIsClosed(address oracleAddr) {
        require(
            oraclesBeingEvaluated[oracleAddr] >= block.number,
            "voting period has not been closed yet");
        _;
    }

    function evaluateOracle(address oracleAddr) external
        isNotBeingEvaluated(oracleAddr)
    {
        oraclesBeingEvaluated[oracleAddr] = block.number + votingPeriodInBlocks;
        voters[oracleAddr][msg.sender] = true;
        votersArray[oracleAddr].push(msg.sender);
        votes[oracleAddr] = 1;
    }

    function voteFor(address oracleAddr) external
        isBeingEvaluated(oracleAddr)
        isNotAlreadyVoting(oracleAddr, msg.sender)
    {
        voters[oracleAddr][msg.sender] = true;
        votersArray[oracleAddr].push(msg.sender);
        votes[oracleAddr] += 1;
    }

    function voteAgainst(address oracleAddr) external
        isBeingEvaluated(oracleAddr)
        isNotAlreadyVoting(oracleAddr, msg.sender)
    {
        voters[oracleAddr][msg.sender] = true;
        votersArray[oracleAddr].push(msg.sender);
        votes[oracleAddr] -= 1;
    }

    function closeVote(address oracleAddr) external votingPeriodIsClosed(oracleAddr) {
        Oracle oracle = Oracle(oracleAddr);
        oraclesBeingEvaluated[oracleAddr] = 0;
        if (votes[oracleAddr] > 0) {
            if (vouchedOracles[oracleAddr]) {
                return;
            }

            vouchedOraclesByWhat[oracle.what()].push(oracle);
            vouchedOracles[oracleAddr] = true;
            vouchedOracleIndex[oracleAddr] = vouchedOraclesByWhat[oracle.what()].length;
        } else if (votes[oracleAddr] < 0) {
            if (!vouchedOracles[oracleAddr]) {
                return;
            }
            vouchedOracles[oracleAddr];
            uint index = vouchedOracleIndex[oracleAddr] - 1;
            uint length = vouchedOraclesByWhat[oracle.what()].length;
            vouchedOraclesByWhat[oracle.what()][index] = vouchedOraclesByWhat[oracle.what()][length-1];
            vouchedOraclesByWhat[oracle.what()].length--;
            vouchedOracleIndex[oracleAddr] = 0;
        }
        clearVotes(oracleAddr);
    }
    
    
    
    function clearVotes(address oracleAddr) internal {
        address[] memory votersArrayForOracle = votersArray[oracleAddr];
        for (uint i=0; i<votersArrayForOracle.length; i++) {
            delete voters[oracleAddr][votersArrayForOracle[i]];
        }
    }
    
    uint nonce;
    
    // get an array of oracles of given size, randomly
    function getRandomOracles(bytes memory what, uint size) public returns (Oracle[] memory) {
        Oracle[] memory oracles = vouchedOraclesByWhat[what];
        if (size >= oracles.length) {
            return oracles;
        }
        
        Oracle[] memory result = new Oracle[](size);
        int[] memory usedIndices = new int[](size);
        // init array elements to -1
        for (uint i = 0; i<size; i++) {
            usedIndices[i] = -1;
        }
        
        for (uint i = 0; i<size; i++) {
            uint oracleIndex;
            do {
                oracleIndex = random(oracles.length);
            } while(contains(usedIndices, int(oracleIndex)));
            usedIndices[i] = int(oracleIndex);
            result[i] = oracles[oracleIndex];
        }
        return result;
    }
 
    // pseudo random number generator
    function random(uint range) internal returns (uint) {
        uint randomNumber = uint(keccak256(abi.encodePacked(now, msg.sender, nonce))) % range;
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

}
