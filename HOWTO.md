# Using the cinema system

The cinema system supports three kinds of users: customers, staff, and managers. When you first launch the application, you will be asked to select which kind of user you are.

## Customer

As a customer, you can browse movies and cinemas without logging in, but you will have to log in with an account to purchase tickets. When you select that you are a customer, you will have the option to continue without logging in, or to login with an existing account or register a new account. With logging in or with registering, you will be asked for your username and password. If you register an account, you will then be logged in with that account. For convenience and demonstration, the username “Ted” and the password “12345” can be used to log in as a customer without having to register a new customer account.

Whether you continue as logged in or logged out, you will be presented with multiple options for browsing information about movies and cinemas.
- The first option is to search for a specific movie. By entering the name of a movie, you will be able to view information about all  of the available showings of that movie.
- The second option is to search for a specific cinema. By entering the name of the cinema, the system will provide a list of the movies that are being shown at that cinema, and information about the individual showings.
- The third option is to view a list of all movies. You will be able to select a movie from the list to view the details of that movie (e.g. synopsis, director), and the program will also list for you all of the available showings of that movie across different cinemas.
- The fourth option is to view a list of all cinemas. Similarly to the previous option, you will be able to select a cinema from the list to view information about the movies being shown at that cinema and the showings of them.
- The fifth option will allow you to save a credit card to your account so that you will be able to use it without having to re-enter the card number when you go to checkout.
- The sixth option lets you purchase tickets. You will have to be logged in as a customer to purchase tickets.

When you go to purchase tickets, you will first select the cinema you want to attend from the list, and then select a movie being shown at that cinema that you want to see. You will then be able to select the screen size you want to see the movie on from the showings that are available (e.g. bronze, silver, gold). Once you have selected the desired screen size, you will be able to select an available showing that meets the criteria, from a list specifying the date, time, and base price of each showing. Once you have selected a showing, you will then be able to select where in the theatre you want to purchase tickets for (e.g. front, middle, rear).

You can then decide how many tickets you wish to purchase. There are four different ticketing groups: adult, child, student, and senior/pension. Children receive a 40% discount, and students and older people or people on a pension receive a 25% discount. You can select a group from the list, and then specify how many tickets in that group you wish to purchase. You will then be returned to the list of groups so that you can purchase more tickets in another group if you wish, or to purchase more tickets in a group you already added tickets from if you need to add more. For convenience, the program will display a running total of the tickets you have added thus far and the total price. Once you are done, you can type “continue” to proceed to checkout.

At checkout, you can pay using a credit card or a gift card. If you select to pay with a credit card, you will be prompted to enter the cardholder name and the card number. For convenience and demonstration, the cardholder name “Charles” and card number “40691” can be used. If you have saved card information previously, you will only have to specify the cardholder name you want to use, and then the system will process the transaction without you having to enter the card number. You will then be asked if you want to save the details of the card you just used to check out with next time, and then you will be printed your transaction receipt, including the unique ID that you can use to get your tickets once you arrive at the cinema.

If you choose to pay with a gift card, you will then enter the full gift card number. If the gift card has not already been used and it has sufficient balance to pay for the purchase you are making, the transaction will complete and you will be printed your receipt as with paying with a credit card. If the gift card you’re using does not have sufficient balance, you will still be able to use it by providing credit card details to pay for the remainder of the purchase.

## Staff

As a staff member you can add a gift card, add showing for a movie, generate reports that show the upcoming movies and the available seats and bookings, add movies to the system, delete movies from the system and modify movie data.

When you select the staff menu from the main menu you will be prompted with a staff login screen where you will need to type in the staff username and password. The username: Martin and password: 12345 can be used to log in as a staff.

As a staff member, you can select to take the following actions:
- Add a gift card to the system by providing the gift card number
- Add a showing of a movie by selecting the cinema and movie, and then entering the date, start time, and end time of the showing, the screen class it will be shown on, and the price of the showing.
- Generate a report for a selected cinema listing upcoming showings, and a report specifying booked and available seats for each showing.
- Add a movie to the system, by specifying the data of the movie (e.g. name, synopsis, director, cast).
- Delete a movie from the system by specifying its name. This will also delete all showings of that movie from different cinemas, and you will be asked to confirm that you wish to proceed.
- Modify data about a movie in the system (e.g. synopsis, director, cast) by specifying the name of the movie to modify, selecting which field to modify, and then providing the new value.

## Manager

As a manager you can add a staff member, delete a staff member and obtain a report of all the user cancellations.

When you select the Manager option from the main menu you will be prompted to log in as the manager. The username: Tod and password: 12345 can be used to log in as the manager. As a manager, you can select to take the following actions
- Add a staff member to the system by providing their name and an initial password for their account.
- Delete a staff member from the system by providing their name.
- Generate a report of cancelled transactions, specifying date and time, the user that cancelled, and the reason for cancellation.
