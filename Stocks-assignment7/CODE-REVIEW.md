# Code critique

### Design critique
- All methods required by inflexible and flexible portfolios have been added to the same PortfolioModel interface
- Similarly, the Portfolio interface represents both the inflexible and flexible portfolios. Due to this, methods not required by the inflexible portfolios still need to be implemented (bait and switch)
- The GUI view does not extend the PortfolioView interface. This may require the consumer of this interface to make changes to their code

### Implementation critique
- asd
-

### Documentation critique
- The setup readme documented all the steps required to run the project so we had no issues in running the code
- Similarly, the design readme was clear and we could understand the design and some implementation details through it
- Although the README was written well, it was a bit misleading because a few functionalities were documented to be working which were not

### Design/code strengths
- The controller design was well thought out and was the responsibility of handling inputs was cleanly segregated into command objects for text UI and actionlisteners for GUI
- The GUI view implementation was done well with each dialog in the system being in its own class

### Design/code limitations
- Fetching stock data on weekends/non-trading is not working
- only works for a few stocks
- Dates are being hardcoded and will only work for years between 2015 and 2022
- Buying and selling a stock on a specific date has not been implemented

### Suggestions
- Move hardcoded strings to a Constants class to bring consistency
- Date validation is being performed manually by splitting a string which represents a date and only works for years between 2015 and 2022 as mentioned previously. Consider using LocalDate
- Split interfaces for inflexible and flexible portfolios
- Add the exceptions being thrown by a method to the method signature and avoid throwing RuntimeException

fixes by them