* Some of the application installation, maintenance requires complex task automation
* Ansible is a task execution engine
  * Ability to manage remote computers is the power of Ansbile
  * Execute tasks on multiple sysems at the same time
  * Ansible is written using python
  * Ansible scripts are written in YAML
  * Ansible require one "Ansible Engine" as a  control system
  * Use of Microsoft Windows as control machine is not supported
* Requires python 2.6 or later
* On fedora
  * sudo dnf search ansible
  * sudo dnf install ansible
* Installation  
```Bash
which ansible
ansible --version // 2.2.1.0
/etc/ansible/ansible.cfg
```

```Bash
#when no sys-admin rights, use pip, and you can have multiple ansible verion
sudo dnf install python-virtualenv
sudo dnf install gcc openssl-devel
virtualenv ~/ansible
source ~/ansbile/bin/activate
pip search ansbile
pip install ansible
```
* Inventory
  * A set of potential targets where task could be executed
  * Collecion of source machines/VM
  * Domain could belong to
    * Ungrouped, Grouped or SubGroups
      ```yaml
      server.sampledomain
      [web]
      server.sampledomain
      [web:east]
      japan.server.sampledomain      
      ```
  * Patterns
    * Single, Inclusive, Exclusive, Union
  * Inventory Variables
  * Variable Scopes
    * Host, Group, Group of Groups, All Group
  * Inventory Sources
    * Static
    * Dynamic
      * All cloud providers
      * Dockers, Vagrant, VMWware, VirtualBox
      * SSH_CONFIG
      * [Inventor Contrib Sources](https://github.com/ansible/ansible/tree/devel/contrib/inventory)
      * [Developing dynamic inventory source](http://docs.ansible.com/ansible/latest/dev_guide/developing_inventory.html)
    