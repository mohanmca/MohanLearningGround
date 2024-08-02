## What is grpc channel?
1. A gRPC channel is an abstraction that represents a connection to a gRPC server. It encapsulates the necessary communication details, including the network address and the underlying protocol details.

## Key Features of gRPC Channels:
1. Connection Management: Channels manage the underlying network connections. They handle the creation, reuse, and shutdown of these connections, making the communication process more efficient.
2. Load Balancing: Channels can perform client-side load balancing. This means that if you have multiple server instances, the channel can distribute the requests across these instances to balance the load.
3. Name Resolution: Channels use name resolution to convert a logical server name into physical addresses. This can involve DNS lookups or service discovery mechanisms.
4. Configuration: Channels allow for various configuration options, such as timeouts, retries, and connection settings. This helps in fine-tuning the communication behavior according to the application’s needs.
5.Interceptors: Channels can be configured with interceptors that allow for custom logic to be applied to the RPC calls, such as logging, authentication, and tracing.

## How to create and use grpc Channel?

```cpp
#include <grpcpp/grpcpp.h>
#include "your_protos.grpc.pb.h"

int main() {
    // Create a channel to the server
    auto channel = grpc::CreateChannel("localhost:50051", grpc::InsecureChannelCredentials());

    // Create a stub (client)
    std::unique_ptr<YourService::Stub> stub = YourService::NewStub(channel);

    // Make a call to the server
    YourRequestMessage request;
    YourResponseMessage response;
    grpc::ClientContext context;
    grpc::Status status = stub->YourRpcMethod(&context, request, &response);

    if (status.ok()) {
        // Handle the response
    } else {
        // Handle the error
    }
    return 0;
}
```

## What is Stub in Grpc?

in gRPC, a stub (also known as a client stub or proxy) is an object that represents a remote service and provides methods corresponding to the service methods defined in the service's interface. The stub abstracts the complexities of network communication, allowing the client to call remote methods as if they were local method calls.

Key Features of gRPC Stubs:
1. Abstracts Network Communication: Stubs handle the serialization of request messages and the deserialization of response messages, making the remote procedure call (RPC) process seamless for the client.
2. Generated from Service Definitions: Stubs are automatically generated from the service definitions written in protocol buffers (protobuf). When you define a service in a .proto file, the gRPC toolchain generates the corresponding stub code in the target programming language.
3. Type-Safe: Stubs provide type-safe method calls, ensuring that the client uses the correct types for requests and responses as defined in the service's proto file.
4.Handles RPC Details: Stubs manage the low-level details of the RPC, such as timeouts, retries, and error handling, based on the configurations provided by the client.


## Streamin client in C++

```cpp
#include <iostream>
#include <memory>
#include <string>

#include <grpcpp/grpcpp.h>
#include "streaming_service.grpc.pb.h"

using grpc::Channel;
using grpc::ClientContext;
using grpc::ClientReaderWriter;
using grpc::SslCredentialsOptions;
using grpc::Status;
using streaming::DataRequest;
using streaming::DataResponse;
using streaming::StreamingService;

class StreamingClient {
public:
    StreamingClient(std::shared_ptr<Channel> channel)
        : stub_(StreamingService::NewStub(channel)) {}

    void StreamData() {
        ClientContext context;
        std::shared_ptr<ClientReaderWriter<DataRequest, DataResponse>> stream(
            stub_->StreamData(&context));

        std::thread writer([stream]() {
            for (int i = 0; i < 5; ++i) {
                DataRequest request;
                request.set_id(i);
                std::cout << "Sending request with id: " << i << std::endl;
                stream->Write(request);
                std::this_thread::sleep_for(std::chrono::seconds(1));
            }
            stream->WritesDone();
        });

        DataResponse response;
        while (stream->Read(&response)) {
            std::cout << "Received response: " << response.message() << std::endl;
        }

        writer.join();
        Status status = stream->Finish();
        if (!status.ok()) {
            std::cerr << "StreamData rpc failed." << std::endl;
        }
    }

private:
    std::unique_ptr<StreamingService::Stub> stub_;
};

int main(int argc, char** argv) {
    SslCredentialsOptions ssl_opts;
    ssl_opts.pem_root_certs = "path/to/ca.crt";
    ssl_opts.pem_private_key = "path/to/client.key";
    ssl_opts.pem_cert_chain = "path/to/client.crt";

    auto channel_creds = grpc::SslCredentials(ssl_opts);
    StreamingClient client(grpc::CreateChannel("localhost:50051", channel_creds));
    client.StreamData();

    return 0;
}
```

## Generate mdanki
mdanki grpc.md grpc.apkg --deck "Mohan::DeepWork::grpc"