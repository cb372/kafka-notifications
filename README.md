A proof-of-concept of something I wanted to try at work: using Kafka as a queue to pass notifications from one app to another, displaying them asynchronously using WebSockets.

Requirements
-----

Vagrant

How to run
-----

Set up Vagrant:

    gem install librarian-chef
    librarian-chef install
    vagrant up
    # Go away and make a cup of tea...

Login to the Vagrant box and start Kafka and the two Play apps:

    ssh vagrant
    /vagrant/start-everything.sh

Logout of the box and open the two Play apps in your browser:

    exit
    open http://localhost:9001 && open http://localhost:9002

Fill in the form in one app (localhost:9001), and the message should appear like magic in the other.
