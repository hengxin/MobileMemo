Project distributed-mobile-memo
=======================

## 1. What is this project about?

This project aims to provide a *distributed shared memory* consisting of *atomic* registers over asynchronous message-passing networks. Both atomic, single-writer multi-reader (for short, SWMR) registers and atomic, multi-writer multi-reader (for short, MWMR) ones are supported. In the view of database, it can also be regarded as a distributed storage system which conforms to the atomic consistency. 

In this project, such atomic registers reside on separate mobile phones.

## 2. How to use this project as a black box?

Suppose a distributed algorithm consisting of `n` processes that access (`read/write`) distributed shared atomic registers. To utilize this project, one follows the steps:

- To distinguish the processes, each process is assigned a unique identifier. This is often a requirement of the algorithm itself.
- All the participating processes are required to log into the project through the `Login` screen described below. It will ask for process identifier and ip address. Once logged in successfully, each process starts as a server replica which is also described below.
- Each register is associated with a unique key. Registers are accessed according to their keys.
- To determine whether the atomic registers accessed are of SWMR or MWMR types.
- For an atomic SWMR register with key `k`, each process plays as a client and 
  - calls `SWMRAtomicityRegisterClient.INSTANCE().put(k:Key, val:String)` to write `val` to it;
  - calls `SWMRAtomicityRegisterClient.INSTANCE().get(k:Key)` to obtain its versioned value from which the value can be retrieved by calling `VersionValue#getValue()`.
- For an atomic MWMR register with key `k`, each process plays as a client and
  - calls `MWMRAtomicityRegisterClient.INSTANCE().put(k:Key, val:String)` to write `val` to it.
  - calls `MWMRAtomicityRegisterClient.INSTANCE().get(k:Key)` to obtain its versioned value from which the value can be retrieved by calling `VersionValue#getValue()`. 

## 3. System models (See package  `sharedmemory`)

### 3.1 Client/Server architecture

Each mobile phone holds a complete copy/replica of all data items and plays as a server. Clients issue their requests (read/write operations) to servers. All servers collectively process the requests and provide an illusion of a shared memory to the clients. In this project, the shared memory conforms to the atomic consistency. 

Clients should implement the `IRegisterClient` interface to issue the `get(key:Key)` and `put(key:Key, val:String)` operations.

### 3.2 Data model (See package `sharedmemory.data.kvs`)

We use the simple key-value data model. Each server replica holds a collection of data items. Each data item is associated with a key, which is simply a string. Values are strings as well. To fit in most algorithms for emulating a distributed shared memory, each key-value pair is further associated with a version. Specifically, in the algorithm for emulating atomic registers in this project, a version is comprised of an integer `process id` (of a client, as described above) and an integer local `seqno`. For each key, all versions are totally ordered. To summarize, each server replica holds a collection of `(key:Key, val:String, version:Version)` triples. 

`KVStoreInMemory` is an implementation of a database of key-value model. It maintains a `HashMap` of `(Key, VersionValue)` pairs, where `Key` is simply a String and `VersionValue` is a value associated with a `Version`. The class `KVPair` is a compressed representation of `(Key, VersionValue)` pairs.  

### 3.3 Communication (See package `sharedmemory.architecture.communication`)

Clients and servers communicate by message-passing. Each message carries the ip of its sender, thus called `IPMessage`. The receivers (both the clients and the servers in this project) of messages implement the `IReceiver` interface to handle with received messages. 

The core of the communication modular lies at the `CommmunicationService` class. It sends an `IPMessage` to a designated destination by calling `public void sendOneWay(final String receiver_ip, final IPMessage msg)`. To start to listen to coming messages, a process (as a client or as a server) can call `public void start2Listen(String server_ip)`. In addition, the `CommunicationService` class has implemented the `onReceive()` method of the `IReceiver` interface to propagate its received messages to `AtomicityMessagingService`. The latter class is related to the algorithm for emulating atomic registers and is described below.

## 4. Algorithm for emulating atomic registers (See package `sharedmemory.atomicity`)

### 4.1 Sketch of the ABD algorithm 

### 4.2 Messages (See package `sharedmemory.atomicity.message`)

### 4.3 

## 5. How to run the project?


