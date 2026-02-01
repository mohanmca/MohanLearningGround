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

For ssh-add -s … to work, these env vars must already exist:

SSH_AUTH_SOCK
SSH_AGENT_PID
```

## How to open-idea from command-line

```
fssh;open -na "IntelliJ IDEA.app"
```
