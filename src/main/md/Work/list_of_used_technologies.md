## [Supervisord](https://supervisord.org/)
`supervisord` is a **process control system** that helps you manage and monitor long-running applications and background processes (daemons) on Unix-like operating systems.
---

### 🧩 Overview

`supervisord` is part of **Supervisor**, a client/server system written in Python. It lets you **start, stop, restart, and automatically restart** processes as needed — especially useful for managing services that don’t have native init/systemd scripts.

It consists of two main components:

* **`supervisord`** — the main daemon that runs in the background and manages the processes.
* **`supervisorctl`** — a command-line client to control and interact with `supervisord`.

---

### ⚙️ How It Works

1. You define your processes in a configuration file, typically `/etc/supervisord.conf` or `/etc/supervisor/conf.d/*.conf`.
2. Each program entry specifies:

   * The command to run
   * The working directory
   * Auto-restart behavior
   * Log files
   * User permissions
3. When you start `supervisord`, it launches all configured processes and monitors them.

Example configuration snippet:

```ini
[program:myapp]
command=/usr/bin/python3 /opt/myapp/app.py
directory=/opt/myapp
autostart=true
autorestart=true
stderr_logfile=/var/log/myapp.err.log
stdout_logfile=/var/log/myapp.out.log
user=ubuntu
```

Then you can control it with:

```bash
supervisorctl start myapp
supervisorctl restart myapp
supervisorctl status
```

---

### 🚀 Common Use Cases

* Running and managing **web servers**, **API services**, **workers**, or **cron-like jobs**.
* Keeping **background jobs** (e.g., Celery workers, queue consumers) always alive.
* Deployments where **systemd** or **init.d** isn’t available (e.g., Docker containers, older Linux distros).

---

### 🧠 Comparison with Other Tools

| Tool            | Typical Use Case                    | Notes                                   |
| --------------- | ----------------------------------- | --------------------------------------- |
| **systemd**     | Default init system on modern Linux | More integrated, less portable          |
| **supervisord** | Lightweight process manager         | Easy to configure; great for containers |
| **PM2**         | Node.js process manager             | More specialized for JS apps            |
| **forever**     | Node.js apps only                   | Simpler but limited                     |

---

### 🛡️ Key Benefits

* Auto-restart crashed processes
* Simple text-based configuration
* Unified logging per process
* Remote control via XML-RPC interface
* Works without root (can manage user-level services)

---

## [Mirrord](https://github.com/metalbear-co/mirrord)
1. mirrord lets developers run local processes in the context of their Kubernetes environment.
2. It’s meant to provide the benefits of running your service on a cloud environment (e.g. staging) without actually going through the hassle of deploying it there, and without disrupting the environment by deploying untested code.
3. It comes as a Visual Studio Code extension, an IntelliJ plugin and a CLI tool. You can read more about it here.

Short answer first — then a few practical examples and gotchas.

---

## What is **mirrord**?

**mirrord** is a developer tool that “lets developers run local processes in the context of their Kubernetes environment.” In practice it connects a local process (or a container you launch locally) to a real pod in your cluster so the process experiences the pod’s network, file access and environment, without you having to deploy your code to the cluster each time. It comes as a CLI, a VS Code extension and an IntelliJ plugin. 

You can use the CLI to either:

* *exec* a local process and bind it to a target pod (`mirrord exec … --target pod/<name>`), or
* *run a container* locally with mirrord’s layer injected so that the container runs “in the context” of the cluster (the unstable `mirrord container` feature). 

---

## How it’s used with **Docker**

There are two common Docker-related workflows:

### 1) Debug a local process that talks to Kubernetes

If your app is normally run as a binary (node, python, go, etc.), deploy the app to Kubernetes as usual and then run the same command **locally under mirrord** so the local process behaves as if it were the pod:

```bash
mirrord exec node app.js --target pod/my-pod
```

This is the simplest flow: mirrord “plugs” your local process into the selected pod — routing incoming traffic to your process, routing outgoing traffic through the pod, and mirroring file/env operations. 

### 2) Run a Docker container that is connected to the cluster (the `mirrord container` flow)

If you want to run a **container image** but have that container run with mirrord’s injection/networking (for example to run a containerized service locally with the cluster’s environment), use the `mirrord container` command.
`mirrord container` will:

* spawn the mirrord agent in the cluster and a small “intproxy” sidecar,
* spawn a helper/CLI image on your machine (the mirrord CLI image),
* add the sidecar’s network to your container and mount/inject the mirrord layer so the container runs inside the mirrord context. 

Practical example (typical form):

```bash
mirrord container -- docker run --rm -it -p 8080:80 my-image
```

Notes about the command and runtime: the container runtime used defaults to **Docker** (but you can switch it — e.g. to Podman — via the `MIRRORD_CONTAINER_USE_RUNTIME` environment variable). 

---

## Important caveats & troubleshooting (Docker-specific)

* **Extra Linux capabilities**
  Running containers inside the pod/agent context requires additional Linux capabilities (the agent/container may need `CAP_NET_ADMIN`, `CAP_NET_RAW`, `CAP_SYS_PTRACE`, `CAP_SYS_ADMIN`). You can disable some capabilities in configuration, but functionality may be limited. 

* **Non-loopback `mirrord` console address**
  When using container mode, mirrord expects the console address env var to be a non-loopback address (so containers can reach it). If that’s misconfigured you’ll see an error. 

* **Host networking / `host.docker.internal`**
  Inside the container, resolving your host’s IP (e.g., `host.docker.internal`) can be tricky. There’s a `container.override_host_ip` config option you can use to point the container to the correct host IP (the docs even show how to find it from within an example container). This is particularly useful on WSL/macOS setups. 

* **Port publishing and cli extra args**
  `mirrord container` is marked unstable and the command warns it can have problems with `-p`/port publishing. If you need to publish ports, add those publish args in `container.cli_extra_args` in your mirrord config. 

* **Unstable feature**
  The `mirrord container` feature is currently unstable (the CLI prints a warning). Expect rough edges compared with the mature `mirrord exec` flow. 

---

## Quick setup recap

1. **Install mirrord (CLI)** — e.g. via Homebrew or the install script:

   ```sh
   brew install metalbear-co/mirrord/mirrord
   # or
   curl -fsSL https://raw.githubusercontent.com/metalbear-co/mirrord/main/scripts/install.sh | bash
   ```



2. **To debug a local process against a pod:**

   ```sh
   mirrord exec <your-command> --target pod/<your-pod>
   ```



3. **To run a Docker container under mirrord:**

   ```sh
   mirrord container -- docker run --rm -it my-image
   ```

   Keep the caveats above in mind (capabilities, console address, host IP). The container mode will create a sidecar and add network/volumes so your container is injected with the mirrord layer. 

---

## Where to read more

* Official repo `README` (overview & `mirrord exec`): see the project README. 
* `mirrord container` and the implementation/config options live in the CLI and config sources (`mirrord/cli/src/container.rs`, `mirrord/config/src/container.rs`) — these show the runtime details, env vars and host/port notes mentioned above.  

