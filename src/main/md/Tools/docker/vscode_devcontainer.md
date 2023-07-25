## Dev-container
1. You need to switch between too many environment without  installing anything on the local machine
  1. Switching between js and cpp project
  2. Switching between python 3.6 and python 3.7
2. Use docker environment as a development environemnt

## PreRequisite
1. Docker Desktop
2. Remote Containers Extension in VSCODE
3. ```docker version``` //validate docker is running

## Steps to make remote container setup
1. "Remote-container: Add Development container configuration" from command pallate
  1.  devcontainer.json and Dockerfile is added by extension
2. "Remote-Containers: Rebuild and Reopen in Container" from command pallate
3. Using "Remote explorer" we can view all the ports that are forwarded
4. We can change the localhost_port by right-clicking port and service in "Remote Explorer"
5. cmd_shift_p > forward a port

## devcontainer.json
1. Dockerfile
2. context : root of the project
3. Args: passed to the Dockerfile while building container
4. settings.... settings for VSCode for the project inside the container
5. extension inside the container for VSCode
6. forwardPorts
7. remoteUser (user inside the container)
8. postCreateCommand (command to execute once the Dockerbuild is complete)

## Installing software to existing containers
1. can be used to create postCreateCommand
2. We can also update Dockerfile
3. At the bottom...
  1. RUN apt-get update
  2. Run command would create new layer
  3. -y should be added to suppress the prompt
4. Remote Container: Rebuild Containers ... (Command pallette)
5. 

## Reference
1. [Introduction [1 of 8] | Beginner's Series to: Dev Containers](https://www.youtube.com/watch?v=61M2takIKl8&list=PLj6YeMhvp2S5G_X6ZyMc8gfXPMFPg3O31)
2. [Dev-container](https://learn.microsoft.com/en-us/shows/beginners-series-to-dev-containers/)
3. [Remote dev container](https://code.visualstudio.com/docs/devcontainers/tutorial)
