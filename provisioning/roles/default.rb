name 'default'

default_attributes(
  :java => {
    :install_flavor => 'oracle',
    :jdk_version => '7',
    :oracle => {
      "accept_oracle_download_terms" => true
    }
  }
)

run_list ["recipe[kafka-notifications::kafka]", "recipe[play2]"]
