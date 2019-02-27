How to install:


Open the folder Meta1SD->src

Run RMIServer;
Run 3 TCPservers;
Run TCPClient;

On TCPClient enter the following commands:

EXAMPLES:

1- Register admin:
type: admin, username: nov2, password: ola

2- Register normal user:
type: register, username: byebye, password: ola

3- Login normal user or admin
type: login, username: bye, password: ola


--------You can only do this after you've logged in or registered-------

4- Create an auction

type: create_auction, code: 111, title: SDF, descritption: adoro o dei, deadline: 2017-01-01 00-01, amount: 10

5- Search an auction

type: search_auction, code: 111

6- Detail auction
type: detail_auction, id: 65416541 (our ids are random generated so search first by code and get it's id)

7- List all my bids
type: my_lici, username: ines

8- Edit my auctions
type: edit_auction, id: 65416541, deadline: 2017-01-02 00-01, title: ahah, description: oooh whats you saaay, amount: 13

9- List older versions of edited auction
type: older_versions, id: 65416541

10- Write message on Auction
type: message, id: 65416541, text: format?

11- See who's online
type: online_users

12- List all my created auctions
type: my_auctions

13- Make a bid on an action
type: bid, id: 65416541, amount: 5


If you are and admin you can also do this:

14- Cancel an auction
type: cancel, id: 65416541

15- Ban an user
type: ban_user, username: ines

16- See statistics:

type: win10 ---> top 10 winners of all users
type: last10 --> auctions created on the last 10 days
type: created10 --> top 10 auction creators


You can close RMI by typping exit in RMI (to keep information up to date on the files, but it's not necessary)


















