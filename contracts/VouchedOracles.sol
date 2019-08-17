pragma solidity >= 0.5.5 < 0.6.0;
import "./Oracle.sol";

contract VouchedOracles {
    mapping(address => uint256) public oraclesBeingEvaluated;
    mapping(address => mapping(address => bool)) public voters;
    mapping(address => uint) public votes;

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

    function evaluateOracle(address oracleAddr) public {
        oraclesBeingEvaluated[oracleAddr] = block.number + votingPeriodInBlocks;
        voters[oracleAddr][msg.sender] = true;
        votes[oracleAddr] = 1;
    }

    function voteFor(address oracleAddr) external
        isBeingEvaluated(oracleAddr)
        isNotAlreadyVoting(oracleAddr, msg.sender)
    {
        voters[oracleAddr][msg.sender] = true;
        votes[oracleAddr] += 1;
    }

    function voteAgainst(address oracleAddr) external
        isBeingEvaluated(oracleAddr)
        isNotAlreadyVoting(oracleAddr, msg.sender)
    {
        voters[oracleAddr][msg.sender] = true;
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
            vouchedOracles[oracleAddr] = false;
            uint index = vouchedOracleIndex[oracleAddr] - 1;
            uint length = vouchedOraclesByWhat[oracle.what()].length;
            vouchedOraclesByWhat[oracle.what()][index] = vouchedOraclesByWhat[oracle.what()][length-1];
            vouchedOraclesByWhat[oracle.what()].length--;
            vouchedOracleIndex[oracleAddr] = 0;
        }
    }
}