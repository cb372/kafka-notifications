include_recipe "java"

remote_file "#{Chef::Config[:file_cache_path]}/kafka_#{node[:kafka][:full_version]}.tar.gz" do
  source "http://ftp.riken.jp/net/apache/kafka/#{node[:kafka][:version]}/kafka_#{node[:kafka][:full_version]}.tar.gz"
  action :create_if_missing
end

package "tar"

execute "extract Kafka" do
  cwd "/usr/local"
  command "tar -xzf #{Chef::Config[:file_cache_path]}/kafka_#{node[:kafka][:full_version]}.tar.gz"
  creates "/usr/local/kafka_#{node[:kafka][:full_version]}"
end

execute "chown Kafka dir" do
  command "chown -R vagrant:vagrant /usr/local/kafka_#{node[:kafka][:full_version]}"
end

link "/usr/local/kafka" do
  to "/usr/local/kafka_#{node[:kafka][:full_version]}"
end

