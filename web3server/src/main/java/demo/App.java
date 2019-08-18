package demo;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws Exception
    {
        if (args.length != 3) {
            System.out.println("params: <num_oracles> <update_wait_millis> <private_key>");
            return;
        }

        int num_oracles = Integer.parseInt(args[0]);
        Long update_wait_millis = Long.parseLong(args[1]);
        String privateKey = args[2];

        System.out.println( "Starting web3server" );

        Web3j web3j = Web3j.build(new HttpService("http://localhost:8545/")); 
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println(clientVersion);

       
        List<TestDatasource_sol_TestDatasource> oracles = new ArrayList<TestDatasource_sol_TestDatasource>();
       
       Credentials credentials = Credentials.create(privateKey);


        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        for (int i=0; i<num_oracles; i++) {
            TestDatasource_sol_TestDatasource contract = TestDatasource_sol_TestDatasource.deploy(
                web3j,
                credentials,
                contractGasProvider,
                new BigInteger("0"),
                "XBTUSD".getBytes()
                ).send();
        
            oracles.add(contract);
            if (num_oracles <= 10) System.out.println("started oracle " + contract.getContractAddress());
        }

        // dump contract addresses to file
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<num_oracles; i++) {
            sb.append(oracles.get(i).getContractAddress()).append(",");
        }
        FileUtils.write(new File("contracts.csv"), sb.toString());

        System.out.println("started " + num_oracles + " oracles, addresses written to " + new File("contracts.csv"));


        int loopNumber = 0;
        while(true) {
            System.out.println("loop number: " + loopNumber++);

            for (int i=0; i<(int)(num_oracles * 0.5); i++) {
                setValue(oracles, i, Double.toString(10000 + (Math.random()*2) - 1));
            }
    
            for (int i = (int)(num_oracles * 0.5); i<(int)(num_oracles * 0.8); i++) {
                setValue(oracles, i, Double.toString(10000 + (Math.random()*200) - 100));
            }
    
            for (int i = (int)(num_oracles * 0.8); i<num_oracles; i++) {
                setValue(oracles, i, Double.toString(10000 + (Math.random()*20000) - 10000));
            }

            Thread.sleep(update_wait_millis);
            System.out.println("updated " + num_oracles + " oracles with new values");
        }

        //String storedValue = oracles.get(0).value().send();
        //System.out.println(storedValue);

    }

    private static void setValue(List<TestDatasource_sol_TestDatasource> oracles, int i, String value) throws Exception {
        TestDatasource_sol_TestDatasource contract = oracles.get(i);
        if (oracles.size() <= 10) System.out.println("set value " + value);
        contract.setValue(value).send();
    }
}
