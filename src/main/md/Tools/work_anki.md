## What does this do?

```
fssh () {
  killall ssh-agent
  ssh-agent > ~/.ssh-agent
  source ~/.ssh-agent
  sleep 1
  ssh-add -s /usr/local/lib/opensc-pkcs11.so
}
```

```
SSH_AUTH_SOCK
SSH_AGENT_PID
Are loaded by 
ssh-agent > ~/.ssh-agent
source ~/.ssh-agent

ssh-add -l lists the SSH keys currently loaded in your running SSH agent.
For ssh-add -s … to work, these env vars must already exist:

SSH_AUTH_SOCK
SSH_AGENT_PID
```

## How to validate
```
echo $SSH_AUTH_SOCK
ssh-add -l
```

## How to open-idea from command-line
```
fssh;open -na "IntelliJ IDEA.app"
```

## How to find which service unable to read kafka topic
1. use datadog MSK dashboard and group by consumer-group, will find application that one is lagging

## Learn to work with - How to use git work-tree?
```
```
