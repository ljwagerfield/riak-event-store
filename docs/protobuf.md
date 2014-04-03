# Regenerate Protocol Buffer Scala classes

1.  Clone the following repository:

        https://github.com/SandroGrzicic/ScalaBuff

2.  SBT into the project and run the following command, substituting in the correct project paths:

        run --proto_path=~/riak-event-store/src/main/protobuf --scala_out=~/riak-event-store/src/main/scala
