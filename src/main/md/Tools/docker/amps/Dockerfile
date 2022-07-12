FROM ubuntu
MAINTAINER 60East Technologies <support@crankuptheamps.com>
ADD http://devnull.crankuptheamps.com/releases/amps/develop/AMPS-develop-Release-Linux.tar.gz /binaries/AMPS.tar
COPY config.xml /
RUN ls -l /
RUN ls -l /binaries
RUN tar xvf /binaries/AMPS.tar --transform='s,AMPS[^/]*/,AMPS/,' 
RUN ls -l /

# expose websocket and tcp port
EXPOSE 9007
EXPOSE 9008

# expose admin port
EXPOSE 8085

CMD ["/AMPS/bin/ampServer","config.xml"]




