import React, { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';
import { ChartComponent } from './chart';
import {
  Contract,
  ABIDefinition,
  BlockType,
  EventLog,
  Callback,
  EventEmitter,
  IProvider,
} from 'web3/types';
import { TransactionObject } from 'web3/eth/types';

interface VouchedOracle {
  options: {
    address: string;
    jsonInterface: ABIDefinition[];
    data: string;
    from: string;
    gasPrice: string;
    gas: number;
  };
  methods: {
    evaluateOracle: (address: string) => TransactionObject<any>;
    voteFor: (address: string) => TransactionObject<any>;
    voteAgainst: (address: string) => TransactionObject<any>;
    closeVote: (address: string) => TransactionObject<any>;
    getRandomOracles: (what: Buffer, size: number) => TransactionObject<any>;
  };
  deploy(options: {
    data: string;
    arguments: any[];
  }): TransactionObject<Contract>;
  events: {
    [eventName: string]: (
      options?: {
        filter?: object;
        fromBlock?: BlockType;
        topics?: string[];
      },
      cb?: Callback<EventLog>,
    ) => EventEmitter;
    allEvents: (
      options?: { filter?: object; fromBlock?: BlockType; topics?: string[] },
      cb?: Callback<EventLog>,
    ) => EventEmitter;
  };
  getPastEvents(
    event: string,
    options?: {
      filter?: object;
      fromBlock?: BlockType;
      toBlock?: BlockType;
      topics?: string[];
    },
    cb?: Callback<EventLog[]>,
  ): Promise<EventLog[]>;
  setProvider(provider: IProvider): void;
}

interface Drizzle {
  store: any;
  contracts: {
    VouchedOracles: VouchedOracle;
  };
}

export interface AppProps {
  drizzle: Drizzle;
}

const App = ({ drizzle }: AppProps) => {
  const [isLoading, setIsLoading] = useState(true);
  const [state, setState] = useState({ drizzleState: null });

  useEffect(() => {
    const unsubscribe = drizzle.store.subscribe(() => {
      // every time the store updates, grab the state from drizzle
      const drizzleState = drizzle.store.getState();

      // check to see if it's ready, if so, update local component state
      if (drizzleState.drizzleStatus.initialized) {
        setIsLoading(false);
        setState({ drizzleState });
      }
    });

    return () => {
      unsubscribe();
    };
  }, []);

  useEffect(() => {
    if (!isLoading) {
      const contract = drizzle.contracts.VouchedOracles;
      contract.methods
        .evaluateOracle('0xCfEB869F69431e42cdB54A4F4f105C19C080A601')
        .send()
        .then((data) => {
          console.log(data);
        });
      console.log(contract.methods);
    }
  }, [isLoading]);

  if (isLoading) {
    return <div>'Loading Drizzle...'</div>;
  }

  return (
    <div className="App">
      <ChartComponent />
    </div>
  );
};

export default App;
