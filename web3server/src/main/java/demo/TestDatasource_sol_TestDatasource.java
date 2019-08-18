package demo;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.4.0.
 */
public class TestDatasource_sol_TestDatasource extends Contract {
    private static final String BINARY = "60806040526040516105de3803806105de8339818101604052602081101561002657600080fd5b810190808051604051939291908464010000000082111561004657600080fd5b90830190602082018581111561005b57600080fd5b825164010000000081118282018810171561007557600080fd5b82525081516020918201929091019080838360005b838110156100a257818101518382015260200161008a565b50505050905090810190601f1680156100cf5780820380516001836020036101000a031916815260200191505b50604052505050600081511161014657604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601660248201527f77686174206d757374206e6f7420626520656d70747900000000000000000000604482015290519081900360640190fd5b8051610159906002906020840190610160565b50506101fb565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106101a157805160ff19168380011785556101ce565b828001600101855582156101ce579182015b828111156101ce5782518255916020019190600101906101b3565b506101da9291506101de565b5090565b6101f891905b808211156101da57600081556001016101e4565b90565b6103d48061020a6000396000f3fe60806040526004361061004a5760003560e01c80633fa4f2451461004f57806393a09352146100d9578063a2e620451461018e578063b24bb84514610196578063f90ce5ba146101ab575b600080fd5b34801561005b57600080fd5b506100646101d2565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561009e578181015183820152602001610086565b50505050905090810190601f1680156100cb5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156100e557600080fd5b5061018c600480360360208110156100fc57600080fd5b81019060208101813564010000000081111561011757600080fd5b82018360208201111561012957600080fd5b8035906020019184600183028401116401000000008311171561014b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610260945050505050565b005b61018c61027b565b3480156101a257600080fd5b506100646102a6565b3480156101b757600080fd5b506101c06102fe565b60408051918252519081900360200190f35b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102585780601f1061022d57610100808354040283529160200191610258565b820191906000526020600020905b81548152906001019060200180831161023b57829003601f168201915b505050505081565b8051610273906000906020840190610304565b505043600155565b6040517f2b3b84d82fbc95f6bc5333052d77ee13dfd99e88c3639b0352ae68f2a7446d6690600090a1565b6002805460408051602060018416156101000260001901909316849004601f810184900484028201840190925281815292918301828280156102585780601f1061022d57610100808354040283529160200191610258565b60015481565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061034557805160ff1916838001178555610372565b82800160010185558215610372579182015b82811115610372578251825591602001919060010190610357565b5061037e929150610382565b5090565b61039c91905b8082111561037e5760008155600101610388565b9056fea265627a7a72315820d565d7e6a9314c77c2ab9572e6a66a007bd4a92f232a828efe603233cd947a3c64736f6c634300050b0032";

    public static final String FUNC_VALUE = "value";

    public static final String FUNC_SETVALUE = "setValue";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC_WHAT = "what";

    public static final String FUNC_LASTUPDATEDBLOCK = "lastUpdatedBlock";

    public static final Event ONUPDATE_EVENT = new Event("OnUpdate", 
            Arrays.<TypeReference<?>>asList());
    ;

    @Deprecated
    protected TestDatasource_sol_TestDatasource(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TestDatasource_sol_TestDatasource(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TestDatasource_sol_TestDatasource(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TestDatasource_sol_TestDatasource(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> value() {
        final Function function = new Function(FUNC_VALUE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setValue(String _value) {
        final Function function = new Function(
                FUNC_SETVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> update(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<byte[]> what() {
        final Function function = new Function(FUNC_WHAT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<BigInteger> lastUpdatedBlock() {
        final Function function = new Function(FUNC_LASTUPDATEDBLOCK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<OnUpdateEventResponse> getOnUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONUPDATE_EVENT, transactionReceipt);
        ArrayList<OnUpdateEventResponse> responses = new ArrayList<OnUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnUpdateEventResponse typedResponse = new OnUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnUpdateEventResponse> onUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OnUpdateEventResponse>() {
            @Override
            public OnUpdateEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONUPDATE_EVENT, log);
                OnUpdateEventResponse typedResponse = new OnUpdateEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<OnUpdateEventResponse> onUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONUPDATE_EVENT));
        return onUpdateEventFlowable(filter);
    }

    @Deprecated
    public static TestDatasource_sol_TestDatasource load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestDatasource_sol_TestDatasource(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TestDatasource_sol_TestDatasource load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestDatasource_sol_TestDatasource(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TestDatasource_sol_TestDatasource load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TestDatasource_sol_TestDatasource(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TestDatasource_sol_TestDatasource load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TestDatasource_sol_TestDatasource(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TestDatasource_sol_TestDatasource> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, byte[] _what) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_what)));
        return deployRemoteCall(TestDatasource_sol_TestDatasource.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<TestDatasource_sol_TestDatasource> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, byte[] _what) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_what)));
        return deployRemoteCall(TestDatasource_sol_TestDatasource.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<TestDatasource_sol_TestDatasource> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, byte[] _what) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_what)));
        return deployRemoteCall(TestDatasource_sol_TestDatasource.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<TestDatasource_sol_TestDatasource> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, byte[] _what) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_what)));
        return deployRemoteCall(TestDatasource_sol_TestDatasource.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static class OnUpdateEventResponse {
        public Log log;
    }
}
