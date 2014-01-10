# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "centos-6.4-x64-minimal"
  config.vm.box_url = "https://dl.dropbox.com/u/7225008/Vagrant/CentOS-6.3-x86_64-minimal.box"

  # Forward ports for the play apps
  config.vm.network :forwarded_port, guest: 9001, host: 9001
  config.vm.network :forwarded_port, guest: 9002, host: 9002

  # Make sure Chef is a recent version
  config.vm.provision :shell, :inline => <<EOF
    if [ "$(gem list -v '~>11.8.2' -i chef)" != "true" ]; then 
      gem install chef --version 11.8.2 --no-rdoc --no-ri
    fi
EOF

  config.vm.provision :chef_solo do |chef|
    chef.log_level = :debug
    chef.cookbooks_path = "cookbooks", "provisioning/cookbooks"
    chef.roles_path = "provisioning/roles"
    chef.add_role "default"
    chef.node_name = "kafka-notifications"
  end

end
