execute "restart-iptables" do
  command "/sbin/service iptables restart"
  action :nothing
end

cookbook_file "/etc/sysconfig/iptables" do
  source "iptables"
  owner "root"
  group "root"
  mode 0600
  notifies :run, "execute[restart-iptables]", :immediately
end
